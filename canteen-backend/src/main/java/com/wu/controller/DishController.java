package com.wu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.wu.service.IDishService;
import com.wu.entity.Dish;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author NaHCO3
 * @since 2023-04-28
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Resource
    private IDishService dishService;

    // 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Dish dish) {
        return dishService.saveOrUpdate(dish);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return dishService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return dishService.removeByIds(ids);
    }

    @GetMapping
    public List<Dish> findAll() {
        return dishService.list();
    }

    @GetMapping("/{id}")
    public Dish findOne(@PathVariable Integer id) {
        return dishService.getById(id);
    }

    @GetMapping("/page")
    public Page<Dish> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return dishService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

}

