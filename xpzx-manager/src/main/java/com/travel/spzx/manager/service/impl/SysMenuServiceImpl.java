package com.travel.spzx.manager.service.impl;

import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.manager.mapper.SysMenuMapper;
import com.travel.spzx.manager.mapper.SysRoleMenuMapper;
import com.travel.spzx.manager.service.SysMenuService;
import com.travel.spzx.model.entity.system.SysMenu;
import com.travel.spzx.model.entity.system.SysUser;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.travel.spzx.model.vo.system.SysMenuVo;
import com.travel.xpzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import com.travel.xpzx.utils.SysMenuHelper;
import org.springframework.util.CollectionUtils;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


    @Override
    public List<SysMenu> findMenuNodes() {
        List<SysMenu> menuNodes = sysMenuMapper.findMenuNodes();
        List<SysMenu> result = SysMenuHelper.buildTree(menuNodes);
        return result;
    }

    @Override
    public Long saveMenu(SysMenu sysMenu) {
        //当添加新菜单时，将父菜单的isHalf为true字段设置为true
        Long aLong = sysMenuMapper.saveMenu(sysMenu);
        updateParentIsHalf(sysMenu.getParentId());
        return aLong;

    }

    private void updateParentIsHalf(Long parentId) {
        SysMenu parentMenu = sysMenuMapper.findParentMenu(parentId);
        if (parentMenu != null) {
            sysRoleMenuMapper.updateParentIsHalf(parentMenu.getId());
             //递归更新父菜单的isHalf字段
            updateParentIsHalf(parentMenu.getParentId());
        }
    }

    @Override
    public Long updateMenu(SysMenu sysMenu) {
        return sysMenuMapper.updateMenu(sysMenu);
    }

    @Override
    public Long deleteMenu(Integer id) {
        //删除菜单及其子菜单
        int subMenuCount = sysMenuMapper.findSubMenuCountByParentId(id);
        if (subMenuCount > 0) {
            throw new GuiguException(ResultCodeEnum.NODE_ERROR);
        }

        return sysMenuMapper.deleteMenu(id);
    }


    @Override
    public List<SysMenuVo> findUserMenuList() {

        SysUser sysUser = AuthContextUtil.get();
        Long userId = sysUser.getId();          // 获取当前登录用户的id
        System.out.println("userId = " + userId);
        //
        List<SysMenu> sysMenuList = sysMenuMapper.selectListByUserId(userId);
        System.out.println("sysMenuList = " + sysMenuList);

        //构建树形数据
        List<SysMenu> sysMenuTreeList = SysMenuHelper.buildTree(sysMenuList);
        return this.buildMenus(sysMenuTreeList);
    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}