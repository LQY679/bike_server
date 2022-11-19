package com.bike_server.dao;

import com.bike_server.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> getAllUser();

    User getUserById(String uid);

    int addUser(User user);

    int deleteUser(String uid);

    int updateUser(User user);

}
