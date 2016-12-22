package com.alpha.tools.shiro.aop;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by cw on 15-12-3.
 */
@Component
@Aspect
@Slf4j
public class PermissionsAspectJ {
    @Autowired
    HttpServletRequest request;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 登录校验
     */
    @Before("@within(com.alpha.tools.shiro.annotation.LoginCheck) || @annotation(com.alpha.tools.shiro.annotation.LoginCheck)")
    public void loginCheck() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
        }
    }
}
