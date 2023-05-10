package com.wu.mapper;

import com.wu.entity.Order;
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
public interface OrderMapper extends BaseMapper<Order> {
    @Override
    int insert(Order entity);

    @Update("update t_order SET order_status=0 WHERE id=#{id}")
    Boolean cancelOrderById(@Param("id") Integer id);

    @Delete("delete from t_order where order_no=#{orderNo}")
    int deleteOrderByOrderNo(@Param("orderNo") String orderNo);

    @Update("update t_order SET order_status=#{status} WHERE order_no=#{no}")
    Boolean updateConfirmStatusByOrderNo(String no,Integer status);
}
