package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.AssembleLocService;
import com.travel.spzx.model.entity.assemble.AssembleLoc;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag("乘车地点相关接口")
@RestController
@RequestMapping(value = "/admin/assembleLoc")
public class AssembleLocController {
    @Autowired
    private AssembleLocService assembelLocService;

    //增加城市信息接口
    @PostMapping("/save")
    public Result save(@RequestBody AssembleLoc assembleLoc) {
        assembelLocService.save(assembleLoc);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/update")
    public Result update(@RequestBody AssembleLoc assembleLoc) {
        assembelLocService.update(assembleLoc);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //修改城市信息接口
    //获取列表
    @GetMapping("/list/{page}/{limit}")
    public Result<PageInfo<AssembleLoc>> findByPage(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        PageInfo<AssembleLoc> pageInfo = assembelLocService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    //删除城市信息接口
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        //TODO  删除城市之前需要判断城市是否在被使用
        assembelLocService.delete(id);
        return Result.success();
    }

}
