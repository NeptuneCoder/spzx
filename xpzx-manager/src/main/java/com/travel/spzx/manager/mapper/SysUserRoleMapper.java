package com.travel.spzx.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {


    void deleteByUserId(Long userId);

    void save(@Param("userId") Long userId, @Param("roleId") Long roleId);

    List<Long> queryUserRoleByUserId(@Param("userId") Integer userId);
}
