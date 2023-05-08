package com.wu.mapper;

import cn.hutool.db.Page;
import com.wu.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author NaHCO3
 * @since 2023-05-05
 */
public interface CartMapper extends BaseMapper<Cart> {


    @Override
    int insert(Cart cart);

    void deleteByUidAndPid(@Param("pid") Integer pId, @Param("uid") Integer uId);

    Cart findByUidAndPid(
            @Param("uid") Integer uid,
            @Param("pid") Integer pid
    );


    @Update("UPDATE t_cart SET num=#{num},modified_user=#{modifiedUser} WHERE cid=#{cid}")
    int updateNumByCid(
            @Param("cid") Integer cid,
            @Param("num") Integer num,
            @Param("modified_user") String modifiedUser
    );
    @Update("UPDATE t_cart SET num=#{num} WHERE cid=#{cid}")
    int updateNumByCidNoUser(
            @Param("cid") Integer cid,
            @Param("num") Integer num
    );
}
