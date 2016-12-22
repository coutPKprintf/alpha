package com.alpha.tools.queue.rabbitmq.impl;

import com.alpha.common.serialize.CodecFactory;
import com.alpha.common.serialize.HessionCodecFactory;
import com.alpha.tools.queue.rabbitmq.EventControlConfig;
import com.alpha.tools.queue.rabbitmq.EventController;
import com.alpha.tools.queue.rabbitmq.EventProcessor;
import com.alpha.tools.queue.rabbitmq.EventTemplate;
import com.alpha.tools.queue.rabbitmq.Message;
import com.alpha.tools.queue.rabbitmq.QueueInfo;
import com.alpha.tools.queue.rabbitmq.util.QueueInfoUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.Getter;

/**
 * Created by chenwen on 16/9/20.
 */
public class DefaultEventController implements EventController {
    private CachingConnectionFactory rabbitConnectionFactory;

    private EventControlConfig config;

    private RabbitAdmin rabbitAdmin;

    @Getter
    private CodecFactory codecFactory;

    private SimpleMessageListenerContainer msgListenerContainer; // rabbitMQ msg listener container

    private MessageAdapterHandler msgAdapterHandler;

    private MessageConverter serializerMessageConverter = new SerializerMessageConverter(); // 直接指定
    //queue cache, key is exchangeName
    private Map<String, Exchange> exchanges = new ConcurrentHashMap<>();
    //queue cache, key is queueName
    private Map<String, Queue> queues = new ConcurrentHashMap<>();
    //bind relation of queue to exchange cache, value is exchangeName | queueName
    private Set<String> binded = new ConcurrentSkipListSet<>();

    @Getter
    private EventTemplate eventTemplate; // 给App使用的Event发送客户端

    private AtomicBoolean isStarted = new AtomicBoolean(false);

    private static DefaultEventController defaultEventController;

    public static DefaultEventController getInstance(EventControlConfig config){
        if(defaultEventController==null){
            synchronized (DefaultEventController.class) {
                if (defaultEventController == null) {
                    defaultEventController = new DefaultEventController(config);
                }
            }
        }
        return defaultEventController;
    }

    private DefaultEventController(EventControlConfig config){
        if (config == null) {
            throw new IllegalArgumentException("Config can not be null.");
        }
        this.config = config;
        initRabbitConnectionFactory();
        // 初始化AmqpAdmin
        rabbitAdmin = new RabbitAdmin(rabbitConnectionFactory);
        // 初始化RabbitTemplate
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        rabbitTemplate.setMessageConverter(serializerMessageConverter);
        if (codecFactory == null){
            codecFactory = new HessionCodecFactory();
        }
        if (msgAdapterHandler == null){
            msgAdapterHandler = new MessageAdapterHandler(codecFactory);
        }
        eventTemplate = new DefaultEventTemplate(rabbitTemplate, this,codecFactory);
    }

    /**
     * 初始化rabbitmq连接
     */
    private void initRabbitConnectionFactory() {
        rabbitConnectionFactory = new CachingConnectionFactory();
        rabbitConnectionFactory.setHost(config.getServerHost());
        rabbitConnectionFactory.setChannelCacheSize(config.getEventMsgProcessNum());
        rabbitConnectionFactory.setPort(config.getPort());
        rabbitConnectionFactory.setUsername(config.getUsername());
        rabbitConnectionFactory.setPassword(config.getPassword());
        if (!StringUtils.isEmpty(config.getVirtualHost())) {
            rabbitConnectionFactory.setVirtualHost(config.getVirtualHost());
        }
    }

    /**
     * 注销程序
     */
    public synchronized void destroy() throws Exception {
        if (!isStarted.get()) {
            return;
        }
        msgListenerContainer.stop();
        eventTemplate = null;
        rabbitAdmin = null;
        rabbitConnectionFactory.destroy();
    }

    @Override
    public void start() {
        if (isStarted.get()) {
            return;
        }
        Set<String> mapping = msgAdapterHandler.getAllBinding();
        for (String relation : mapping) {
            declareBinding(QueueInfoUtil.getQueueInfo(relation));
        }
        initMsgListenerAdapter();
        isStarted.set(true);
    }

    /**
     * 初始化消息监听器容器
     */
    private void initMsgListenerAdapter(){
        MessageListener listener = new MessageListenerAdapter(msgAdapterHandler,serializerMessageConverter);
        msgListenerContainer = new SimpleMessageListenerContainer();
        msgListenerContainer.setConnectionFactory(rabbitConnectionFactory);
        msgListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        msgListenerContainer.setMessageListener(listener);
        msgListenerContainer.setErrorHandler(new MessageErrorHandler());
        msgListenerContainer.setPrefetchCount(config.getPrefetchSize()); // 设置每个消费者消息的预取值
        msgListenerContainer.setConcurrentConsumers(config.getEventMsgProcessNum());
        msgListenerContainer.setTxSize(config.getPrefetchSize());//设置有事务时处理的消息数
        msgListenerContainer.setQueues(queues.values().toArray(new Queue[queues.size()]));
        msgListenerContainer.start();
    }

    public <T extends Message> EventController add(QueueInfo queueInfo, EventProcessor<T> eventProcessor) {
        msgAdapterHandler.add(queueInfo , eventProcessor);
        if(isStarted.get()){
            initMsgListenerAdapter();
        }
        return this;
    }

    @Override
    public boolean beBinded(QueueInfo queueInfo) {
        return binded.contains(QueueInfoUtil.getBindKey(queueInfo));    }

    @Override
    public void declareBinding(QueueInfo queueInfo) {
        String bindRelation = QueueInfoUtil.getBindKey(queueInfo);
        if (binded.contains(bindRelation)) return;

        boolean needBinding = false;
        Exchange exchange = exchanges.get(queueInfo.getExchangeName());
        if(exchange == null) {
            switch (queueInfo.getExchangeType()){
                case DIRECT:
                    exchange = new DirectExchange(queueInfo.getExchangeName(), true, false, null);
                    break;
                case FANOUT:
                    exchange = new FanoutExchange(queueInfo.getExchangeName(),true,false,null);
                    break;
                default:
                    throw new IllegalArgumentException("not support this exchange");
            }
            exchanges.put(queueInfo.getExchangeName(), exchange);
            rabbitAdmin.declareExchange(exchange);//声明exchange
            needBinding = true;
        }

        Queue queue = queues.get(queueInfo.getQueueName());
        if(queue == null) {
            queue = new Queue(queueInfo.getQueueName(), queueInfo.getDurable(), queueInfo.getExclusive(), queueInfo.getAutoDelete());
            queues.put(queueInfo.getQueueName(), queue);
            rabbitAdmin.declareQueue(queue);	//声明queue
            needBinding = true;
        }

        if(needBinding) {
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(queueInfo.getRouteKey()).noargs();//将queue绑定到exchange
            rabbitAdmin.declareBinding(binding);//声明绑定关系
            binded.add(bindRelation);
        }
    }
}
