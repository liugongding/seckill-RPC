package com.dingding.seckill.service.impl;

import com.dingding.seckill.dao.BrokerMessageLogMapper;
import com.dingding.seckill.entity.User;
import com.dingding.seckill.enums.ConstantEnum;
import com.dingding.seckill.exception.RepeatKillException;
import com.dingding.seckill.exception.SeckillCloseException;
import com.dingding.seckill.service.SeckillMqSender;
import com.dingding.seckill.util.FastJsonConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-05
 */
@Service
@Slf4j
public class SeckillMqSenderImpl implements SeckillMqSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private BrokerMessageLogMapper brokerMessageLogMapper;

           final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
            log.info("回调:{}", correlationData.getId());
            String messageId = correlationData.getId();
            log.info("ack:{}", ack);
            if (ack) {
                //如果confirm返回成功、则改变broker的状态
                //MQ成功到达交换机
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId, ConstantEnum.MESSAGE_SEND_SUCCESS.getStatus(), new Date());
                log.info("消息成功到达mq");
            } else {
                log.error("MQ消息到达Exchange异常");
            }
        };

        @Override
        public void sendMessage(User user) {
            rabbitTemplate.setConfirmCallback(confirmCallback);
            CorrelationData correlationData = new CorrelationData(String.valueOf(user.getUserPhone()));
            String msg = FastJsonConvertUtil.convertObjectToJSON(user);
            log.info("消息唯一id：{}", correlationData);
            log.info("json对象字符串: {}", msg);
            try {
                rabbitTemplate.convertAndSend("successKilled-exchange", "successKilled.send", msg, correlationData);
            } catch (SeckillCloseException | RepeatKillException e) {
                throw e;
            }
    }
}
