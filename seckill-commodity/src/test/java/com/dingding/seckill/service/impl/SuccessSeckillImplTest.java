package com.dingding.seckill.service.impl;

import com.dingding.seckill.CommodityApplication;
import com.dingding.seckill.api.commodity.SuccessSeckillApi;
import com.dingding.seckill.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-06
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommodityApplication.class)
@Slf4j
public class SuccessSeckillImplTest {

    @Reference
    private SuccessSeckillApi successSeckillApi;
    @Test
    public void reduceStock() {
        User user = User.builder()
                .commodityId(1000)
                .userPhone(155865319561L)
                .build();
        successSeckillApi.reduceStock(user);
    }
}