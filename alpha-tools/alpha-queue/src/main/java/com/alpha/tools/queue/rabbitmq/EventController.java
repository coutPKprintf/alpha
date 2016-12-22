package com.alpha.tools.queue.rabbitmq;

import com.alpha.common.serialize.CodecFactory;

/**
 * Created by chenwen on 16/9/20.
 */
public interface EventController {
    /**
     * 启动监听
     */
    void start();

    /**
     * 获取发送模版
     */
    EventTemplate getEventTemplate();

    /**
     * 获取序列化工具
     * @return 序列化工厂
     */
    CodecFactory getCodecFactory();

    /**
     * add listener
     * @param queueInfo queue
     * @param eventProcessor processor
     * @return this
     */
    <T extends Message> EventController add(QueueInfo queueInfo, EventProcessor<T> eventProcessor);

    /**
     * is or not binded
     * @param queueInfo queue info
     * @return is or not binded
     */
    boolean beBinded(QueueInfo queueInfo);

    /**
     * bind
     * @param queueInfo queue info
     */
    void declareBinding(QueueInfo queueInfo);
}
