package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper {

    List<SysMenu> findMenuNodes();

    Long saveMenu(SysMenu menu);

    Long updateMenu(SysMenu menu);

    Long deleteMenu(@Param("id") Integer id);

    int findSubMenuCountByParentId(@Param("id") Integer id);

    List<SysMenu> selectListByUserId(Long userId);
}
