package com.dingding.seckill.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-09
 */
@Data
public class BaseResponse<T> implements Serializable {
    private boolean success;
    private T data;
    private String error;

    public BaseResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public BaseResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
    }
}
