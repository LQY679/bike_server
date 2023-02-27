package com.bike_server.controller;

import com.bike_server.pojo.Order;
import com.bike_server.pojo.Result;
import com.bike_server.service.AliPayService;
import com.bike_server.service.OrderService;
import com.bike_server.utility.JsonUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    AliPayService aliPayService;

    JsonUtility ju = new JsonUtility();
    ObjectMapper objectMapper = ju.mapperFactory(true, true);

    @PostMapping("/createOrder")
    public String createOrder(@RequestBody Order order){
        Result result = new Result();  // 初始化对象默认 coed , sub 为业务成功状态
        Boolean state = null;
        try {
            state = orderService.createOrder(order.getOrder_id(), order.getStart_time());
        }catch (Exception e){
            result.setCode(3000);
            result.setSub("error");
            result.setMsg("非法参数!");
            e.printStackTrace();
            return ju.jsonParser_ByObject(objectMapper, result);
        }
        if (state == null || !state){
            result.setCode(2000);
            result.setSub("fail");
            result.setMsg("创建订单失败!可能该订单已经存在!");
        }
        else if(state) {
            result.setMsg("创建订单成功!");
        }
        else {
//            result.setCode(2000);
//            result.setSub("fail");
//            result.setMsg("创建订单失败!");
        }
        return ju.jsonParser_ByObject(objectMapper, result);
    }

    @PostMapping("/stopOrder")
    public String stopOrder(@RequestBody Order order){
        Result result = new Result();  // 初始化对象默认 coed , sub 为业务成功状态
        Boolean state = null;
        try {
            state = orderService.stopOrder(order.getOrder_id(), order.getAmount(), order.getEnd_time());
        }catch (Exception e){
            result.setCode(3000);
            result.setSub("error");
            result.setMsg("非法参数!");
            e.printStackTrace();
            return ju.jsonParser_ByObject(objectMapper, result);
        }
        if (state == null){
            result.setCode(4000);
            result.setSub("error");
            result.setMsg("停止订单出现异常!请稍后重试");
        }
        else if(state) {
            result.setMsg("停止订单成功!");
        }
        else {
            result.setCode(2000);
            result.setSub("fail");
            result.setMsg("停止订单失败!");
        }
        return ju.jsonParser_ByObject(objectMapper, result);
    }

    @GetMapping("/deleteOrder")
    public String deleteOrder(@RequestParam String order_id){
        Result result = new Result();  // 初始化对象默认 coed , sub 为业务成功状态
        int rows = 0;
        try {
            rows = orderService.deleteOrder(order_id);
        }catch (Exception e){
            result.setCode(4000);
            result.setSub("error");
            result.setMsg("删除订单出现异常!请稍后重试");
        }
        if (rows == 0){
            result.setCode(2000);
            result.setSub("fail");
            result.setMsg("该订单不存在!");
        }
        else{
            result.setMsg("删除订单成功!");
        }
        return ju.jsonParser_ByObject(objectMapper, result);
    }

    @GetMapping("/getAllOrder")
    public String getAllOrder(){
        Result result = new Result();
        // 注意! 这里哪怕查询为空也认为是业务成功! 查询为空返回空列表而不是null
        List list = orderService.getAllOrder();
        if (list == null || list.isEmpty()){
            result.setCode(2000);
            result.setSub("fail");
            result.setMsg("查询全部订单失败!请检查服务后重试!");
        }else {
            result.setData(list);
        }
        return ju.jsonParser_ByObject(objectMapper, result);
    }

    @GetMapping("/getOrderByOrder_id")
    public String getOrderByOrder_id(@RequestParam String order_id){
        Order order = orderService.getOrderByOrder_id(order_id);
        Result result = new Result();
        if(order!=null){
            result.setMsg("根据订单编号查询订单成功!");
            result.setData(order);
        }
        else {
            result.setCode(2000);
            result.setSub("fail");
            result.setMsg("查询失败! 该订单不存在!");
        }
        return ju.jsonParser_ByObject(objectMapper, result);
    }

    @GetMapping("/getUnFinishOrderByUid")
    public String getUnPaidOrderByUid(@RequestParam String uid){
        Result result = new Result();
        Order order = orderService.getUnFinishOrderByUid(uid);
        if (order == null) {
            result.setCode(2000);
            result.setSub("fail");
            result.setMsg("获取失败,该用户不存在未完成订单!");
        }
        else {
            result.setData(order);
            result.setMsg("获取成功!该用户存在未支付订单!");
        }
        return ju.jsonParser_ByObject(objectMapper, result);
    }
}
