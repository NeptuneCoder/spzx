package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    List<Integer> findSysRoleMenuByRoleId(@Param("roleId") Long roleId);

    void deleteSysRoleMenuByRoleId(Long roleId);

    void saveSysRoleMenu(AssginMenuDto assginMenuDto);

    void updateParentIsHalf(Long menuid);
}
