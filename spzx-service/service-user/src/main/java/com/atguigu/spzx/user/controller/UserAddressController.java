package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "会员用户接口")
@RestController
@RequestMapping("/api/user/userAddress")
public class UserAddressController {


    @Autowired
    private UserAddressService userAddressService;

    @Operation(summary = "获取用户地址列表")
    @GetMapping("auth/findUserAddressList")
    public Result<List<UserAddress>> findUserAddressList() {
        List<UserAddress> list = userAddressService.findUserAddressList();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    //远程调用
    @Operation(summary = "获取用户地址列表")
    @GetMapping("auth/getUserAddress/{id}")
    UserAddress getUserAddress(@PathVariable("id") Long userAddressId) {
        return userAddressService.getUserAddress(userAddressId);
    }
}
