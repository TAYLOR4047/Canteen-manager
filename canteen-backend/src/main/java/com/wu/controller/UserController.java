package com.wu.controller;

import com.wu.entity.User;
import com.wu.mapper.UserMapper;

import com.wu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

  /*  @PostMapping
    private Integer save(@RequestBody User user){
        return userService.save(user);
    }*/

    @PostMapping
    private boolean save(@RequestBody User user){
        return userService.saveService(user);
    }

    @GetMapping
    public List<User> index(){
        List<User> all=userMapper.findAll();
        return all;
    }

    @GetMapping("page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam String username){
        pageNum=(pageNum-1)*pageSize;
        List<User> data=userMapper.selectPage(pageNum,pageSize,username);
        Integer total= userMapper.selectTotal(username);
        Map<String,Object> res=new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }


    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id){
        return userMapper.deleteById(id);
    }
}
