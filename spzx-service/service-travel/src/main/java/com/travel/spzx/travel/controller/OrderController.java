package com.travel.spzx.travel.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "订单相关接口", description = "获取订单列表，获取订单详情等")
@RestController
@RequestMapping("/api/product/")
public class OrderController {
}
