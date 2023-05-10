package com.wu.mapper;

import com.wu.entity.Cart;
import com.wu.entity.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-06
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    @Override
    int insert(OrderItem orderItem);

    @Delete("delete from t_order_item WHERE order_no like #{orderNo}")
    int deleteOrderByOrderNo(@Param("orderNo")String orderNo);

    @Update("update t_order_item set num=#{num} where id=#{id}")
    int updateNumById(Integer id,Integer num);
}
