package com.wu.mapper;

import com.wu.entity.User;
import org.apache.ibatis.annotations.*;


import java.util.List;

public interface UserMapper{
    //查询所有数据
    @Select("select * from user where username like concat('%',#{username},'%') limit #{pageNum},#{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize,String username);


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
