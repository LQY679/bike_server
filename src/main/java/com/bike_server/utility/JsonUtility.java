package com.bike_server.utility;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bike_server.pojo.User;

import java.lang.reflect.Field;
import java.util.List;

/*
转换JSON的工具类
 */
public class JsonUtility {

    /*
       json对象和数组, 整个类共享,类似单例设计模式,可以暴露用于再加工
        而不用担心受限于返回字符方法而无法对JSONObject对象进行再次加工
     */
    public static JSONObject jsonObject = new JSONObject();
    public static JSONArray jsonArray = new JSONArray();

    /*
        将对象转换成JSON字符串
     */
    public static String toJSONStringByObject(Object o) throws IllegalAccessException {
        jsonObject.clear(); // 清理缓存
        for (Field field:o.getClass().getDeclaredFields()) {
                field.setAccessible(true);   // 设置字段为可更改
                jsonObject.put(field.getName(),field.get(o));
        }
        return jsonObject.toJSONString();
    }

    public static String toJSONStringByList(List list) throws IllegalAccessException {
        jsonArray.clear(); // 清理缓存
        for (Object element:list) {
            jsonArray.add(element);
        }
        return jsonArray.toJSONString();
    }
}
