package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.IdTypeService;
import com.travel.spzx.model.entity.IdType.IdTypeInfo;
import com.travel.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "证件类型信息，增加，删除，修改，获取列表")
@RestController
@RequestMapping("/admin/idType")
public class IdTypeController {

    @Autowired
    private IdTypeService idTypeService;

    @Operation(summary = "保存证件类型信息")
    @PostMapping("/save")
    public Result save(@RequestBody IdTypeInfo data) {
        idTypeService.save(data);
        return Result.success();
    }

    @Operation(summary = "获取证件类型列表")
    @GetMapping("/findAll/{page}/{limit}")
    public Result<PageInfo<IdTypeInfo>> findAll(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        return Result.success(idTypeService.findAll(page, limit));
    }

    @Operation(summary = "获取所有证件类型列表")
    @GetMapping("/getAllList")
    public Result<PageInfo<IdTypeInfo>> findAllList() {
        return Result.success(idTypeService.getAllList());
    }

    @Operation(summary = "删除证件类型信息")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        idTypeService.delete(id);
        return Result.success();
    }

    @Operation(summary = "修改证件类型信息")
    @PutMapping("/update")
    public Result update(@RequestBody IdTypeInfo data) {
        idTypeService.update(data);
        return Result.success();
    }
}
