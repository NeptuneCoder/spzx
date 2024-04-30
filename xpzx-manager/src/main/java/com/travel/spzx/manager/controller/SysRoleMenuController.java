package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.SysRoleMenuService;
import com.travel.spzx.model.dto.system.AssginMenuDto;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// com.travel.spzx.manager.controller
@RestController
@RequestMapping(value = "/admin/system/sysRoleMenu")
public class SysRoleMenuController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @GetMapping(value = "/findSysRoleMenuByRoleId/{roleId}")
    public Result<Map<String, Object>> findSysRoleMenuByRoleId(@PathVariable(value = "roleId") Long roleId) {
        Map<String, Object> sysRoleMenuList = sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(sysRoleMenuList, ResultCodeEnum.SUCCESS);
    }

    @PostMapping(value = "/doAssign")
    public Result doAssign(@RequestBody AssginMenuDto assginMenuDto) {
        sysRoleMenuService.doAssign(assginMenuDto);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

}