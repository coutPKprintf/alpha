package com.alpha.web.example;

import com.alpha.common.web.AbstractMainApp;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenwen on 16/12/22.
 */
@Slf4j
@SpringBootApplication
@EnableCaching
@PropertySources(
        {
                @PropertySource(value = "classpath:application-common.properties",ignoreResourceNotFound = false)
        }
)
@ComponentScan(
        basePackages = {"com.alpha"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX,pattern = "com.alpha.web.[^(example)].*")}
)
public class MainApp extends AbstractMainApp {
    public static void main(String[] args) {
        context = SpringApplication.run(MainApp.class, args);
        for(String name : context.getBeanDefinitionNames()){
            log.info(name);
        }
    }

    @Override
    public void start(ApplicationArguments arguments) {

    }
}
