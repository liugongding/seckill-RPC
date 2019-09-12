package com.dingding.seckill.service;

import com.dingding.seckill.entity.User;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-05
 */
public interface ReadMqService {

    /**
     * 创建消息队列
     * @param user
     */
    void createSeckillMq(User user);
}
