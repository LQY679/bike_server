package com.bike_server.controller;

import com.alibaba.fastjson.JSONObject;
import com.bike_server.dao.BikeMapper;
import com.bike_server.pojo.Bike;
import com.bike_server.utility.JsonUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BikeController {
    @Autowired
    BikeMapper bikeMapper;

    @RequestMapping("/getAllBike")
    public String getAllBike(){
        JSONObject jsonData = new JSONObject();   // 返回给前端的JSON对象数据
        List<Bike> bikeList ;
        try {
            bikeList = bikeMapper.getAllBike();
            if (bikeList.isEmpty()){
                jsonData.put("msg","false");
            }
            else{
                jsonData.put("msg","true");
                JsonUtility.toJSONStringByList(bikeList);
                jsonData.put("data",JsonUtility.jsonArray);
            }
        } catch (Exception e) {
            jsonData.put("msg","err");
            e.printStackTrace();
        }
        return JSONObject.toJSONString(jsonData);
    }

    @RequestMapping("/getBikeById/{id}")
    public String getBikeById(@PathVariable String id){
        JSONObject jsonData = new JSONObject();   // 返回给前端的JSON对象数据
        Bike data ;
        try {
            data = bikeMapper.getBikeById(id);
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

    @PutMapping(value = "/addBike")
    public String addBike(@RequestBody Bike bike){
        JSONObject jsonData = new JSONObject();   // 返回给前端的JSON对象数据
        try {
            int n = bikeMapper.addBike(bike);
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

    @DeleteMapping(value = "/deleteBike/{id}")
    public String deleteBike(@PathVariable String id){
        JSONObject jsonData = new JSONObject();   // 返回给前端的JSON对象数据
        try{
            int n = bikeMapper.deleteBike(id);
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
    
    
    @PutMapping(value = "/updateBike")
    public String update(@RequestBody Bike bike){
        JSONObject jsonData = new JSONObject();   // 返回给前端的JSON对象数据
        try {
            int n = bikeMapper.updateBike(bike);
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
