package com.alpha.application.example.service.impl;

import com.alpha.application.example.service.ExampleService;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenwen on 16/12/22.
 */
@Service
@Slf4j
public class ExampleServiceImpl implements ExampleService{
    @Override
    public void sayHello() {
        log.info("hello");
    }
}
