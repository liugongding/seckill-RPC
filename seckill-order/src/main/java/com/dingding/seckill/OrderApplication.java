package com.dingding.seckill;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-05
 */
@EnableDubbo
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(OrderApplication.class, args);

        for (String name : applicationContext.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }
}
