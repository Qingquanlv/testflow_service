package com.github.qingquanlv.testops_service_biz.utilities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/11/20 下午2:19
 */
public class FastJsonUtil {

    public static <T> T toBean(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public static <T> List<T> toListBean(String text, Class<T> clazz) {
        return JSON.parseArray(text,clazz);
    }

    public static String toJson(Object beanClass) {
        return JSON.toJSONString(beanClass);
    }

    public static HashMap<String, String> toMap(String text) {
        JSONObject json = JSONObject.parseObject(text);
        return JSONObject.parseObject(null == json ? null : json.toString(), HashMap.class);
    }

}

