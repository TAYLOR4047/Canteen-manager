package com.wu.service;

import com.wu.entity.Cart;
import com.wu.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-06
 */
public interface IOrderService extends IService<Order> {

    void addToOrder(String orderId,Integer uid,List<Integer> ids);

    Boolean cancelOrderById(Integer id);

    Boolean removesByOrderNo(List<String> ids);

    Boolean removeByOrderNo(String id);
}
