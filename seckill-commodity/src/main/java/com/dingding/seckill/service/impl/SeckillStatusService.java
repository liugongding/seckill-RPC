package com.dingding.seckill.service.impl;

import com.dingding.seckill.dto.SeckillExecution;
import com.dingding.seckill.service.SeckillStatusApi;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-09
 */
@Component
public class SeckillStatusService implements SeckillStatusApi, Serializable {


    private static Map<Integer, SeckillExecution> seckillStatusMap = new ConcurrentHashMap<>(10);

    @Override
    public SeckillExecution querySeckillStatus(Integer commodityId_userPhone) {
        return seckillStatusMap.get(commodityId_userPhone);
    }


    public static void setSeckillStatusMap(Integer commodityId_userPhone, SeckillExecution seckillExecution) {
        seckillStatusMap.put(commodityId_userPhone, seckillExecution);
    }
}
