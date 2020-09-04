package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


@Configuration
public class ModelSchedulingConfigurer implements SchedulingConfigurer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod="shutdown")
    public ScheduledExecutorService taskExecutor() {
        return new ScheduledThreadPoolExecutor(30, new ThreadFactory() {
            //等同于i++,++i,更加安全
            private AtomicInteger max = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                logger.info("max="+max.incrementAndGet());
                return new Thread(r, "modelScheduleConfig-" + max.get());
            }
        });
    }
}
