package com.wu.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.wu.entity.Dish;
import com.wu.entity.DishType;
import com.wu.entity.Type;
import com.wu.mapper.DishMapper;
import com.wu.mapper.DishTypeMapper;
import com.wu.service.IDishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wu.service.ITypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author NaHCO3
 * @since 2023-04-28
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {


    @Resource
    private DishTypeMapper dishTypeMapper;

    @Resource
    private ITypeService typeService;

    //问题是没有插入新的数据！
    @Override
    public void setDishType(Integer dishId, List<Integer> typeIds) {
        // 先删除当前角色id所有的绑定关系
        dishTypeMapper.deleteByDishId(dishId);
        // 再把前端传过来的菜单id数组绑定到当前的这个角色id上去
        for (Integer typeId : typeIds) {
            DishType dishType = new DishType();
            dishType.setDishId(dishId);
            dishType.setTypeId(typeId);
            dishTypeMapper.insert(dishType);

        }
    }


    @Override
    public List<Integer> getDishType(Integer dishId) {
        return dishTypeMapper.selectByDishId(dishId);
    }
}
