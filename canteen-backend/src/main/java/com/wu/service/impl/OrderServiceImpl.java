package com.wu.service.impl;

import com.wu.entity.Cart;
import com.wu.entity.Order;
import com.wu.entity.User;
import com.wu.mapper.CartMapper;
import com.wu.mapper.OrderItemMapper;
import com.wu.mapper.OrderMapper;
import com.wu.service.ICartService;
import com.wu.service.IDishService;
import com.wu.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IUserService userService;

    @Override
    public void addToOrder(String orderNo, Integer uid, List<Integer> ids) {
        if (ids != null) {
            Order order = new Order();
            Double totalSum = 0.0;
            for (Integer cid : ids) {
                Cart cart = cartService.getById(cid);
                totalSum += cart.getPrice() * cart.getNum();
            }
            User user = userService.getById(uid);

            order.setOrderNo(orderNo);
            order.setUid(uid);
            order.setUname(user.getUsername());
            order.setTotalPrice(totalSum);
            order.setOrderStatus(1);

            int row = orderMapper.insert(order);
            if (row != 1) {
                System.out.println("插入订单爆炸！");
            } else {
                System.out.println("插入订单good！");
            }
        } else {
            System.out.println("订单中的cid的list数组爆炸");
        }
    }

    @Override
    public Boolean cancelOrderById(Integer id) {
        return orderMapper.cancelOrderById(id);
    }

    @Override
    public Boolean removeByOrderNo(String no) {
        int row=orderMapper.deleteOrderByOrderNo(no);
        if(row != 1){
            return false;
        }
        return true;
    }

    @Override
    public Boolean removesByOrderNo(List<String> nos) {
        if (!nos.isEmpty()) {
            int row = 0;
            for (String no : nos) {
                row = orderMapper.deleteOrderByOrderNo(no);
            }
            if(row != nos.toArray().length){
                return false;
            }
            return true;
        }
        return false;
    }



}
