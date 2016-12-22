package com.alpha.tools.queue.rabbitmq;

/**
 * Created by chenwen on 16/9/20.
 */
public interface EventTemplate {
    /**
     * send message
     * @param queueInfo queue info
     * @param message message
     * @param <T> message type
     */
    <T extends Message> void send(QueueInfo queueInfo, T message) throws SendRefuseException;
}
