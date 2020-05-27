package com.dingding.seckill.service.impl;

import com.dingding.seckill.api.commodity.SuccessSeckillApi;
import com.dingding.seckill.api.order.OrderServiceApi;
import com.dingding.seckill.dao.CommodityMapper;
import com.dingding.seckill.dto.SeckillExecution;
import com.dingding.seckill.entity.Commodity;
import com.dingding.seckill.entity.Order;
import com.dingding.seckill.entity.User;
import com.dingding.seckill.enums.SeckillStateEnum;
import com.dingding.seckill.exception.RepeatKillException;
import com.dingding.seckill.exception.SeckillCloseException;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description 这里由于分布式事务没有的一个小bug没有解决、所以我们多查了一次 db ，有待优化
 * 分布式事务插入成功与否都返回-1、不知道如何解决
 * 因此我们根据 commodityId 和 userPhone 查出 order 是否存在进而判断是否重复秒杀
 * TODO 要不要把重复秒杀或秒杀结束的信息存到redis有待优化
 * @Author liugongding
 * @Date 2019-09-06
 */
@Service(interfaceClass = SuccessSeckillApi.class)
@Slf4j
public class SuccessSeckillImpl implements SuccessSeckillApi {

    private final String KEY = "SeckillExecution";
    private final String COMMODITY = "commodity";
    @Resource
    private CommodityMapper commodityMapper;

    @Reference
    private OrderServiceApi orderService;

    @Autowired
    private SeckillStatusService seckillStatusService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @GlobalTransactional(rollbackFor = RuntimeException.class)
    public void reduceStock(User user) throws RepeatKillException, SeckillCloseException{
        Integer commodityId_userPhone = user.getCommodityId() + user.getUserPhone().intValue();
        log.info("commodityId_userPhone:{}", commodityId_userPhone);
        log.info("秒杀对象:{}", user);
            //查询订单是否存在
            log.info("商品id：{}", user.getCommodityId());
            log.info("用户手机号:{}", user.getUserPhone());
            Order orderIdResult = orderService.queryOrderByCommodityId(user.getCommodityId(), user.getUserPhone());
            log.info("订单详情：{}", orderIdResult);
            if (orderIdResult != null) {
                //重复创建订单
                SeckillExecution seckillExecution = new SeckillExecution(commodityId_userPhone, SeckillStateEnum.REPEAT_KILL);

                //保存秒杀状态
                redisTemplate.boundHashOps(KEY).put(commodityId_userPhone, seckillExecution);

                //TODO 66，67,68 行多余、为了方便打印日志，测试完删除
                SeckillExecution seckillExecutionStatus = (SeckillExecution) redisTemplate.boundHashOps(KEY).get(commodityId_userPhone);
                log.info("秒杀执行状态:{}", seckillExecutionStatus);
                throw new RepeatKillException("seckill is repeated");
            } else {
                //创建订单
                orderService.createOrder(user);

                // db 减库存
                int updateResult = commodityMapper.reduceNumber(user.getCommodityId(), new Date());
                Commodity commodity = null;

                if (updateResult <= 0) {
                    //秒杀活动已结束
                    log.info("updateResult:{}", updateResult);
                    SeckillExecution seckillExecution = new SeckillExecution(commodityId_userPhone, SeckillStateEnum.END);
                    redisTemplate.boundHashOps(KEY).put(commodityId_userPhone, seckillExecution);

                    //TODO 66，67,68 行多余、为了方便打印日志，测试完删除
                    SeckillExecution seckillExecutionStatus = (SeckillExecution) redisTemplate.boundHashOps(KEY).get(commodityId_userPhone);
                    log.info("秒杀执行状态:{}", seckillExecutionStatus);
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    log.info("updateResult:{}", updateResult);

                    //查询订单结果
                    Order order = orderService.queryByIdWithSeckill(user.getCommodityId(), user.getUserPhone());
                    log.info("订单：{}", order);

                    //redis减库存
                    commodity = (Commodity) redisTemplate.boundHashOps(COMMODITY).get(user.getCommodityId());
                    if (commodity == null) {
                        commodity = commodityMapper.queryById(user.getCommodityId());
                    }
                    commodity.setNumber(commodity.getCommodityId() - 1);
                    redisTemplate.boundHashOps(COMMODITY).put(user.getCommodityId(), commodity);

                    //返回订单结果
                    SeckillExecution seckillExecution = new SeckillExecution(commodityId_userPhone, SeckillStateEnum.SUCCESS, order);
                    log.info("seckillExecution:{}", seckillExecution);

                    // 将订单结果缓存
                    redisTemplate.boundHashOps(KEY).put(commodityId_userPhone, seckillExecution);

                    //TODO 查询秒杀状态
                    SeckillExecution seckillExecutionStatus = (SeckillExecution) redisTemplate.boundHashOps(KEY).get(commodityId_userPhone);
                    log.info("秒杀执行状态:{}", seckillExecutionStatus);
                    log.info("秒杀完成");
                }
            }
    }
}
