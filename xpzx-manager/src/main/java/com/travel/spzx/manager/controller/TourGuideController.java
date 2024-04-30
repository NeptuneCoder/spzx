package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.TourGuideInfoService;
import com.travel.spzx.model.entity.guide.TourGuideInfo;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    public Result save(@RequestBody @Valid TourGuideInfo tourGuideInfo) {
        tourGuideInfoService.save(tourGuideInfo);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询领队信息列表")
    @GetMapping("/findAll/{page}/{limit}")
    public Result<PageInfo<TourGuideInfo>> list(@PathVariable("page") Integer page,
                                                @PathVariable("limit") Integer limit,
                                                @RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "nickname", required = false) String nickname,
                                                @RequestParam(value = "phone", required = false) String phone,
                                                @RequestParam(value = "remark", required = false) String remark) {
        PageInfo<TourGuideInfo> result = tourGuideInfoService.findListByPage(page, limit, name, nickname, phone, remark);
        return Result.build(result, ResultCodeEnum.SUCCESS);
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        tourGuideInfoService.delete(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改领队信息")
    @PutMapping("/update")
    public Result update(@RequestBody @Valid TourGuideInfo tourGuideInfo) {
        tourGuideInfoService.update(tourGuideInfo);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}
