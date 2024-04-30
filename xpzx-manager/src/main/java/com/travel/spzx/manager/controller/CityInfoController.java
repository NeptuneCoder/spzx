package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.CityInfoService;
import com.travel.spzx.model.entity.city.CityInfo;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "文件上传到minio相关接口")
@RestController
@RequestMapping(value = "/admin/cityInfo")
public class CityInfoController {
    @Autowired
    private CityInfoService cityInfoService;

    //增加城市信息接口
    @PostMapping("/save")
    public Result save(@RequestBody CityInfo cityInfo) {
        cityInfoService.save(cityInfo);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/update")
    public Result update(@RequestBody CityInfo cityInfo) {
        cityInfoService.update(cityInfo);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //修改城市信息接口
    //获取列表
    @GetMapping("/list/{page}/{limit}")
    public Result<PageInfo<CityInfo>> findByPage(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        PageInfo<CityInfo> pageInfo = cityInfoService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    @GetMapping("/getAll")
    public Result<List<CityInfo>> getAll() {
        List<CityInfo> data = cityInfoService.getAll();
        return Result.build(data, ResultCodeEnum.SUCCESS);
    }

    //删除城市信息接口
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        //TODO  删除城市之前需要判断城市是否在被使用
        cityInfoService.delete(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
