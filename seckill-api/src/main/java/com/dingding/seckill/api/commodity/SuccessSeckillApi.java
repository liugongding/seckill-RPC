package com.dingding.seckill.api.commodity;

import com.dingding.seckill.entity.User;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-06
 */
public interface SuccessSeckillApi {

    /**
     * 减库存操作
     * @param user
     */
     void reduceStock(User user) ;
}
