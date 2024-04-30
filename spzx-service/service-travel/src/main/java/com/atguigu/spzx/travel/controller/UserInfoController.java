package com.atguigu.spzx.travel.controller;

import com.atguigu.spzx.model.dto.h5.UserLoginCodeDto;
import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;

import com.atguigu.spzx.travel.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "会员用户接口")
@RestController
@RequestMapping("api/user/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "会员注册")
    @PostMapping("register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto) {
        userInfoService.register(userRegisterDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "会员登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto userLoginDto) {
        return Result.build(userInfoService.login(userLoginDto), ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "验证码登录及注册")
    @PostMapping("/login/code")
    public Result login(@RequestBody UserLoginCodeDto userLoginCodeDto) {
        return Result.build(userInfoService.login(userLoginCodeDto), ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "注销账号")
    @DeleteMapping("/auth/closeAccount")
    public Result login() {
        userInfoService.closeAccount();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("auth/getCurrentUserInfo")
    public Result<UserInfoVo> getCurrentUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo(token);
        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }

}

