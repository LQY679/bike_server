package com.bike_server.junit;


import com.bike_server.pojo.Order;
import com.bike_server.utility.JsonUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class Test {

    JsonUtility ju = new JsonUtility();
    ObjectMapper mapper = ju.mapperFactory(true, true);

    @org.junit.jupiter.api.Test
    public void t(){
        System.out.println(null == null);
    }

    @org.junit.jupiter.api.Test
    public void test_createOrder_data() {
        Order order = new Order();
        Date date = new Date();
        order.setOrder_id("u1001-b1001-"+date.getTime());
        order.setOrder_status(Order.BeIng);
        order.setStart_time(date);
        System.out.println(ju.jsonParser_ByObject(mapper, order));

    }
    @org.junit.jupiter.api.Test
    public void test_stopOrder_data(){
        Order order = new Order();
        Date date = new Date();
        order.setOrder_id("u1001-b1001-1677251427153");
        order.setOrder_status(Order.WaitPay);
        order.setAmount(5.2);
        order.setEnd_time(date);
        System.out.println(ju.jsonParser_ByObject(mapper, order));
    }

    @org.junit.jupiter.api.Test
    public void test_goPay_data(){
        Order order = new Order();
        Date date = new Date();
        order.setOrder_id("u1001-b1001-1677251427153");
        order.setAmount(5.2);
        System.out.println(ju.jsonParser_ByObject(mapper, order));
    }
}
