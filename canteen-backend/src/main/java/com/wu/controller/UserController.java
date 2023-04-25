package com.wu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    private boolean save(@RequestBody User user) {
        return userService.saveService(user);
    }

/*    @GetMapping
    public List<User> index(){
        List<User> all=userMapper.findAll();
        return all;
    }*/

    /**
     * 分页查询接口
     * 接口路径：/user/page
     *
     * @param pageNum  = (pageNum - 1) * pageSize
     * @param pageSize = pageSize
     * @param username 用户名
     * @param email    邮箱
     * @param address  地址
     * @return 返回分页用户信息
     * @RequestParam 接收?pageNum=1&pageSize=10
     */
    @GetMapping("page")
    public IPage<User> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(defaultValue = "") String username, @RequestParam(defaultValue = "") String email, @RequestParam(defaultValue = "") String address) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }
        if (!"".equals(email)) {
            queryWrapper.like("email", email);
        }
        if (!"".equals(address)) {
            queryWrapper.like("address", address);
        }
        queryWrapper.orderByDesc("id");
        return userService.page(page, queryWrapper);
    }


    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }


    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeByIds(ids);
    }

}
