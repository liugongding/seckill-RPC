package com.dingding.seckill.service.impl;

import com.dingding.seckill.dao.BrokerMessageLogMapper;
import com.dingding.seckill.entity.BrokerMessageLog;
import com.dingding.seckill.entity.User;
import com.dingding.seckill.enums.ConstantEnum;
import com.dingding.seckill.exception.RepeatKillException;
import com.dingding.seckill.exception.SeckillCloseException;
import com.dingding.seckill.service.ReadMqService;
import com.dingding.seckill.service.SeckillMqSender;
import com.dingding.seckill.util.FastJsonConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-05
 */
@Slf4j
@Service
public class ReadMqServiceImpl implements ReadMqService {

    @Resource
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Autowired
    private SeckillMqSender seckillMqSender;

    @Override
    public void createSeckillMq(User user) {

        log.info("秒杀对象: {}", user);
        BrokerMessageLog brokerMessageLog = BrokerMessageLog.builder()
                //消息唯一id
                .messageId(user.getUserPhone())
                //保存消息整体、转 json 入库
                .message(FastJsonConvertUtil.convertObjectToJSON(user))
                //设置消息状态为0，表示发送中
                .status(ConstantEnum.MESSAGE_SENDING.getStatus())
                //设置消息未确认，超时窗口为1min
                .nextRetry(new Date())
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        //插入消息brokerLog
        log.info("broker对象：{}", brokerMessageLog);
        brokerMessageLogMapper.insertBrokerMessage(brokerMessageLog);
        try {

            log.info("生产者发送消息");

            seckillMqSender.sendMessage(user);


        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        }
    }
}
