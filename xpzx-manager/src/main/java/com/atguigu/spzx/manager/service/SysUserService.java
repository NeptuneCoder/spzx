package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;


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
