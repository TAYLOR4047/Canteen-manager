package com.wu.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wu.entity.User;
import com.wu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper,User> {
    @Autowired
    private UserMapper userMapper;

    public boolean saveService(User user) {
        if(user.getId()==null){
            return super.save(user);
        }else{
            return super.updateById(user);
        }
    }

/*    public int save(User user) {
        if(user.getId()==null)
            return userMapper.insert(user);
        else
            return userMapper.update(user);
    }*/

}
