package com.dingding.seckill.api.order;

import com.dingding.seckill.entity.Order;
import com.dingding.seckill.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-06
 */
public interface OrderServiceApi {

    /**
     * 创建订单
     * @param user
     * @return
     */
    int createOrder(User user);

    /**
     * 根据id查询订单结果并携带商品品对象实体
     * @param commodityId
     * @param userPhone
     * @return
     */
    Order queryByIdWithSeckill(@Param("commodityId") Integer commodityId, @Param("userPhone") Long userPhone);

    /**
     * 分布式事务插入成功与否都返回-1、不知道如何解决
     * 因此我们根据 commodityId 查出 orderId 看是否为空来判断是否重复秒杀
     * @param commodityId
     * @param userPhone
     * @return
     */
    Order queryOrderByCommodityId(@Param("commodityId") Integer commodityId,@Param("userPhone") Long userPhone);

}
