package com.alpha.tools.queue.rabbitmq.impl;

import com.alpha.common.serialize.CodecFactory;
import com.alpha.common.serialize.HessionCodecFactory;
import com.alpha.tools.queue.rabbitmq.EventController;
import com.alpha.tools.queue.rabbitmq.EventMessage;
import com.alpha.tools.queue.rabbitmq.EventTemplate;
import com.alpha.tools.queue.rabbitmq.Message;
import com.alpha.tools.queue.rabbitmq.QueueInfo;
import com.alpha.tools.queue.rabbitmq.SendRefuseException;
import com.alpha.tools.queue.rabbitmq.util.ValidateUtil;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;

import java.io.IOException;

/**
 * Created by chenwen on 16/9/20.
 */
public class DefaultEventTemplate implements EventTemplate {
    private EventController eventController;

    private AmqpTemplate amqpTemplate;

    private CodecFactory codecFactory;

    public DefaultEventTemplate(AmqpTemplate amqpTemplate, EventController eventController, CodecFactory codecFactory){
        this.amqpTemplate = amqpTemplate;
        this.eventController = eventController;
        this.codecFactory = codecFactory == null ? new HessionCodecFactory() : codecFactory;
    }

    public <T extends Message> void send(QueueInfo queueInfo, T message)  throws SendRefuseException {
        if (!ValidateUtil.validateQueueInfo(queueInfo)){
            throw new SendRefuseException("illegal queue info");
        }

        if (!eventController.beBinded(queueInfo)){
            eventController.declareBinding(queueInfo);
        }

        try {
            EventMessage eventMessage = new EventMessage(queueInfo,codecFactory.serialize(message));
            amqpTemplate.convertAndSend(queueInfo.getExchangeName(),queueInfo.getRouteKey(),eventMessage);
        }catch (AmqpException | IOException e){
            throw new SendRefuseException("send event fail",e);
        }
    }
}
