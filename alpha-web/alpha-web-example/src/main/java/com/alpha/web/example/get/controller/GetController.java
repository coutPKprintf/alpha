package com.alpha.web.example.get.controller;

import com.alpha.common.web.result.JSONResult;
import com.alpha.common.web.result.Result;
import com.alpha.web.example.get.service.GetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenwen on 16/12/22.
 */
@Slf4j
@Controller
@RequestMapping("/get")
public class GetController {

    @Autowired
    GetService getService;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    @ResponseBody
    public Result test(){
        log.info("get {}", getService.helloGet());
        return JSONResult.ok();
    }
}
