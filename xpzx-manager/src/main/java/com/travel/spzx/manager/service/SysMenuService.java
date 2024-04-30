package com.travel.spzx.manager.service;

import com.travel.spzx.model.entity.system.SysMenu;
import com.travel.spzx.model.vo.system.SysMenuVo;

import java.util.List;

public interface SysMenuService {

    List<SysMenu> findMenuNodes();

    Long saveMenu(SysMenu sysMenu);

    Long updateMenu(SysMenu sysMenu);

    Long deleteMenu(Integer id);

    List<SysMenuVo> findUserMenuList();
}
