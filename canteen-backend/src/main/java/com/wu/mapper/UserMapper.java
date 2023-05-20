package com.wu.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wu.controller.dto.UserPasswordDTO;
import com.wu.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author NaHCO3
 * @since 2023-04-26
 */
public interface UserMapper extends BaseMapper<User> {
    int updatePassword(@Param("userPasswordDTO") UserPasswordDTO userPasswordDTO);

    @Update("update sys_user set status=#{Status} where id=#{uid}")
    int updateStatus(Integer uid,Integer Status);

    /* Page<User> findPage(Page<User> page, @Param("username") String username, @Param("email") String email, @Param("address") String address);*/
}
