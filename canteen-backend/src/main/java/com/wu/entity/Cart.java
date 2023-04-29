package com.wu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_")
@Data
public class Cart{
    private static final long serialVersionUID = -9051846958681813039L;

    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Integer num;
    private Long price;

}
