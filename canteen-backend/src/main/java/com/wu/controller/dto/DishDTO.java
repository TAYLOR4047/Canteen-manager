package com.wu.controller.dto;

import com.wu.entity.Menu;
import com.wu.entity.Type;

import java.util.List;

public class DishDTO {
    private String title;
    private double price;
    private Integer num;
    private String image;
    private Integer status ;
    private List<Type> types;
}
