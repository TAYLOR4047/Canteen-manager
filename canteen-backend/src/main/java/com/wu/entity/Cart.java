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
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-05
 */
@Getter
@Setter
@Data
@TableName("t_cart")
@ApiModel(value = "Cart对象", description = "")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;

    private Integer uid;

    private Integer pid;

    private String name;

    private Integer num;

    private String image;

    private Double price;

    private String createdUser;

    private String createdTime;

    private String modifiedUser;

    private String modifiedTime;

    private Integer checked;


}
