package com.bike_server;

import com.bike_server.dao.UserMapper;
import com.bike_server.pojo.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.util.List;

@SpringBootApplication
public class BikeServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BikeServerApplication.class, args);

    }

}
