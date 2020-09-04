package com.example.demo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class DemoTask {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Scheduled(cron = "19 * * * * ?")
    public void putLog(){
        logger.info("输出log1"+new Date());
    }

   // @Scheduled(cron = "21 * * * * ?")
    public void putLog2(){
        logger.info("输出log2"+new Date());
    }

   // @Scheduled(cron = "23 * * * * ?")
    public void putLog3(){
        logger.info("输出log3"+new Date());
    }
}
