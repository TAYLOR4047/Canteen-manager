package com.wu.controller;


import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wu.common.Result;
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
@RequestMapping("/order")
public class OrderController {

    @Resource
    private IOrderService orderService;
    @Resource
    private IOrderItemService orderItemService;

    // 新增
    @PostMapping("/insert")
    public boolean insert(@RequestBody List<Integer> ids, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userid = Long.parseLong(JWT.decode(token).getAudience().get(0));
        String orderNo = getOrderCode(userid);
        Integer uid = Integer.parseInt(JWT.decode(token).getAudience().get(0));
        //先插入订单明细
        orderItemService.addToOrderItem(orderNo, uid, ids);
        orderService.addToOrder(orderNo, uid, ids);
        return true;
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        Order order = new Order();
        order = orderService.getBaseMapper().selectById(id);
        String no = order.getOrderNo();
        boolean row1 = orderService.removeByOrderNo(no);
        boolean row2 = orderItemService.removeItemByOrderNo(no);
        if ((row1 == true) && (row2 == true)) {
            return true;
        } else {
            return false;
        }
    }


    @PostMapping("/confirmOrder/{No}")
    public Boolean confirmOrder(@PathVariable String No){
        return orderService.updateConfirmStatusByOrderNo(No);
    }

    @PostMapping("/{id}")
    public Boolean cancel(@PathVariable Integer id) {
        return orderService.cancelOrderById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        Order order = new Order();
        List<String> nos=new ArrayList<String>();
        for (Integer id : ids) {
            order = orderService.getBaseMapper().selectById(id);
            String no = order.getOrderNo();
            nos.add(no);
        }
        boolean row1 = orderService.removesByOrderNo(nos);
        boolean row2 = orderItemService.removeItemsByOrderNo(nos);
        if ((row1 == true) && (row2 == true)) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping
    public List<Order> findAll() {
        return orderService.list();
    }

    @GetMapping("/{id}")
    public Order findOne(@PathVariable Integer id) {
        return orderService.getById(id);
    }

    @GetMapping("/page")
    public Page<Order> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return orderService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @GetMapping("/pageUser")
    public Page<Order> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, HttpServletRequest request) {
        String token = request.getHeader("token");
        String uid = JWT.decode(token).getAudience().get(0);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        queryWrapper.like("uid", uid);
        return orderService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    /**
     * 生成一个5位的随机数；（当然可以生成3、4、6、7、8……位；只是，这儿为了便于管理、程序明确，这儿我们统一生成5位）
     *
     * @param n
     * @return
     */
    private static int getRandom(Long n) {
        Random random = new Random();
        return (int) (random.nextDouble() * (90000)) + 10000;
    }

    /**
     * 生成订单号
     *
     * @param userId
     * @return
     */
    public String getOrderCode(Long userId) {
        return getDateTime() + getRandom(userId);
    }

    private static String getDateTime() {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }


}

