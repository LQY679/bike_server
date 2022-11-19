package com.bike_server.unit;

import com.alibaba.fastjson.JSONObject;
import com.bike_server.dao.UserMapper;
import com.bike_server.pojo.User;
import com.bike_server.service.UserService;
import com.bike_server.utility.JsonUtility;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Test {
    @org.junit.jupiter.api.Test
    public void test1(){
        JSONObject jsonObject = new JSONObject();
        User data= new User("1000","11", "admin");
//        for (Field field:user.getClass().getDeclaredFields()) {
////            System.out.println(field.getName());
//            try {
//                field.setAccessible(true);  //
//                jsonObject.put(field.getName(),field.get(user));
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//        JSONObject object = new JSONObject();
//        object.put("data",jsonObject);
//        object.put("提示信息:","你好");
//        System.out.println(object);
        JSONObject json = new JSONObject();
        try {
            JsonUtility.toJSONStringByObject(data);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        json.put("data",JsonUtility.jsonObject);
        if (data == null){
            json.put("type","err");
        }
        else {
            json.put("type","success");
        }
        System.out.println(JSONObject.toJSONString(json));


    }
    @Autowired
    UserMapper userMapper;
    @org.junit.jupiter.api.Test
    public void test2(){
     System.out.println(userMapper);
     List<User> list = userMapper.getAllUser();
        for (User user:list) {
            System.out.println(user);
        }

    }
}
