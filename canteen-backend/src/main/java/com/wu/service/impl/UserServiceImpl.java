package com.wu.service.impl;

import com.wu.entity.User;
import com.wu.mapper.UserMapper;
import com.wu.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author NaHCO3
 * @since 2023-04-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    public boolean saveService(User user) {
        if(user.getId()==null){
            return super.save(user);
        }else{
            return super.updateById(user);
        }
    }
}
