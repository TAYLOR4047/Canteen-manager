package com.wu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wu.common.Result;
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
 * @since 2023-05-04
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
    @PostMapping("/insert")
    public boolean insert(@RequestBody Dish dish) {
        return dishService.save(dish);
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
    public Page<Dish> findPage(@RequestParam String name,@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id");
        return dishService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Dish dish) {
        dishService.updateById(dish);
        return Result.success();
    }

    @PostMapping("/dishType/{dishId}")
    public Result dishType(@PathVariable Integer dishId, @RequestBody List<Integer> typeIds) {
        dishService.setDishType(dishId, typeIds);
        return Result.success();
    }

    @GetMapping("/dishType/{dishId}")
    public Result getDishType(@PathVariable Integer dishId) {
        return Result.success(dishService.getDishType(dishId));
    }
}

