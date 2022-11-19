package com.bike_server;

import com.bike_server.dao.BikeMapper;
import com.bike_server.dao.UserMapper;
import com.bike_server.pojo.Bike;
import com.bike_server.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.lang.reflect.Field;
import java.util.List;

@SpringBootTest
class BikeServerApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Autowired
    BikeMapper bikeMapper;

    @Test
    void testMybatis() {
        List<User> userList =  userMapper.getAllUser();
        System.out.println(userMapper.getUserById("u1000"));

    }

    @Test
    public void testBikeMapper(){
//        List<Bike> list = bikeMapper.getAllBike();
//        for (Bike bike:list) {
//            System.out.println(bike);
//        }

        System.out.println(bikeMapper.getBikeById("b1001"));
//        System.out.println(bikeMapper.deleteBike("b1000"));
    }




}
