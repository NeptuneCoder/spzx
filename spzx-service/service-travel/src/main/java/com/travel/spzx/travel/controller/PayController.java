package com.travel.spzx.travel.controller;

import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.travel.service.PayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "支付相关接口", description = "创建支付信息等")
@RestController
@RequestMapping("/api/pay/auth")
public class PayController {
    @Autowired
    private PayService payService;

    @GetMapping("/mockPay/{id}")
    public Result mockPay(@PathVariable("id") Long id) {
        payService.mockPay(id);
        return Result.success("支付成功");

    }
}
