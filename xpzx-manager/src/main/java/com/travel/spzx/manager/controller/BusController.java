package com.travel.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.manager.service.BrandService;
import com.travel.spzx.manager.service.BusService;
import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.product.Brand;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/carinfo")
public class BusController {

    @Autowired
    private BusService brandService;

    @GetMapping("/findAll/{page}/{limit}")
    public Result<PageInfo<BusInfo>> findByPage(@PathVariable("page") Integer page,
                                                @PathVariable("limit") Integer limit,
                                                @RequestParam(value = "carNo", required = false) String carNo,
                                                @RequestParam(value = "driverName", required = false) String driverName) {
        PageInfo<BusInfo> pageInfo = brandService.findByPage(page, limit, carNo, driverName);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("save")
    public Result save(@RequestBody BusInfo brand) {
        brandService.save(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        brandService.delete(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("update")
    public Result update(@RequestBody BusInfo brand) {
        brandService.update(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll() {
        List<BusInfo> list = brandService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
