package com.dingding.seckill.controller;

import com.dingding.seckill.CommodityApplication;
import com.dingding.seckill.dto.SeckillExecution;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommodityApplication.class)
@Slf4j
public class SeckillControllerTest {


    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test1(){
        Integer commodityId_userPhone = -539221985;
        String SECKILLEXECUTON = "SeckillExecution";
        SeckillExecution seckillExecution = (SeckillExecution) redisTemplate.boundHashOps(SECKILLEXECUTON).get(commodityId_userPhone);
        log.info("秒杀状态:{}",seckillExecution.getStateInfo());
    }
}