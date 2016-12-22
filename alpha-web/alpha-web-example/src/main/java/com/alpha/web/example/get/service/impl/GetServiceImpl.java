package com.alpha.web.example.get.service.impl;

import com.alpha.web.example.get.service.GetService;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenwen on 16/12/22.
 */
@Service
@Slf4j
public class GetServiceImpl implements GetService{
    @Override
    public String helloGet() {
        return "hello get";
    }
}
