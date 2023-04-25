package com.wu.mapper;

import com.wu.entity.User;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface UserMapper{
    //查询所有数据
    @Select("select * from user limit #{pageNum},#{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize);

    @Select("select count(*) from user")
    Integer selectTotal();

    @Select("select * from user")
    List<User> findAll();

    @Insert("insert into user(username,password,nickname,email,phone,address) values (#{username},#{password},#{nickname},#{email},#{phone},#{address})")
    int insert(User user);

    int update(User user);
    @Delete("delete from user where id=#{id}")
    Integer deleteById(@Param("id") Integer id);
}
