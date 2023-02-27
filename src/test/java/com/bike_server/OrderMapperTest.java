package com.bike_server;

import com.bike_server.dao.OrderMapper;
import com.bike_server.pojo.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Date;
import java.util.List;

/**
 *  日期查询测试不通过
 */
@SpringBootTest
class OrderMapperTest {
    @Autowired
    OrderMapper orderMapper;
    @Test
    public void testAdd(){
        for (int i=6; i<10; i++){
            Date date = new Date();
            String s = "" + i;
            Order order = new Order(
                    "u1001"+"-b100"+s+"-"+date.getTime(),
                    "888888",
                    Order.AliPay,
                    0,
                    Order.BeIng,
                    date,
                    date
            );
            orderMapper.addOrder(order);
        }

    }

    @Test
    public void test_deleteOrder(){
        if (orderMapper.deleteOrder("u1008-b1008-1677137130678") == 0){
            System.err.println("删除失败,该订单不存在!");
        } else {
            System.out.println("订单删除成功!");
        }
    }

    @Test
    public void test_updateOrder(){
        Date date = new Date();
        Order order = new Order(
                "u1001-b1001-1677137130325",
                "1010101010",
                Order.AliPay,
                10,
                Order.Finished,
                date,
                date
        );
        if (orderMapper.updateOrder(order) == 0 ){
            System.err.println("修改失败!该数据可能不存在");
        }else {
            System.out.println("修改成功!");
        }
    }

    @Test
    public void test_getAllOrder(){
        List<Order> list = orderMapper.getAllOrder();
        if (list.isEmpty()){
            System.err.println("查询为空!");
        }else {
            list.forEach(System.out::println);
        }
    }

    @Test
    public void test_getOrderByTrade_type(){
        List<Order> list = orderMapper.getOrderByTrade_type(Order.WeChatPay);
        if (list.isEmpty()){
            System.err.println("查询为空!");
        }else {
            list.forEach(System.out::println);
        }
    }

    @Test
    public void test_getOrderByOrder_status(){
        List<Order> list = orderMapper.getOrderByOrder_status(Order.Finished);
        if (list.isEmpty()){
            System.err.println("查询为空!");
        }else {
            list.forEach(System.out::println);
        }
    }


    @Test
    public void test_getOrderByCondition(){
        String condition = "start_time <= 5.21 and order_status = 'Wait_Pay'" ;
        List<Order> list = orderMapper.getOrderByCondition(condition);
        if (list.isEmpty()){
            System.err.println("查询为空!");
        }else {
            list.forEach(System.out::println);
        }
    }

    @Test
    public void test_getOrderByOrder_id(){
        System.out.println(orderMapper.getOrderByOrder_id("u1001-b1001-1677138162002"));
    }

    @Test
    public void test_getOrderByTrade_no(){
        System.out.println(orderMapper.getOrderByTrade_no("1010101010"));
    }


}
