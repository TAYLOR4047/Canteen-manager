package com.wu.mapper;

import com.wu.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    @Select("select count(*) from user where username like concat('%',#{username},'%')")
    Integer selectTotal(String username);

    @Select("select * from user")
    List<User> findAll();

    @Insert("insert into user(username,password,nickname,email,phone,address) values (#{username},#{password},#{nickname},#{email},#{phone},#{address})")
    int insert(User user);

    int update(User user);
    @Delete("delete from user where id=#{id}")
    Integer deleteById(@Param("id") Integer id);
}
