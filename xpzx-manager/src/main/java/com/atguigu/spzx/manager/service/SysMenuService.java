package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;

import java.util.List;

public interface SysMenuService {

    List<SysMenu> findMenuNodes();

    Long saveMenu(SysMenu sysMenu);

    Long updateMenu(SysMenu sysMenu);

    Long deleteMenu(Integer id);

    List<SysMenuVo> findUserMenuList();
}
