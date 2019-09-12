package com.dingding.seckill.util;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-07-25
 */
public class RandomUtil {

    /**
     * 入参 n 代表随机数的位数
     * @param n
     * @return
     */
    public static Long getRandomNumber(int n) {
        if(n<1){
            throw new IllegalArgumentException("随机数位数必须大于0");
        }
        return (long)(Math.random()*9*Math.pow(10,n-1)) + (long)Math.pow(10,n-1);
    }
}
