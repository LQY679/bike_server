package com.bike_server.controller;

import com.alibaba.fastjson.JSONObject;
import com.bike_server.dao.UserMapper;
import com.bike_server.pojo.Result;
import com.bike_server.pojo.User;
import com.bike_server.utility.JsonUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 旧接口 没有实现 Result 标准!!!
 */

@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    JsonUtility ju = new JsonUtility();
    ObjectMapper mapper = ju.mapperFactory();


    @RequestMapping("/getAllUser")
    public String getAllUser(){
        Result result = new Result();
        List<User> userList;
        try {
            userList = userMapper.getAllUser();
            if (userList.isEmpty()){
                result.setMsg("false");
                result.setCode(2000);
                result.setSub("fail");
            }
            else{
                result.setMsg("true");
                result.setData(userList);
            }
        } catch (Exception e) {
            result.setMsg("err");
            result.setCode(4000);
            result.setSub("error");
            e.printStackTrace();
        }
        return ju.jsonParser_ByObject(mapper, result);
    }

    @GetMapping("/getUserById/{uid}")
    public String getUserById(HttpServletRequest request, @PathVariable("uid") String uid){
        Result result = new Result();
        User data;  // 数据库查询到的数据
        try {
            data = userMapper.getUserById(uid);
            if (data == null){
                result.setMsg("false");
                result.setCode(2000);
                result.setSub("fail");
            }
            else{
                result.setMsg("true");
                result.setData(data);
                JsonNode jsonNode = ju.readObjectToNode(mapper, result);
                ju.setAttribute((ObjectNode) jsonNode, "sessionID", request.getSession().getId());
                return jsonNode.toString();
            }
        } catch (Exception e) {
            result.setMsg("err");
            result.setCode(4000);
            result.setSub("error");
            e.printStackTrace();
        }
        return ju.jsonParser_ByObject(mapper, result);
    }

    @DeleteMapping(value = "/deleteUser/{uid}")
    public String deleteUser(@PathVariable String uid){
        Result result = new Result();
        try{
            int n = userMapper.deleteUser(uid);
            if (n==0){
                result.setMsg("false");
                result.setCode(2000);
                result.setSub("fail");
            }
            else {
                result.setMsg("true");
            }
        }catch (Exception e){
            result.setMsg("err");
            result.setCode(4000);
            result.setSub("error");
            e.printStackTrace();
        }
        return ju.jsonParser_ByObject(mapper, result);
    }

    @PostMapping(value = "/addUser")
    public String addUser(@RequestBody User user){
        // axios 中, 使用data 传参 , 必须用@RequestBody接收, 且参数是一个实体类
        Result result = new Result();
        try {
            int n = userMapper.addUser(user);
            if (n == 0){
                result.setMsg("false");
                result.setCode(2000);
                result.setSub("fail");
            }
            else {
                result.setMsg("true");
            }
        }catch (Exception e){
            result.setMsg("err");
            result.setCode(4000);
            result.setSub("error");
            System.err.println("数据:"+user.toString()+"插入失败!");
        }
        return ju.jsonParser_ByObject(mapper, result);
    }

    @PutMapping(value = "/updateUser")
    // axios 中, 使用data 传参 , 必须用@RequestBody接收, 且参数是一个实体类
    public String update(@RequestBody User user){
        Result result = new Result();
        try {
            int n = userMapper.updateUser(user);
            if (n == 0){
                result.setMsg("false");
                result.setCode(2000);
                result.setSub("fail");
            }
            else {
                result.setMsg("true");
            }
        }catch (Exception e){
            result.setMsg("err");
            result.setCode(4000);
            result.setSub("error");
            System.err.println("数据:"+user.toString()+"修改失败!原因: 可能该数据并不存在!");
        }
        return ju.jsonParser_ByObject(mapper, result);
    }


}
