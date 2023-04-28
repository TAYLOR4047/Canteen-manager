package com.wu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_dish_type")
@Data
public class DishType {

    private Integer dishId;
    private Integer typeId;
}
