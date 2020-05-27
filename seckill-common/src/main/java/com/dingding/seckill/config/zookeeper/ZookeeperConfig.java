package com.dingding.seckill.config.zookeeper;

import org.apache.dubbo.config.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liudingding
 * @ClassName ZookeeperConfig
 * @description
 * @date 2020/3/27 20:24
 * Version 1.0
 */
@Configuration
public class ZookeeperConfig {

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setCheck(false);
        consumerConfig.setTimeout(20000);
        return consumerConfig;
    }
}
