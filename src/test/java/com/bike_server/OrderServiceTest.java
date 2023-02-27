package com.bike_server;

import com.bike_server.pojo.Order;
import com.bike_server.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Test
    public void test_getUnPaidOrderByUid(){
        System.out.println(orderService.getUnFinishOrderByUid("u1009"));
    }
}
