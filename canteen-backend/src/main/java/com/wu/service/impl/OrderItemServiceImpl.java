package com.wu.service.impl;

import com.wu.entity.OrderItem;
import com.wu.mapper.OrderItemMapper;
import com.wu.service.IOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-06
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

}
