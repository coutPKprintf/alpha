package com.alpha.common.application;

import org.apache.commons.lang.time.StopWatch;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenwen on 16/12/22.
 * 抽象的应用程序类
 * 所有非web应用程序的入口类 -> 需要实现此类
 */
@SpringBootApplication
@Slf4j
public abstract class AbstractApplication implements CommandLineRunner{

    @Override
    public void run(String... strings) throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("---------------------------- {} start running with args = {} ----------------------------", this.getClass().getName(), strings);

        start(strings);

        stopWatch.stop();
        log.info("---------------------------- end running costTime = {} (ms) ----------------------------", stopWatch.getTime());
    }

    public abstract void start(String... strings);
}
