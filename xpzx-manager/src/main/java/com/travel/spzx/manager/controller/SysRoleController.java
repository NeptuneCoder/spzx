package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.SysRoleService;
import com.travel.spzx.model.dto.system.SysRoleDto;
import com.travel.spzx.model.entity.system.SysRole;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "角色接口")
@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


    @GetMapping(value = "/findAllRoles/{userId}")
    public Result<Map<String, Object>> findAllRoles(@PathVariable(value = "userId") Integer userId) {
        Map<String, Object> resultMap = sysRoleService.findAllRoles(userId);
        return Result.build(resultMap, ResultCodeEnum.SUCCESS);
    }



    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findByPage(@RequestBody SysRoleDto sysRoleDto,
                                                @PathVariable(value = "pageNum") Integer pageNum,
                                                @PathVariable(value = "pageSize") Integer pageSize) {
        System.out.println(sysRoleDto);
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/saveSysRole")
    public Result save(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/update")
    public Result update(@RequestBody SysRole sysRole) {
        sysRoleService.update(sysRole);

        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable(value = "id") Integer id) {
        sysRoleService.delete(id);

        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
