package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.TourGuideInfoService;
import com.atguigu.spzx.manager.service.TourLevelService;
import com.atguigu.spzx.model.entity.IdType.IdTypeInfo;
import com.atguigu.spzx.model.entity.guide.TourGuideInfo;
import com.atguigu.spzx.model.entity.guide.TourLevel;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag("领队等级")
@RestController
@RequestMapping("/admin/tourLevel")
public class TourLevelController {
    @Autowired
    private TourLevelService tourLevelService;

    @PostMapping("save")
    public Result save(@RequestBody TourLevel tourLevel) {
        tourLevelService.save(tourLevel);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取证件类型列表")
    @GetMapping("/findAll/{page}/{limit}")
    public Result<PageInfo<TourLevel>> findAll(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        return Result.success(tourLevelService.findAll(page, limit));
    }

    @Operation(summary = "获取所用等级列表")
    @GetMapping("/getLevelList")
    public Result<List<TourLevel>> getLevelList() {
        return Result.success(tourLevelService.getAllLevelList());
    }


    @Operation(summary = "删除证件类型信息")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        tourLevelService.delete(id);
        return Result.success();
    }

    @Operation(summary = "修改证件类型信息")
    @PutMapping("/update")
    public Result update(@RequestBody TourLevel data) {
        tourLevelService.update(data);
        return Result.success();
    }


}
