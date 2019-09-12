package com.dingding.seckill.dao;

import com.dingding.seckill.entity.BrokerMessageLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-08-28
 */
@Mapper
public interface BrokerMessageLogMapper {
    /**
     * 查询消息状态为0(发送中)，且已经超时的消息集合
     * @return
     */
//    List<BrokerMessageLog> queryStatusAndTimeoutMessage();

    /**
     * 重新发送消息
     * @param messageId
     * @param updateTime
     */
//    void updateReSendCount(@Param("messageId") String messageId, @Param("updateTime") Date updateTime);

    /**
     * 更新最终消息发送结果
     * * 成功 or 失败
     * @param messageId
     * @param status
     * @param updateTime
     */
    void changeBrokerMessageLogStatus(@Param("messageId") String messageId, @Param("status") String status, @Param("updateTime") Date updateTime);

    /**
     * 插入秒杀记录
     * @param brokerMessageLog
     * @return
     */
    int insertBrokerMessage(BrokerMessageLog brokerMessageLog);
}
