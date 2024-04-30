package com.travel.spzx.manager.mapper;


import com.travel.spzx.model.dto.system.SysRoleDto;
import com.travel.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    void save(SysRole sysRole);

    void updateByPrimaryKeySelective(SysRole sysRole);

    void deleteByLogic(Integer id);

    List<SysRole> findAll();
}
