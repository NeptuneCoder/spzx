package com.travel.spzx.user.controller;

import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.travel.spzx.user.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//  com.travel.spzx.user.controller;
@RestController
@RequestMapping("api/user/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @GetMapping(value = "/sendCode/{phone}/{type}")
    public Result sendValidateCode(@PathVariable("phone") String phone, @PathVariable("type") String type) {
        smsService.sendValidateCode(phone, type);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}