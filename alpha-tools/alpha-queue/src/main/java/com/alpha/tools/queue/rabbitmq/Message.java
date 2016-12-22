package com.alpha.tools.queue.rabbitmq;

import com.alpha.common.utils.RandomUtil;

import java.io.Serializable;

import lombok.Getter;

/**
 * Created by chenwen on 16/9/20.
 */
public class Message implements Serializable{
    /**
     * serial version
     */
    private static final long serialVersionUID = 1L;

    @Getter
    private String _id = RandomUtil.generateRandomString();
}
