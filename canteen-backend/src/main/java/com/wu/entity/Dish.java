package com.wu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

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
 * @since 2023-05-04
 */
@Getter
@Setter
@Data
@TableName("t_dish")
@ApiModel(value = "Dish对象", description = "")
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String name;

    private String price;

    private String num;

    private String image;

    private String status;

    private String createDate;

    private String modifiedTime;
}
