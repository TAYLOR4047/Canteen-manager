package com.wu.mapper;

import com.wu.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author NaHCO3
 * @since 2023-04-27
 */
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select id from sys_role where flag=#{flag}")
    Integer selectByFlag(@Param("flag") String flag);

    @Select("select menu_id from sys_role_menu where role_id = #{roleId}")
    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);

    @Delete("delete from sys_role_menu where role_id = #{roleId}")
    int deleteByRoleId(@Param("roleId") Integer roleId);
}
