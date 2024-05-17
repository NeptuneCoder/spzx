package com.travel.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.manager.service.UserInfoService;
import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.spzx.model.vo.common.Result;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@Tag("用户管理")
@RestController
@RequestMapping("/admin/userinfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/list/{page}/{limit}")
    public Result<PageInfo<UserInfo>> list(@PathVariable("page") int page, @PathVariable("limit") int limit, @RequestParam(required = false, name = "username", defaultValue = "") String username) {


        return Result.success(userInfoService.list(page, limit, username));
    }

    @PutMapping("/update/status")
    public Result<UserInfo> update(@RequestBody UserInfo userInfo) {
        userInfoService.update(userInfo);
        return Result.success();
    }
}



