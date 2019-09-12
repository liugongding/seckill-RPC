package com.dingding.seckill.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 订单服务实体类
 * @Author liugongding
 * @Date 2019-09-04
 */
@Data
public class Order implements Serializable {

    /**
     * 订单id
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
     * 用户电话
     */
    private Long userPhone;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 一个商品
     */
    private Commodity commodity;
}
