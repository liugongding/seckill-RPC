package com.dingding.seckill.service.impl;

import com.dingding.seckill.OrderApplication;
import com.dingding.seckill.api.order.OrderServiceApi;
import com.dingding.seckill.entity.Order;
import com.dingding.seckill.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-06
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
@Slf4j
public class OrderServiceImplTest {

    @Reference
    private OrderServiceApi orderService;

    @Test
    public void createOrder() {
        User user = User.builder()
                .userPhone(155865319256L)
                .commodityId(1002)
                .build();
        int insertCount = orderService.createOrder(user);
        if (insertCount <= 0) {
            log.info("重复下单：{}", insertCount);
        } else {
            log.info("订单状态：{}", insertCount);
        }

    }

    @Test
    public void queryByIdWithSeckill() {
        Integer id = 1002;
        Long phone = 15586531956L;
        Order order = orderService.queryByIdWithSeckill(id,phone);
        log.info("订单结果：{}",order);
    }

    @Test
    public void queryOrderByCommodityId() {
        Integer commodityId = 1001;
        Long userPhone = 15586531956L;
        Order result = orderService.queryOrderByCommodityId(commodityId,userPhone);
        log.info("订单详情:{}",result);
    }
}