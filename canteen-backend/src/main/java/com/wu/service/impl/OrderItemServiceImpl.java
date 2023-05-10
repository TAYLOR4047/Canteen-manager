package com.wu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wu.entity.Cart;
import com.wu.entity.Dish;
import com.wu.entity.OrderItem;
import com.wu.mapper.OrderItemMapper;
import com.wu.service.ICartService;
import com.wu.service.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ICartService cartService;

    @Override
    public void addToOrderItem(String orderId, Integer uid, List<Integer> ids) {
        if (ids != null) {
            for (Integer cid : ids) {
                Cart cart = cartService.getById(cid);
                OrderItem orderItem = new OrderItem();

                orderItem.setOrderNo(orderId);
                orderItem.setDishid(cid);
                orderItem.setDishname(cart.getName());
                orderItem.setDishImg(cart.getImage());
                orderItem.setDishPrice(cart.getPrice());
                orderItem.setNum(cart.getNum());
                orderItem.setTotalPrice(cart.getPrice() * cart.getNum());
                int row = orderItemMapper.insert(orderItem);
                if (row != 1) {
                    System.out.println("插入订单明细爆炸！");
                } else {
                    System.out.println("插入订单明细good！");
                }
            }
        } else {
            System.out.println("订单明细中的cid的list数组爆炸");
        }

    }

    @Override
    public Boolean removeItemByOrderNo(String no) {
        int row = orderItemMapper.deleteOrderByOrderNo(no);
        if (row != 1) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean removeItemsByOrderNo(List<String> nos) {
        if (nos != null) {
            for (String no : nos) {
                orderItemMapper.deleteOrderByOrderNo(no);
            }
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateNumDownById(Integer id) {
        Integer num=orderItemMapper.selectById(id).getNum();
        int row=orderItemMapper.updateNumById(id,num-1);
        if(row != 1){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Boolean updateNumUpById(Integer id) {
        Integer num=orderItemMapper.selectById(id).getNum();
        int row=orderItemMapper.updateNumById(id,num+1);
        if(row != 1){
            return false;
        }else{
            return true;
        }
    }


}