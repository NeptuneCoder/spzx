package com.travel.spzx.manager.service.impl;

import com.travel.spzx.manager.mapper.SysRoleMapper;
import com.travel.spzx.manager.mapper.SysUserRoleMapper;
import com.travel.spzx.manager.service.SysRoleService;
import com.travel.spzx.model.dto.system.SysRoleDto;
import com.travel.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;


    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto);
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoleList);
        return pageInfo;
    }

    @Override
    public void save(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }

    @Override
    public void update(SysRole sysRole) {
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    @Override
    public void delete(Integer id) {
        sysRoleMapper.deleteByLogic(id);

    }

    @Override
    public Map<String, Object> findAllRoles(Integer userId) {
        List<SysRole> allRolesList = sysRoleMapper.findAll();
        Map<String, Object> result = new java.util.HashMap<>();
        result.put("allRolesList", allRolesList);
        List<Long> roleIdList = sysUserRoleMapper.queryUserRoleByUserId(userId);
        result.put("roleIdList", roleIdList);
        //TODO 查询当前用户已拥有的角色
        return result;
    }
}
