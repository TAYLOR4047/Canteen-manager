package com.wu.service.impl;

import com.wu.entity.Order;
import com.wu.mapper.CartMapper;
import com.wu.mapper.OrderItemMapper;
import com.wu.mapper.OrderMapper;
import com.wu.service.ICartService;
import com.wu.service.IDishService;
import com.wu.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    ICartService cartService;
    @Autowired
    IDishService dishService;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;

    @Override
    public boolean createOrder(List<Integer> ids) {
        //通过List的cid来获取其所选取的购物车的选项


        return false;
    }
}
