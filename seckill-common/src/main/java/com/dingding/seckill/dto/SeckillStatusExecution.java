package com.dingding.seckill.dto;

import com.dingding.seckill.enums.HandleSeckillEnum;
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

    public SeckillStatusExecution(Integer commodityId, Long userPhone, HandleSeckillEnum handleSeckillEnum){
        this.commodityId = commodityId;
        this.userPhone = userPhone;
        this.state = handleSeckillEnum.getState();
        this.stateInfo = handleSeckillEnum.getStateInfo();
    }
}
