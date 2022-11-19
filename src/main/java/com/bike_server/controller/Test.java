package com.bike_server.controller;

import com.bike_server.dao.UserMapper;
import com.bike_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @Autowired
    UserService userService;

    @RequestMapping("/test")
    public String test01(){

        System.out.println(userService.getAllUser());
        return "test!";
    }
}
