package com.dingding.seckill.dao;

import com.dingding.seckill.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-09
 */
@Mapper
public interface CommodityTestMapper {

    /**
     * 发放商品
     * @param commodity
     * @return
     */
    int insertCommodity(Commodity commodity);
}
