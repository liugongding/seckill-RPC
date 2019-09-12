package com.dingding.seckill.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dingding.seckill.entity.User;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-07-26
 */
public class FastJsonConvertUtil {
    public static User convertJSONToObject(String message, Object type){
        User user = JSON.parseObject(message, new TypeReference<User>() {});
        return user;
    }

    public static String convertObjectToJSON(Object obj){
        String text = JSON.toJSONString(obj);
        return text;
    }
}
