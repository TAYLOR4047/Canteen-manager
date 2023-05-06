package com.wu.service;

import com.wu.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wu.entity.Dish;
import io.swagger.models.auth.In;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-05
 */
public interface ICartService extends IService<Cart> {


    void addToCart(Integer pid,Integer userId);

    boolean updateNumUpByCid(Integer cid);

    boolean updateNumDownByCid(Integer cid);

}
