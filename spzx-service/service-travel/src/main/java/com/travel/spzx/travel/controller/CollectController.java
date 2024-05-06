package com.travel.spzx.travel.controller;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.collect.Collect;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.travel.service.CollectService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2024/5/6
 * 收藏控制器
 */
@RestController
@RequestMapping("/api/collect/auth/")
public class CollectController {
    @Autowired
    private CollectService collectService;

    //查询收藏列表
    @Operation(description = "查询收藏列表")
    @GetMapping("list/{page}/{limit}")
    public Result<PageInfo<Collect>> list(@PathVariable("page") int page, @PathVariable("limit") int limit) {
        PageInfo<Collect> collectList = collectService.queryCollectList(page, limit);
        return Result.success(collectList);
    }

    //查询当前商品是否被收藏
    @Operation(description = "查询当前商品是否被收藏")
    @GetMapping("isCollect/product/{productId}")
    public Result isCollect(@PathVariable("productId") Long productId) {
        return Result.success(collectService.isCollect(productId));
    }

    //收藏商品
    @GetMapping("collect/product/{productId}")
    public Result collect(@PathVariable("productId") Long productId) {
        collectService.collect(productId);
        return Result.success();
    }

    //取消收藏
    @GetMapping("cancelCollect/product/{productId}")
    public Result cancelCollect(@PathVariable("productId") Long productId) {
        collectService.cancelCollect(productId);
        return Result.success();
    }
}
