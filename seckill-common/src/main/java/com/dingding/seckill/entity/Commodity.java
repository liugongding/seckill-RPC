package com.dingding.seckill.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 商品服务实体类
 * @Author liugongding
 * @Date 2019-09-04
 */
@Data
public class Commodity implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer commodityId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品库存
     */
    private Integer number;

    /**
     * 秒杀开始时间
     */
    private Date startTime;

    /**
     * 秒杀结束时间
     */
    private Date endTime;

    /**
     * 商品秒杀活动创建时间
     */
    private Date createTime;
}
