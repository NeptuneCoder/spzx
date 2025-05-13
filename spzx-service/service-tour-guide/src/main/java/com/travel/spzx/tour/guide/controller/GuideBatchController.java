package com.travel.spzx.tour.guide.controller;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.vo.batch.GuideBatchDetailVo;
import com.travel.spzx.model.vo.batch.TourGuideVo;
import com.travel.spzx.model.vo.batch.TouristDetailVo;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.tour.guide.service.GuideBatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "会员用户接口")
@RestController
@RequestMapping("api/v1/guide/batch")
public class GuideBatchController {
    @Autowired
    private GuideBatchService orderService;


    // TODO: 获取当前领队（id）的已经带过的和即将带的批次列表
    @Operation(description = "获取当前领队（id）的已经带过的和即将带的批次列表")
    @GetMapping("/leader/batchList/{page}/{limit}")
    public Result leaderBatchList(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, @RequestParam("type") String type) {
        PageInfo<GuideBatchDetailVo> batchVOList = orderService.getLeaderBatchList(page, limit, type);
        return Result.success(batchVOList);
    }

    @Operation(description = "通过批次id查询当前批次信息")
    @GetMapping("/detail/{batchId}")
    public Result getBatchInfo(@PathVariable("batchId") String batchId) {
        GuideBatchDetailVo batchVO = orderService.getBatchInfo(batchId);
        return Result.success(batchVO);
    }

    //TODO : 根据当前批次和领队id及状态（全部，已签到，未签到）获取全部的游客列表
    @Operation(description = "获取当前领队（id）的已经带过的和即将带的批次列表")
    @GetMapping("/trip/list/{page}/{limit}")
    public Result getCurrentBatchTripList(@PathVariable("page") Integer page,
                                          @PathVariable("limit") Integer limit,
                                          @RequestParam("type") String type,
                                          @RequestParam("batchId") String batchId,
                                          @RequestParam("name") String name,
                                          @RequestParam("carId") String carId) {
        PageInfo<TouristDetailVo> batchVOList = orderService.getCurrentBatchTripList(page, limit, type, batchId, name, carId);
        return Result.success(batchVOList);
    }
    //TODO : 签到功能，该功能可根据配置领队条件进行开发(可根据后台配置，主领队，副领队，实习领队满足条件才能签到否则只有查看功能)
    //TODO : 根据当前批次和车辆信息或去改批次的领队信息


    @PutMapping("/tourist/sign/{batchId}/{tripId}/{orderItemId}/{orderId}")
    public Result sign(@PathVariable("batchId") String batchId,
                       @PathVariable("tripId") String tripId,
                       @PathVariable("orderId") String orderId,
                       @PathVariable("orderItemId") String orderItemId) {
        Long rowId = orderService.touristSign(batchId, tripId, orderId, orderItemId);
        return Result.success(rowId);
    }

    @PutMapping("/tourist/unsign/{batchId}/{tripId}/{orderItemId}/{orderId}")
    public Result unSign(@PathVariable("batchId") String batchId,
                         @PathVariable("tripId") String tripId,
                         @PathVariable("orderId") String orderId,
                         @PathVariable("orderItemId") String orderItemId) {
        Long rowId = orderService.touristUnSign(batchId, tripId, orderId, orderItemId);
        return Result.success(rowId);
    }

    ///api/v1/batch/guide/list
//    current/batch/tourGuide/list
    @GetMapping("current/batch/tourGuide/list")
    public Result getCurrentBatchTourGuideList(@RequestParam("batchId") String batchId, @RequestParam("carId") String carId) {

        // TODO: 根据当前批次获取当前领队列表
        List<TourGuideVo> tourGuideDetailVoList = orderService.getCurrentBatchTourGuideList(batchId, carId);
        return Result.success(tourGuideDetailVoList);
    }

    //行程完成接口
    @PutMapping("completed/{batchId}")
    public Result tripComplete(@PathVariable("batchId") String batchId) {
        orderService.tripComplete(batchId);
        return Result.success();
    }
}
