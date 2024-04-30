package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper {

    List<SysMenu> findMenuNodes();

    Long saveMenu(SysMenu menu);

    Long updateMenu(SysMenu menu);
    //@Param的作用,把参数的id取一个别名,方便后在sql语句中使用通过@Param定义的别名id。
    Long deleteMenu(@Param("id") Integer id);

    int findSubMenuCountByParentId(@Param("id") Integer id);

    List<SysMenu> selectListByUserId(Long userId);


    SysMenu findParentMenu(Long parentId);
}
