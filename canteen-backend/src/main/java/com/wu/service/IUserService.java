package com.wu.service;

import com.wu.controller.dto.UserDTO;
import com.wu.controller.dto.UserPasswordDTO;
import com.wu.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author NaHCO3
 * @since 2023-04-26
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(@Param("userPasswordDTO") UserPasswordDTO userPasswordDTO);
}
