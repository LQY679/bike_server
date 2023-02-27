package com.bike_server.service;

import com.bike_server.dao.UserMapper;
import com.bike_server.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserMapper{
    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    public User getUserById(String uid) {
        return userMapper.getUserById(uid);
    }

    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    public int deleteUser(String uid) {
        return userMapper.deleteUser(uid);
    }

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

}
