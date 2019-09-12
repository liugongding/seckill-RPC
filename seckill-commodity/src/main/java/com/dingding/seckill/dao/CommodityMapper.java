package com.dingding.seckill.dao;

import com.dingding.seckill.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-04
 */
@Mapper
public interface CommodityMapper {

    /**
     * 减库存
     * @param commodityId
     * @param killTime
     * @return 如果受影响的行数 > 1, 表示更新记录行数
     */
    int  reduceNumber(@Param("commodityId") Integer commodityId, @Param("killTime") Date killTime);

    /**
     * 根据id查询秒杀对象
     * @param commodityId
     * @return
     */
    Commodity queryById(long commodityId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @return
     */
    List<Commodity> queryAll();
}
