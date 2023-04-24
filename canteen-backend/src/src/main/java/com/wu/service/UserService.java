package com.wu.service;

import com.wu.entity.User;
import com.wu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public int save(User user) {
        if(user.getId()==null)
            return userMapper.insert(user);
        else
            return userMapper.update(user);
    }
}
