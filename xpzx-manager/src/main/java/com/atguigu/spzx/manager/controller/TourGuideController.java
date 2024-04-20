package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.TourGuideInfoService;
import com.atguigu.spzx.model.dto.guide.TourGuideInfoDto;
import com.atguigu.spzx.model.entity.guide.TourGuideInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag("领队信息管理")
@RestController
@RequestMapping("/admin/tourGuide")
public class TourGuideController {
    @Autowired
    private TourGuideInfoService tourGuideInfoService;

    @PostMapping("save")
    public Result save(@RequestBody TourGuideInfo tourGuideInfo) {
        tourGuideInfoService.save(tourGuideInfo);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询领队信息列表")
    @GetMapping("/list/{page}/{limit}")
    public Result<PageInfo<TourGuideInfo>> list(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        PageInfo<TourGuideInfo> result = tourGuideInfoService.findListByPage(page, limit);
        return Result.build(result, ResultCodeEnum.SUCCESS);
    }


}
