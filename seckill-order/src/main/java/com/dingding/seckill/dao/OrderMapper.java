package com.dingding.seckill.dao;

import com.dingding.seckill.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-04
 */
@Mapper
public interface OrderMapper {

    /**
     * 根据用户id和手机号插入购买明细，可过滤重复
     * 如果出现主键冲突，返回0
     * @param order
     * @return
     */
    int insertOrder(Order order);

    /**
     * 根据id查询SuccessKilled并携带秒杀产品对象实体
     * @param commodityId
     * @param userPhone
     * @return
     */
    Order queryByIdWithSeckill(@Param("commodityId") Integer commodityId, @Param("userPhone") Long userPhone);

    /**
     * 分布式事务插入成功与否都返回-1、不知道如何解决
     * 因此我们根据 commodityId 查出 Order 看是否为空来判断是否重复秒杀
     * @param commodityId
     * @param userPhone
     * @return
     */
    Order queryOrderByCommodityId(@Param("commodityId") Integer commodityId,@Param("userPhone") Long userPhone);
}
