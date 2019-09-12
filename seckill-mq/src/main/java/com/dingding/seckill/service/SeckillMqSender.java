package com.dingding.seckill.service;

import com.dingding.seckill.entity.User;
import com.dingding.seckill.exception.RepeatKillException;
import com.dingding.seckill.exception.SeckillCloseException;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-05
 */
public interface SeckillMqSender {

    /**
     * 给消息发送者的rpc接口
     *
     * @param user
     * @throws SeckillCloseException
     * @throws RepeatKillException
     */
    void sendMessage(User user) throws SeckillCloseException, RepeatKillException;
}
