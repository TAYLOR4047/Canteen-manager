package com.wu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.wu.service.IOrderItemService;
import com.wu.entity.OrderItem;

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
@RequestMapping("/order-item")
        public class OrderItemController {
    
@Resource
private IOrderItemService orderItemService;

// 新增或者更新
@PostMapping
public boolean save(@RequestBody OrderItem orderItem){
        return orderItemService.saveOrUpdate(orderItem);
        }

@DeleteMapping("/{id}")
public Boolean delete(@PathVariable Integer id){
        return orderItemService.removeById(id);
        }

@PostMapping("/del/batch")
public boolean deleteBatch(@RequestBody List<Integer> ids){
        return orderItemService.removeByIds(ids);
        }

@GetMapping
public List<OrderItem> findAll(){
        return orderItemService.list();
        }

@GetMapping("/{id}")
public OrderItem findOne(@PathVariable Integer id){
        return orderItemService.getById(id);
        }

@GetMapping("/page")
public Page<OrderItem> findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        QueryWrapper<OrderItem> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return orderItemService.page(new Page<>(pageNum,pageSize),queryWrapper);
        }

        }

