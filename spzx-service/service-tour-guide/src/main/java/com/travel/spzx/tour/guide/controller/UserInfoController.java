package com.travel.spzx.tour.guide.controller;

import com.alibaba.fastjson.JSON;
import com.travel.spzx.model.dto.h5.ProfileDto;
import com.travel.spzx.model.dto.h5.UserLoginCodeDto;
import com.travel.spzx.model.dto.h5.UserLoginDto;
import com.travel.spzx.model.dto.h5.UserRegisterDto;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.travel.spzx.model.vo.h5.UserInfoVo;

import com.travel.spzx.tour.guide.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "会员用户接口")
@RestController
@RequestMapping("api/tour/guide/user")
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

    @Operation(summary = "退出登录")
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        userInfoService.logout(request.getHeader("token"));
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("auth/profile")
    public Result<UserInfoVo> getCurrentUserInfo() {

        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo();
        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "更新用户信息")
    @PutMapping("auth/profile")
    public Result<UserInfoVo> putUserInfo(@RequestBody ProfileDto profileDto, HttpServletRequest request) {
        System.out.println("profile 更新用户信息:" + JSON.toJSONString(profileDto));
        System.out.println("profile ip:" + request.getRemoteAddr());
        userInfoService.putUserInfo(request.getHeader("token"), profileDto);
        return Result.build(profileDto, ResultCodeEnum.SUCCESS);
    }


}

