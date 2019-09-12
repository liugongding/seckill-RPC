package com.dingding.seckill;

import com.dingding.seckill.service.impl.WebSocketServer;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-04
 */
@EnableScheduling
@EnableDubbo
@SpringBootApplication
public class CommodityApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CommodityApplication.class, args);
        //解决WebSocket不能注入的问题
        WebSocketServer.setApplicationContext(applicationContext);

    }
}
