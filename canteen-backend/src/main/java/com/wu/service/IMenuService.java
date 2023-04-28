package com.wu.service;

import com.wu.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author NaHCO3
 * @since 2023-04-27
 */
public interface IMenuService extends IService<Menu> {

    public List<Menu> findMenus(String name);
}
