package com.dingding.seckill.dao;

import com.dingding.seckill.OrderApplication;
import com.dingding.seckill.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-05
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
@Slf4j
public class OrderMapperTest {

    @Resource
    private OrderMapper orderMapper;

    @Test
    public void insertOrder() {
        Integer commodityId = 1001;
        Long userPhone = System.currentTimeMillis();
        Order order = new Order();
        order.setCommodityId(commodityId);
        order.setUserPhone(15586531956L);
        int insertCount = orderMapper.insertOrder(order);
        log.info("insertCount: {}", insertCount);
        log.info("自增主键: {}", order.getId());
    }

    @Test
    public void queryByIdWithSeckill() {
        Integer id = 1002;
        long phone = 15586531956L;
        Order successKilled = orderMapper.queryByIdWithSeckill(id, phone);
        log.info("{}", successKilled);
        log.info("SuccessKilled{}", successKilled.getCommodity());
    }

    @Test
    public void queryOrderByCommodityId() {
        Integer commodityId = 1001;
        Long userPhone = 15586531956L;
        Order result = orderMapper.queryOrderByCommodityId(commodityId,userPhone);
        log.info("订单详情:{}",result);
    }
}