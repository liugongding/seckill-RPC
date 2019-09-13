package com.dingding.seckill.dto;

import com.dingding.seckill.enums.HandleSeckillEnum;
import com.dingding.seckill.enums.SeckillStateEnum;
import lombok.Data;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-11
 */

@Data
public class SeckillStatusExecution {

    /**
     * 商品id
     */
    private Integer commodityId;

    /**
     * 用户电话
     */
    private Long userPhone;

    /**
     * 秒杀执行结果状态
     */
    private Integer state;
    /**
     * 状态表示
     */
    private String stateInfo;

    /**
     * 订单正在处理中
     *
     * @param commodityId
     * @param userPhone
     * @param handleSeckillEnum
     */
    public SeckillStatusExecution(Integer commodityId, Long userPhone, HandleSeckillEnum handleSeckillEnum) {
        this.commodityId = commodityId;
        this.userPhone = userPhone;
        this.state = handleSeckillEnum.getState();
        this.stateInfo = handleSeckillEnum.getStateInfo();
    }

    /**
     * 重复秒杀状态
     *
     * @param commodityId
     * @param userPhone
     * @param seckillStateEnum
     */
    public SeckillStatusExecution(Integer commodityId, Long userPhone, SeckillStateEnum seckillStateEnum) {
        this.commodityId = commodityId;
        this.userPhone = userPhone;
        this.state = seckillStateEnum.getState();
        this.stateInfo = seckillStateEnum.getStateInfo();
    }
}
