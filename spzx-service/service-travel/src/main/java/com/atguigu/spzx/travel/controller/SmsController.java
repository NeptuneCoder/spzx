package com.atguigu.spzx.travel.controller;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;

import com.atguigu.spzx.travel.service.SmsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "短信接口")
@RestController
@RequestMapping("api/user/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Tag(name = "发送验证码接口")
    @GetMapping(value = "/sendCode/{phone}")
    public Result sendValidateCode(@PathVariable("phone") String phone) {
        smsService.sendValidateCode(phone);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}