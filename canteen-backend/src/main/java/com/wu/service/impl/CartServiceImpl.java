package com.wu.service.impl;

import com.wu.entity.Cart;
import com.wu.entity.Dish;
import com.wu.entity.User;
import com.wu.mapper.CartMapper;
import com.wu.service.ICartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wu.service.IDishService;
import com.wu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-05
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Resource
    private IDishService dishService;
    @Resource
    private IUserService userService;

    @Override
    public void addToCart(Integer pid, Integer userId) {
        User user = userService.getById(userId);
        String username = user.getUsername();
        Integer uid = user.getId();
        // 调用findByUidAndPid()查询购物车详情
        Cart list = cartMapper.findByUidAndPid(uid, pid);
        System.out.println("=======================counts===================");
        // 判断查询结果是否为null
        if (list == null) {
            System.out.println("购物车无此内容，执行插入");
            // 是：表示该用户的购物车没有该商品，则需要执行insert操作
            // -- 调用productService.getById()得到商品详情，该数据中包含商品价格
            Dish dish = dishService.getById(pid);
            // -- 创建新的Cart对象
            Cart cart = new Cart();
            // -- 补全Cart对象的属性：uid > 参数uid
            cart.setUid(uid);
            // -- 补全Cart对象的属性：pid > 参数pid
            cart.setPid(pid);
            // -- 补全Cart对象的属性：num > 参数amount
            cart.setNum(1);
            // -- 补全Cart对象的属性：price > 以上查询到的商品详情中包含价格
            cart.setPrice(Double.valueOf(dish.getPrice()));
            cart.setName(dish.getName());
            cart.setImage(dish.getImage());
            cart.setCreatedUser(username);
            cart.setChecked(0);
            // -- 补全Cart对象的属性：4个日志 > 参数username, now
            // -- 调用insert()插入数据
            int row = cartMapper.insert(cart);
            if (row != 1) {
                System.out.println("未执行插入操作");
            }
            System.out.println(row);
            System.out.println("=================================插入完成===============================");
        } else {
            System.out.println("======================购物车存在此内容，执行更新=========================");
            // 否：表示该用户的购物车已有该商品，则需要执行update操作增加数量
            // -- 从查询结果中获取cid
            Integer cid = list.getCid();
            System.out.println(cid);
            Integer nums = list.getNum();
            System.out.println(nums);
            // -- 从查询结果中取出原有数量，与参数amount相加，得到新的数量
            Integer num = nums + 1;
            // -- 调用updateNumByCid()执行修改数量
            int row = cartMapper.updateNumByCid(cid, num,username);
            if (row != 1) {
                System.out.println("未执行更新操作");
            }
            System.out.println(row);
            System.out.println("=================================更新完成===============================");
        }
        System.out.println("=============插入或更新操作结束===================");
    }

    @Override
    public boolean updateNumUpByCid(Integer cid) {
        Integer num=cartMapper.selectById(cid).getNum();
        int row=cartMapper.updateNumByCidNoUser(cid,num+1);
        if(row != 1){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean updateNumDownByCid(Integer cid) {
        Integer num=cartMapper.selectById(cid).getNum();
        int row=cartMapper.updateNumByCidNoUser(cid,num-1);
        if(row != 1){
            return false;
        }else{
            return true;
        }
    }

}
