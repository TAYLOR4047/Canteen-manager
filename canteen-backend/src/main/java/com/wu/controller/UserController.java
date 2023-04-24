package com.wu.controller;

import com.wu.entity.User;
import com.wu.mapper.UserMapper;

import com.wu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @PostMapping
    private Integer save(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping
    public List<User> index(){
        List<User> all=userMapper.findAll();
        return all;
    }


    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id){
        return userMapper.deleteById(id);
    }
}
