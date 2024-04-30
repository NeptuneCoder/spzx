package com.travel.spzx.manager.service.impl;

import com.travel.spzx.manager.mapper.SysRoleMenuMapper;
import com.travel.spzx.manager.service.SysMenuService;
import com.travel.spzx.manager.service.SysRoleMenuService;
import com.travel.spzx.model.dto.system.AssginMenuDto;
import com.travel.spzx.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {

        // 查询所有的菜单数据
        List<SysMenu> sysMenuList = sysMenuService.findMenuNodes();

        // 查询当前角色的菜单数据
        List<Integer> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);

        // 将数据存储到Map中进行返回
        Map<String, Object> result = new HashMap<>();
        result.put("sysMenuList", sysMenuList);
        result.put("roleMenuIds", roleMenuIds);

        // 返回
        return result;
    }

    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        // 先删除原有菜单数据
        sysRoleMenuMapper.deleteSysRoleMenuByRoleId(assginMenuDto.getRoleId());


        // 再插入新菜单数据
        List<Map<String, Number>> menuIdList = assginMenuDto.getMenuIdList();
        if (menuIdList != null && menuIdList.size() > 0) {
            sysRoleMenuMapper.saveSysRoleMenu(assginMenuDto);
        }
    }

}
