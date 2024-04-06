package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    List<Integer> findSysRoleMenuByRoleId(@Param("roleId") Long roleId);

    void deleteSysRoleMenuByRoleId(Long roleId);

    void saveSysRoleMenu(AssginMenuDto assginMenuDto);
}
