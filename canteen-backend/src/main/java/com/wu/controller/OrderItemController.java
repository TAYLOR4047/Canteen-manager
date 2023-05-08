package com.wu.controller;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wu.common.Result;
import com.wu.entity.OrderItem;
import com.wu.service.IOrderItemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.wu.service.IOrderService;
import com.wu.entity.Order;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-06
 */
@RestController
@CrossOrigin
@RequestMapping("/order_item")
public class OrderItemController {

    @Resource
    private IOrderItemService orderItemService;

    @GetMapping("/pageUser/{orderNo}")
    public Page<OrderItem> findPage(@PathVariable String orderNo, @RequestParam Integer pageNum, @RequestParam Integer pageSize, HttpServletRequest request) {
        String token = request.getHeader("token");
        String uid = JWT.decode(token).getAudience().get(0);
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("order_no", orderNo);
        queryWrapper.orderByAsc("id");
        return orderItemService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}

