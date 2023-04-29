package com.wu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@TableName("t_cart")
@Data
public class Cart implements Serializable {

    @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Integer num;
    private Long price;
    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;

}
