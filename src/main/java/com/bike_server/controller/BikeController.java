package com.bike_server.controller;

import com.bike_server.dao.BikeMapper;
import com.bike_server.pojo.Bike;
import com.bike_server.pojo.Result;
import com.bike_server.utility.JsonUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 旧接口, 未实现Result标准
 */

@RestController
public class BikeController {
    @Autowired
    BikeMapper bikeMapper;

    JsonUtility ju = new JsonUtility();
    ObjectMapper mapper = ju.mapperFactory();

    @RequestMapping("/getAllBike")
    public String getAllBike(){
        Result result = new Result();
        List<Bike> bikeList ;
        try {
            bikeList = bikeMapper.getAllBike();
            if (bikeList.isEmpty()){
                result.setMsg("false");
                result.setCode(2000);
                result.setSub("fail");
            }
            else{
                result.setMsg("true");
                result.setData(bikeList);
            }
        } catch (Exception e) {
            result.setMsg("err");
            result.setCode(4000);
            result.setSub("error");
            e.printStackTrace();
        }
        return ju.jsonParser_ByObject(mapper, result);
    }

    @RequestMapping("/getBikeById/{id}")
    public String getBikeById(@PathVariable String id){
        Result result = new Result();
        Bike data ;
        try {
            data = bikeMapper.getBikeById(id);
            if (data == null){
                result.setMsg("false");
                result.setCode(2000);
                result.setSub("fail");
            }
            else{
                result.setMsg("true");
                result.setData(data);
            }
        } catch (Exception e) {
            result.setMsg("err");
            result.setCode(4000);
            result.setSub("error");
            e.printStackTrace();
        }
        return ju.jsonParser_ByObject(mapper, result);
    }

    @PutMapping(value = "/addBike")
    public String addBike(@RequestBody Bike bike){
        Result result = new Result();
        try {
            int n = bikeMapper.addBike(bike);
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
            System.err.println("数据:"+bike.toString()+"插入失败!可能该数据已经存在");
        }
        return ju.jsonParser_ByObject(mapper, result);
    }

    @DeleteMapping(value = "/deleteBike/{id}")
    public String deleteBike(@PathVariable String id){
        Result result = new Result();
        try{
            int n = bikeMapper.deleteBike(id);
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
    
    
    @PutMapping(value = "/updateBike")
    public String update(@RequestBody Bike bike){
        Result result = new Result();
        try {
            int n = bikeMapper.updateBike(bike);
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
            System.err.println("数据:"+bike.toString()+"修改失败!原因: 可能该数据并不存在!");
        }
        return ju.jsonParser_ByObject(mapper, result);
    }
}
