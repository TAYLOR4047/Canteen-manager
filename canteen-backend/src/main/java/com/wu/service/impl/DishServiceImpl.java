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
 *  服务实现类
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

    @Override
    public void setDishType(Integer dishId, List<Integer> typeIds) {
        dishTypeMapper.deleteByDishId(dishId);
        List<Integer> typeIdsCopy= CollUtil.newArrayList(typeIds);
        for(Integer typeId : typeIds){
            Type type= typeService.getById(typeId);
            if(type.getPid()!=null&&!typeIdsCopy.contains(type.getPid())){
                DishType dishType=new DishType();
                dishType.setDishId(dishId);
                dishType.setTypeId(type.getPid());
                dishTypeMapper.insert(dishType);
                typeIdsCopy.add(type.getPid());
            }
            DishType dishType=new DishType();
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
