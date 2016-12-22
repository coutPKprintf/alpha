package com.alpha.application.example;

import com.alpha.application.example.service.ExampleService;
import com.alpha.common.application.AbstractApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenwen on 16/12/22.
 * 非web应用启动样例
 */
@EnableAutoConfiguration
@Slf4j
public class Application extends AbstractApplication{
    /**
     * 样例服务注入
     */
    @Autowired
    ExampleService exampleService;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void start(String... strings) {
        exampleService.sayHello();
    }
}
