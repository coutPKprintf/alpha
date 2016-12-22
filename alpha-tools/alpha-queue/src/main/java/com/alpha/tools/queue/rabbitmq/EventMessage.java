package com.alpha.tools.queue.rabbitmq;

import java.io.Serializable;
import java.util.Arrays;

import lombok.Getter;

/**
 * User: chenwen
 * Date: 2015-10-29
 *
 * 在App和RabbitMQ之间转送的消息
 */
@SuppressWarnings("serial")
public class EventMessage implements Serializable{
	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	private QueueInfo queueInfo;

	@Getter
	private byte[] eventData;

	public EventMessage(QueueInfo queueInfo, byte[] eventData) {
		this.queueInfo = queueInfo;
		this.eventData = eventData;
	}

	public EventMessage() {
	}

	@Override
	public String toString() {
		return "EventMessage{" +
				"queueInfo=" + queueInfo +
				", eventData=" + Arrays.toString(eventData) +
				'}';
	}
}
