package com.wu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wu.entity.Type;
import com.wu.mapper.TypeMapper;
import com.wu.service.ITypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author NaHCO3
 * @since 2023-04-28
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {

    @Override
    public List<Type> findTypes(String name) {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        // 查询所有数据
        List<Type> list = list(queryWrapper);
        // 找出pid为null的一级菜单
        List<Type> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (Type menu : parentNodes) {
            // 筛选所有数据中pid=父级id的数据就是二级菜单
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}
