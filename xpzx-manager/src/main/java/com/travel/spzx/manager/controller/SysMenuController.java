package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.SysMenuService;
import com.travel.spzx.model.entity.system.SysMenu;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag("系统权限功能")
@RestController
@RequestMapping(value = "/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping(value = "/findMenuNodes")
    public Result<List<SysMenu>> findMenuNodes() {

        return Result.build(sysMenuService.findMenuNodes(), ResultCodeEnum.SUCCESS);
    }

    @PostMapping(value = "/saveMenu")
    public Result<SysMenu> saveMenu(@RequestBody SysMenu sysMenu) {

        return Result.build(sysMenuService.saveMenu(sysMenu), ResultCodeEnum.SUCCESS);
    }

    @PutMapping(value = "/updateMenu")
    public Result<SysMenu> updateMenu(@RequestBody SysMenu sysMenu) {

        return Result.build(sysMenuService.updateMenu(sysMenu), ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping(value = "/removeMenuById/{id}")
    public Result<String> deleteMenu(@PathVariable("id") Integer id) {

        return Result.build(sysMenuService.deleteMenu(id), ResultCodeEnum.SUCCESS);
    }

}
