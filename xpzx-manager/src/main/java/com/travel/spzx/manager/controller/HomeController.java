package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.HomeService;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.home.HomeVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "home界面中相关的接口")
@RestController
@RequestMapping("/admin/home")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping("/index")
    public Result index() {
        HomeVo homeVo = homeService.getHomeData();
        return Result.success(homeVo);
    }
}
