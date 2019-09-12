package com.dingding.seckill.enums;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-11
 */

public enum HandleSeckillEnum {
    /**
     * 秒杀处理中
     */
    HANDLING(2,"正在处理您的订单");

    private int state;

    private String stateInfo;

    HandleSeckillEnum(int state,String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}
