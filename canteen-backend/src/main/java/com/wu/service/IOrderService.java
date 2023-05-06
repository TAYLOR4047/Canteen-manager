package com.wu.service;

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

    boolean createOrder(List<Integer> ids);
}
