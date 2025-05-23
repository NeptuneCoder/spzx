package com.travel.spzx.manager.service;

import com.travel.spzx.model.dto.system.SysRoleDto;
import com.travel.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface SysRoleService {
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize);

    void save(SysRole sysRole);

    void update(SysRole sysRole);

    void delete(Integer id);

    Map<String, Object> findAllRoles(Integer userId);
}
