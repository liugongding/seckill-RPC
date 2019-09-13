package com.dingding.seckill.service;

import com.dingding.seckill.dto.Exposer;
import com.dingding.seckill.dto.SeckillExecution;
import com.dingding.seckill.dto.SeckillStatusExecution;
import com.dingding.seckill.entity.Commodity;
import com.dingding.seckill.exception.RepeatKillException;
import com.dingding.seckill.exception.SeckillCloseException;
import com.dingding.seckill.exception.SeckillException;

import java.util.List;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-05
 */
public interface CommodityService {
    /**
     * 查询所有商品
     *
     * @return
     */
    List<Commodity> getCommodityList();

    /**
     * 查询单个秒杀记录
     *
     * @param commodityId
     * @return
     */
    Commodity getById(Integer commodityId);

    /**
     * 秒杀开启时、暴露秒杀接口地址
     * 否则输出系统时间和秒杀时间
     *
     * @param commodityId
     */
    Exposer exportSeckillUrl(Integer commodityId);

    /**
     * 执行秒杀操作
     *
     * @param commodityId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws SeckillCloseException
     * @throws RepeatKillException
     */
    SeckillStatusExecution executeSeckill(Integer commodityId, Long userPhone, String md5)
            throws SeckillException, SeckillCloseException, RepeatKillException, Exception;

    /**
     * 模拟短信通知
     * 只有秒杀成功才有短信通知
     *
     * @param commodityId_userPhone
     * @return
     */
    SeckillExecution querySeckillStatus(Integer commodityId_userPhone) throws InterruptedException;
}
