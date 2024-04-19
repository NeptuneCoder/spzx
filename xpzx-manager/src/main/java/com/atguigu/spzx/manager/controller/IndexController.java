package com.atguigu.spzx.manager.controller;


import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import com.atguigu.xpzx.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private SysMenuService sysMenuService;

    @Log(title = "用户登录", businessType = 0)
    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    //每次都从请求头中获取token，不安全，后续优化
    //    @GetMapping(value = "/getUserInfo")
    //    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
    //        SysUser sysUser = sysUserService.getUserInfo(token);
    //        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    //    }
    //优化后
    @GetMapping(value = "/getUserInfo")
    public Result<SysUser> getUserInfo() {
        SysUser sysUser = AuthContextUtil.get();
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    @GetMapping(value = "/logout")
    public Result<Void> logout(@RequestHeader(name = "token") String token) {
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @GetMapping("/menus")
    public Result menus() {
        List<SysMenuVo> sysMenuVoList = sysMenuService.findUserMenuList();
        return Result.build(sysMenuVoList, ResultCodeEnum.SUCCESS);
    }
}
