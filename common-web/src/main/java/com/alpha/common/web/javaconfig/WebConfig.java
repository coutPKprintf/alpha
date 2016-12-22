package com.alpha.common.web.javaconfig;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alpha.common.web.fastjson.FastJsonArgumentResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by chenwen on 16/10/10.
 */
@Configuration
//@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public FastJsonArgumentResolver fastJsonArgumentResolver(){
        return new FastJsonArgumentResolver();
    }

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
        return new FastJsonHttpMessageConverter();
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // equivalent to <mvc:argument-resolvers>
        argumentResolvers.add(fastJsonArgumentResolver());
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // equivalent to <mvc:message-converters>
        converters.add(fastJsonHttpMessageConverter());
    }
}

