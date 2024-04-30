package com.travel.spzx.travel.controller;

import com.travel.spzx.model.vo.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/travel/")
public class IndexController {

    @GetMapping("welcome")
    public Result welcome() {
        return Result.success("欢迎来到旅游商城");
    }
}
