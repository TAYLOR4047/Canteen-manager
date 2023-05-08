package com.wu.service;

import com.wu.entity.OrderItem;
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
public interface IOrderItemService extends IService<OrderItem> {
    void addToOrderItem(String orderId,Integer uid, List<Integer> ids);

    Boolean removeItemByOrderNo(String id);

    Boolean removeItemsByOrderNo(List<String> ids);
}
