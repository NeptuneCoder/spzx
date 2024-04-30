package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.RichTxtService;
import com.travel.spzx.model.entity.richTxt.RichTxtInfo;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "各种富文本内容管理接口")
@RestController
@RequestMapping(value = "/admin/richTxt")
public class RichTxtController {
    @Autowired
    private RichTxtService richTxtService;

    @PostMapping("/save")
    public Result save(@RequestBody RichTxtInfo data) {
        richTxtService.save(data);
        return Result.success();
    }


    @PutMapping("/update")
    public Result update(@RequestBody RichTxtInfo richTxtInfo) {
        richTxtService.update(richTxtInfo);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //修改城市信息接口
    //获取列表
    @GetMapping("/list/{page}/{limit}")
    public Result<PageInfo<RichTxtInfo>> findByPage(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        PageInfo<RichTxtInfo> pageInfo = richTxtService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    //删除城市信息接口
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        //TODO  删除城市之前需要判断城市是否在被使用
        richTxtService.delete(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
