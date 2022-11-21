package com.bike_server.controller;

import com.alibaba.fastjson.JSONObject;
import com.bike_server.dao.UserMapper;
import com.bike_server.pojo.User;
import com.bike_server.utility.JsonUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/getAllUser")
    public String getAllUser(){
        JSONObject jsonData = new JSONObject();   // 返回给前端的JSON对象数据
        List<User> userList ;
        try {
            userList = userMapper.getAllUser();
            if (userList.isEmpty()){
                jsonData.put("msg","false");
            }
            else{
                jsonData.put("msg","true");
                JsonUtility.toJSONStringByList(userList);
                jsonData.put("data",JsonUtility.jsonArray);
            }
        } catch (Exception e) {
            jsonData.put("msg","err");
            e.printStackTrace();
        }
        return JSONObject.toJSONString(jsonData);
    }

    @RequestMapping("/getUserById/{uid}")
    public String getUserById(@PathVariable("uid") String uid){
        JSONObject jsonData = new JSONObject();   // 返回给前端的JSON对象数据
        User data;  // 数据库查询到的数据
        try {
            data = userMapper.getUserById(uid);
            if (data == null){
                jsonData.put("msg","false");
            }
            else{
                jsonData.put("msg","true");
                JsonUtility.toJSONStringByObject(data);
                jsonData.put("data",JsonUtility.jsonObject);
            }
        } catch (Exception e) {
            jsonData.put("msg","err");
            e.printStackTrace();
        }
        return JSONObject.toJSONString(jsonData);
    }

    @DeleteMapping(value = "/deleteUser/{uid}")
    public String deleteUser(@PathVariable String uid){
        JSONObject jsonData = new JSONObject();   // 返回给前端的JSON对象数据
        try{
            int n = userMapper.deleteUser(uid);
            if (n==0){
                jsonData.put("msg","false");
            }
            else {
                jsonData.put("msg","true");
            }
        }catch (Exception e){
            jsonData.put("msg","err");
            e.printStackTrace();
        }
        return JSONObject.toJSONString(jsonData);
    }

    @PostMapping(value = "/addUser")
    public String addUser(@RequestBody User user){
        JSONObject jsonData = new JSONObject();   // 返回给前端的JSON对象数据
        try {
            int n = userMapper.addUser(user);
            if (n == 0){
                jsonData.put("msg","false");
            }
            else {
                jsonData.put("msg","true");
            }
        }catch (Exception e){
            jsonData.put("msg","err");
            e.printStackTrace();
        }
        return JSONObject.toJSONString(jsonData);
    }

    @PutMapping(value = "/updateUser")
    // axios 中, 使用data 传参 , 必须用@RequestBody接收, 且参数是一个实体类
    public String update(@RequestBody User user){
        JSONObject jsonData = new JSONObject();   // 返回给前端的JSON对象数据
        try {
            int n = userMapper.updateUser(user);
            if (n == 0){
                jsonData.put("msg","false");
            }
            else {
                jsonData.put("msg","true");
            }
        }catch (Exception e){
            jsonData.put("msg","err");
            e.printStackTrace();
        }
        return JSONObject.toJSONString(jsonData);
    }


}
