package com.dingding.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    /**
     * 商品id
     */
    private Integer commodityId;


    /**
     * 用户id
     */
    private Long userPhone;
}
