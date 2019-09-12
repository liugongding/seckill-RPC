package com.dingding.seckill.service.impl;

import com.dingding.seckill.MqApplication;
import com.dingding.seckill.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-06
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqApplication.class)
@Slf4j
public class SeckillMqSenderImplTest {

    @Autowired
    private SeckillMqSenderImpl seckillMqSender;
    @Test
    public void sendMessage() {
        User user = User.builder()
                .userPhone(15586531951L)
                .commodityId(1003)
                .build();
            seckillMqSender.sendMessage(user);
    }
}