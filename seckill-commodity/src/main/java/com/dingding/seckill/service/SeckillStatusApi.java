package com.dingding.seckill.service;

import com.dingding.seckill.dto.SeckillExecution;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-09
 * @TODO 缓存该状态
 */
public interface SeckillStatusApi {

    /**
     * RPC 远程查询秒杀状态
     * @param commodityId_userPhone
     * @return
     */
    SeckillExecution querySeckillStatus(Integer commodityId_userPhone);

    /**
     * 将秒杀结果存入map
     * @param commodityId
     * @param seckillExecution
     */
//    void setSeckillStatusMap(Integer commodityId,SeckillExecution seckillExecution);

}
