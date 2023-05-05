package com.wu.mapper;

import com.wu.common.Result;
import com.wu.entity.Cart;
import com.wu.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-04
 */
public interface DishMapper extends BaseMapper<Dish> {

    @Override
    int insert(Dish dish);


}
