package com.travel.spzx.travel.controller;

import com.travel.spzx.model.dto.product.IndexDto;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.travel.service.IndexService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/travel/")
public class IndexController {
    @Autowired
    IndexService indexService;

    @Operation(description = "首页的基本信息:banner,category")
    @GetMapping("/index")
    public Result index() {
        IndexDto indexDto = indexService.index();
        return Result.success(indexDto);
    }
    //home/goods/guessLike
    @Operation(description = "首页的基本信息:banner,category")
    @GetMapping("/guessLike")
    public Result guessLike() {
        IndexDto indexDto = indexService.index();
        return Result.success(indexDto);
    }
}
