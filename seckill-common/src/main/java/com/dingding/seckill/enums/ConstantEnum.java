package com.dingding.seckill.enums;

import java.io.Serializable;

/**
 * 常量枚举
 */
public enum ConstantEnum implements Serializable {
    /**
     * 发送中
     */
    MESSAGE_SENDING("0"),
    /**
     * 发送成功
     */
    MESSAGE_SEND_SUCCESS("1"),
    /**
     * 发送失败
     */
    MESSAGE_SEND_FALURE("2"),
    /**
     * 发送超时 单位：min
     */
    MESSAGE_SEND_TIMEOUT(1);

    private String status;
    private Integer timeout;

    ConstantEnum(String status) {
        this.status = status;
    }

    ConstantEnum(Integer timeout) {
        this.timeout = timeout;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
