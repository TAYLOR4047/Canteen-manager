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
@TableName("t_order")
@ApiModel(value = "Order对象", description = "")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("订单号（非主键id）")
    private String orderNo;

    @ApiModelProperty("用户id")
    private Integer uid;

    @ApiModelProperty("用户id")
    private String uname;

    @ApiModelProperty("订单总价格")
    private Double totalPrice;

    @ApiModelProperty("订单的状态：0（用户取消）1（未付款）2（已付款）3（商家已制作完毕）4（交易完毕）")
    private Integer orderStatus;

    @ApiModelProperty("支付方式")
    private Integer paymentType;

    @ApiModelProperty("发货时间")
    private LocalDateTime finishTime;

    @ApiModelProperty("支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty("支付时间")
    private LocalDateTime endTime;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;


}
