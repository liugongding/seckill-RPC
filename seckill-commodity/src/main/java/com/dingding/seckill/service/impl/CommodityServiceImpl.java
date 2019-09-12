package com.dingding.seckill.service.impl;

import com.dingding.seckill.dao.CommodityMapper;
import com.dingding.seckill.dto.Exposer;
import com.dingding.seckill.dto.SeckillExecution;
import com.dingding.seckill.dto.SeckillStatusExecution;
import com.dingding.seckill.entity.Commodity;
import com.dingding.seckill.entity.User;
import com.dingding.seckill.enums.HandleSeckillEnum;
import com.dingding.seckill.exception.RepeatKillException;
import com.dingding.seckill.exception.SeckillCloseException;
import com.dingding.seckill.exception.SeckillException;
import com.dingding.seckill.service.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-05
 */
@Service
@Slf4j
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    /**
     * 设置秒杀redis缓存的key
     */
    private final String COMMODITY = "commodity";

    private final String SECKILLEXECUTON = "SeckillExecution";

    @Resource
    private CommodityMapper commodityMapper;

    @Autowired
    private ReadMqServiceImpl readMqService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillStatusService seckillStatusApi;

    /**
     * 盐值
     */
    private final String slat = "dfa123EDdasSA,.";
    /**
     * md5加密
     */
    private String md5 = null;

    @Override
    public List<Commodity> getCommodityList() {
        //1、首页全部加载进缓存
        List<Commodity> commodityList = redisTemplate.boundHashOps(COMMODITY).values();
        if (commodityList == null || commodityList.size() == 0) {
            //2、如果缓存中没有数据、从数据库中取出来添加到缓存
            commodityList = commodityMapper.queryAll();
            commodityList.forEach(commodity -> {
                redisTemplate.boundHashOps(COMMODITY).put(commodity.getCommodityId(), commodity);
            });
        }
        return commodityList;
    }

    @Override
    public Commodity getById(Integer commodityId) {
        Commodity seckill = (Commodity) redisTemplate.boundHashOps(COMMODITY).get(commodityId);
        if (seckill == null) {
            seckill = commodityMapper.queryById(commodityId);
        }
        return seckill;
    }

    @Override
    public Exposer exportSeckillUrl(Integer commodityId) {
        //首先从缓存中查找
        Commodity seckill = (Commodity) redisTemplate.boundHashOps(COMMODITY).get(commodityId);
        if (seckill == null) {
            log.info("查mysql");
            //缓存没有
            seckill = commodityMapper.queryById(commodityId);

            if (seckill == null) {
                return new Exposer(false, commodityId);
            }
            redisTemplate.boundHashOps(COMMODITY).put(commodityId, seckill);
        }
        //当前时间
        Date nowTime = new Date();
        //获取数据库的时间字段
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();

        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, commodityId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        md5 = getMD5(commodityId);
        return new Exposer(true, md5, commodityId);
    }

    /**
     * 生成 md5
     *
     * @param commodityId
     * @return
     */
    private String getMD5(Integer commodityId) {
        String base = commodityId + "/" + slat;
        md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    public SeckillStatusExecution executeSeckill(Integer commodityId, Long userPhone, String md5) {
        if (md5 == null || !md5.equals(getMD5(commodityId))) {
            throw new SeckillException("seckill data rewrite");
        }


        try {
            readMqService.createSeckillMq(new User(commodityId, userPhone));
            return new SeckillStatusExecution(commodityId, userPhone, HandleSeckillEnum.HANDLING);
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (SeckillException e3) {
            throw e3;
        }
    }

    @Override
    public SeckillExecution querySeckillStatus(Integer commodityId_userPhone) throws InterruptedException {
         SeckillExecution seckillExecution = null;
        while (true) {
            Thread.sleep(2000);
            seckillExecution = (SeckillExecution) redisTemplate.boundHashOps(SECKILLEXECUTON).get(commodityId_userPhone);
            if (seckillExecution != null) {
                return seckillExecution;
            }
        }
    }


}
