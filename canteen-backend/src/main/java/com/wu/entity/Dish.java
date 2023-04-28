package com.wu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author NaHCO3
 * @since 2023-04-28
 */
@Getter
@Setter
@TableName("t_dish")
@ApiModel(value = "Dish对象", description = "")
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商品标题")
    private String title;

    @ApiModelProperty("商品单价")
    private Double price;

    @ApiModelProperty("库存数量")
    private Integer num;

    @ApiModelProperty("图片路径")
    private String image;

    @ApiModelProperty("商品状态：1：上架，2：下架，3：删除")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty("最后修改时间")
    private LocalDateTime modifiedTime;


}
