package com.wu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.wu.service.IOrderService;
import com.wu.entity.Order;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-06
 */
@RestController
@RequestMapping("/order")
        public class OrderController {
    
@Resource
private IOrderService orderService;

// 新增或者更新
@PostMapping
public boolean save(@RequestBody Order order){
        return orderService.saveOrUpdate(order);
        }

@DeleteMapping("/{id}")
public Boolean delete(@PathVariable Integer id){
        return orderService.removeById(id);
        }

@PostMapping("/del/batch")
public boolean deleteBatch(@RequestBody List<Integer> ids){
        return orderService.removeByIds(ids);
        }

@GetMapping
public List<Order> findAll(){
        return orderService.list();
        }

@GetMapping("/{id}")
public Order findOne(@PathVariable Integer id){
        return orderService.getById(id);
        }

@GetMapping("/page")
public Page<Order> findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return orderService.page(new Page<>(pageNum,pageSize),queryWrapper);
        }

        }

