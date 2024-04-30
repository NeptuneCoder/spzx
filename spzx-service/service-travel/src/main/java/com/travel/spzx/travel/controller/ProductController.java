package com.travel.spzx.travel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "产品相关接口", description = "获取产品列表，获取产品详情等")
@RestController
@RequestMapping("/api/product/")
public class ProductController {
}
