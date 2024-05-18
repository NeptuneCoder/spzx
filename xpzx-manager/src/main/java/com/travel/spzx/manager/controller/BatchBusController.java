package com.travel.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.manager.service.BatchBusService;
import com.travel.spzx.manager.service.BusService;
import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.order.BatchBusInfo;
import com.travel.spzx.model.entity.order.BatchBusInfoDto;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/assign/bus")
public class BatchBusController {

    @Autowired
    private BatchBusService batchBusService;

    @Operation(description = "获取当前批次分配的汽车列表信息")
    @GetMapping("/list/{batchId}")
    public Result findByPage(@PathVariable("batchId") String batchId) {
        List<BatchBusInfo> pageInfo = batchBusService.findByPage(batchId);
        //根据分配的车辆信息查询领队信息和游客数据
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("assignCar")
    public Result save(@RequestBody BatchBusInfoDto data) {
        batchBusService.save(data);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("deleteAssignCar/{id}/{carId}/{batchId}")
    public Result delete(@PathVariable("id") String id, @PathVariable("carId") String carId, @PathVariable("batchId") String batchId) {
        batchBusService.delete(id,carId, batchId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}
