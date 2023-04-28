package com.wu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wu.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.wu.service.ITypeService;
import com.wu.entity.Type;

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
@RequestMapping("/type")
public class TypeController {

    @Resource
    private ITypeService typeService;

    // 新增或者更新
    @PostMapping
    public boolean save(@RequestBody Type type) {
        return typeService.saveOrUpdate(type);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return typeService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return typeService.removeByIds(ids);
    }

    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "") String name) {
        return Result.success(typeService.findTypes(name));
    }


    @GetMapping("/{id}")
    public Type findOne(@PathVariable Integer id) {
        return typeService.getById(id);
    }

    @GetMapping("/page")
    public Page<Type> findPage(@RequestParam String name, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        queryWrapper.orderByDesc("id");
        return typeService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

}

