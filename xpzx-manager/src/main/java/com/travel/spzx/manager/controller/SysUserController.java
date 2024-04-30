package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.SysUserService;
import com.travel.spzx.model.dto.system.AssginRoleDto;
import com.travel.spzx.model.dto.system.SysUserDto;
import com.travel.spzx.model.entity.system.SysUser;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "角色接口")
@RestController
@RequestMapping(value = "/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    @PostMapping(value = "/list/{pageNo}/{pageSize}")
    public Result<PageInfo<SysUser>> list(@RequestBody SysUserDto sysUserDto,
                                          @PathVariable(value = "pageNo") Integer pageNo,
                                          @PathVariable(value = "pageSize") Integer pageSize) {
        return Result.build(sysUserService.list(sysUserDto, pageNo, pageSize), ResultCodeEnum.SUCCESS);
    }

    @PostMapping(value = "/saveSysUser")
    public Result save(@RequestBody SysUser sysUser) {
        return Result.build(sysUserService.saveSysUser(sysUser), ResultCodeEnum.SUCCESS);
    }
    //updateSysUser


    @PutMapping(value = "/updateSysUser")
    public Result update(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "通过id删除用户")
    @DeleteMapping(value = "/deleteById/{id}")
    public Result delete(@PathVariable(value = "id") Integer id) {
        sysUserService.delete(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "保存用户角色数据")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto) {
        sysUserService.doAssign(assginRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}