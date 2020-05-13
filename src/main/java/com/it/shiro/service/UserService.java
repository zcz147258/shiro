package com.it.shiro.service;

import com.it.shiro.Bean.User;
import com.it.shiro.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public User getUser(String username){
        return userMapper.getUser(username);
    }
}
