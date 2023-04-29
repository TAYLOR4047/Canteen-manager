package com.wu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wu.entity.DishType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DishTypeMapper extends BaseMapper<DishType> {

    @Override
    int insert(DishType entity);

    @Select("select typeid from t_dish_type where dishid = #{dishId}")
    List<Integer> selectByDishId(@Param("dishId") Integer dishId);

    @Delete("delete from t_dish_type where dishid = #{dishId}")
    int deleteByDishId(@Param("dishId") Integer dishId);
}
