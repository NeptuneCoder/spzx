package com.travel.spzx.manager.service;

import com.travel.spzx.model.dto.system.AssginRoleDto;
import com.travel.spzx.model.dto.system.LoginDto;
import com.travel.spzx.model.dto.system.SysUserDto;
import com.travel.spzx.model.entity.system.SysUser;
import com.travel.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageInfo;


public interface SysUserService {

    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> list(SysUserDto sysUserDto, Integer pageNo, Integer pageSize);

    Long saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void delete(Integer id);

    void doAssign(AssginRoleDto assginRoleDto);
}
