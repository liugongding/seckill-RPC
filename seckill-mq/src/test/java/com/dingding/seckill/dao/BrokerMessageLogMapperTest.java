package com.dingding.seckill.dao;

import com.dingding.seckill.MqApplication;
import com.dingding.seckill.entity.BrokerMessageLog;
import com.dingding.seckill.entity.User;
import com.dingding.seckill.enums.ConstantEnum;
import com.dingding.seckill.util.FastJsonConvertUtil;
import com.dingding.seckill.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-05
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqApplication.class)
@Slf4j
public class BrokerMessageLogMapperTest {

    @Resource
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Test
    public void queryStatusAndTimeoutMessage() {
    }

    @Test
    public void updateReSendCount() {
    }

    @Test
    public void changeBrokerMessageLogStatus() {
    }

    @Test
    public void insertBrokerMessage() {
        User user = new User(1000, RandomUtil.getRandomNumber(11));
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        brokerMessageLog.setMessageId(user.getUserPhone());
        brokerMessageLog.setMessage(FastJsonConvertUtil.convertObjectToJSON(user));
        brokerMessageLog.setStatus(ConstantEnum.MESSAGE_SENDING.getStatus());
        brokerMessageLog.setNextRetry(new Date());
        brokerMessageLog.setCreateTime(new Date());
        brokerMessageLog.setUpdateTime(new Date());
        log.info("电话" + user.getUserPhone());
        brokerMessageLogMapper.insertBrokerMessage(brokerMessageLog);
    }
}