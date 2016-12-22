package com.alpha.tools.queue.rabbitmq.impl;

import org.springframework.util.ErrorHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * User: chenwen
 * Date: 2015-10-29
 */
@Slf4j
public class MessageErrorHandler implements ErrorHandler {

	@Override
	public void handleError(Throwable t) {
		log.error("RabbitMQ happen a error:" + t.getMessage(), t);
	}

}
