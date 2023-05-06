package com.wu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-06
 */
@Getter
@Setter
@Data
@TableName("t_order_item")
@ApiModel(value = "OrderItem对象", description = "")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("归属订单id")
    private String orderNo;

    @ApiModelProperty("菜品id")
    private Integer dishid;

    @ApiModelProperty("菜品图片")
    private String dishImg;

    @ApiModelProperty("单价")
    private Double dishPrice;

    @ApiModelProperty("菜品数量")
    private Integer num;

    @ApiModelProperty("菜品总价")
    private Double totalPrice;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
