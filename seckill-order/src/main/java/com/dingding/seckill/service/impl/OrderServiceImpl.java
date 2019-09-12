package com.dingding.seckill.service.impl;

import com.dingding.seckill.api.order.OrderServiceApi;
import com.dingding.seckill.dao.OrderMapper;
import com.dingding.seckill.entity.Order;
import com.dingding.seckill.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-06
 */

@Service(interfaceClass = OrderServiceApi.class)
@Slf4j
public class OrderServiceImpl implements OrderServiceApi {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public int createOrder(User user) {
        Order order = new Order();
        order.setCommodityId(user.getCommodityId());
        order.setUserPhone(user.getUserPhone());
        int insertCount;
        try {
             insertCount = orderMapper.insertOrder(order);
        } catch (Exception e) {
            throw e;
        }
        return insertCount;
    }

    @Override
    public Order queryByIdWithSeckill(Integer  commodityId, Long userPhone) {
        return orderMapper.queryByIdWithSeckill(commodityId,userPhone);
    }

    @Override
    public Order queryOrderByCommodityId(Integer commodityId, Long userPhone) {
        return orderMapper.queryOrderByCommodityId(commodityId,userPhone);
    }

}
