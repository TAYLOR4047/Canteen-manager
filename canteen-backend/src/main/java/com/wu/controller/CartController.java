package com.wu.controller;


import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wu.common.Result;
import com.wu.entity.Dish;
import com.wu.entity.User;
import com.wu.mapper.CartMapper;
import com.wu.service.IUserService;
import com.wu.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.wu.service.ICartService;
import com.wu.entity.Cart;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-05
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private ICartService cartService;


    // 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Cart cart) {
        return cartService.saveOrUpdate(cart);
    }

    @PostMapping("/insert/{pid}")
    public Result insert(@PathVariable Integer pid,HttpServletRequest request) {
        String token = request.getHeader("token");
        Integer uid = Integer.parseInt(JWT.decode(token).getAudience().get(0));
        cartService.addToCart(pid, uid);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return cartService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return cartService.removeByIds(ids);
    }

    @PostMapping("/num/drop/{cid}")
    public boolean dropNum(@PathVariable Integer cid){
        return cartService.updateNumDownByCid(cid);
    }

    @PostMapping("/num/add/{cid}")
    public boolean addNum(@PathVariable Integer cid){
        return cartService.updateNumUpByCid(cid);
    }

    @GetMapping
    public List<Cart> findAll() {
        return cartService.list();
    }

    @GetMapping("/{id}")
    public Cart findOne(@PathVariable Integer id) {
        return cartService.getById(id);
    }

    @GetMapping("/pageUser")
    public Page<Cart> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, HttpServletRequest request) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        queryWrapper.like("uid", userId);
        queryWrapper.orderByAsc("cid");
        return cartService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

}

