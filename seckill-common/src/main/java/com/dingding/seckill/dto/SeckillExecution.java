package com.dingding.seckill.dto;

import com.dingding.seckill.entity.Order;
import com.dingding.seckill.enums.SeckillStateEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 封装秒杀结束后的结果
 *
 * @author liudingding
 */
@Data
@NoArgsConstructor
public class SeckillExecution implements Serializable {

    /**
     * 成功秒杀的订单id
     */
    private Integer orderId;

    /**
     * 秒杀执行结果状态
     */
    private int state;

    /**
     * 状态表示
     */
    private String stateInfo;

    /**
     * 成功秒杀对象
     */
    private Order order;

    /**
     * 成功秒杀后的执行结果
     *
     * @param orderId
     * @param stateEnum
     * @param order
     */
    public SeckillExecution(Integer orderId, SeckillStateEnum stateEnum, Order order) {
        this.orderId = orderId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.order = order;
    }

    /**
     * 失败秒杀后的执行结果
     * orderId = commodityId = userPhone.intValue();
     * @param orderId
     * @param stateEnum
     */
    public SeckillExecution(Integer orderId, SeckillStateEnum stateEnum) {
        this.orderId = orderId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

}
