package com.dingding.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liudingding
 * @ClassName BrokerMessageLog
 * @description mq消息确认机制的broker
 * @date 2019/6/24 16:35
 * Version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrokerMessageLog implements Serializable {

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 消息id
     */
    private Long messageId;

    /**
     * 消息体
     */
    private String message;

    /**
     * 重试次数
     */
    private Integer tryCount;

    /**
     * 最终状态
     */
    private String status;

    /**
     * 消息开始重试时间
     */
    private Date nextRetry;

    /**
     * brokerLog创建时间
     */
    private Date createTime;

    /**
     * brokerLog更新时间
     */
    private Date updateTime;

}
