package com.travel.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.manager.service.BatchDetailService;
import com.travel.spzx.model.entity.order.BusTourGuideDto;
import com.travel.spzx.model.entity.order.BusTourGuideInfo;
import com.travel.spzx.model.entity.order.BusTouristDto;
import com.travel.spzx.model.entity.order.BusTouristInfo;
import com.travel.spzx.model.vo.batch.TouristVO;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.guide.TourGuideVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/batch/detail")
public class BatchDetailController {

    @Autowired
    private BatchDetailService batchDetailService;
    //获取游客列表；根据批次从订单中获取用户列表

    //获取批次中已经成交的游客信息
    @GetMapping(value = "/tourist/list/{page}/{limit}")
    public Result getTouristList(@PathVariable("page") int page,
                                 @PathVariable("limit") int limit,
                                 @RequestParam(value = "batchId", required = false) Integer batchId,
                                 @RequestParam(value = "touristName", required = false) String touristName) {
        PageInfo<TouristVO> pageInfo = batchDetailService.getTouristList(page, limit, batchId, touristName);
        return Result.success(pageInfo);
    }

    //获取领队列表
    //游客分配车辆信息
    @PostMapping(value = "/tourist/assignCar")
    public Result touristAssignCar(@RequestBody BusTouristDto busTouristDto) {
        batchDetailService.touristAssignCar(busTouristDto);
        return Result.success();
    }


    //获取当前批次游客已经分配汽车的用户
    @GetMapping(value = "/assignedCar/tourist/list")
    public Result getAssignedCarTouristList(@RequestParam("carId") String carId,
                                            @RequestParam("batchId") String batchId) {
        List<BusTouristInfo> touristList = batchDetailService.getAssignedCarTouristList(carId, batchId);
        return Result.success(touristList);
    }


    //获取批次中已经成交的游客信息
    @GetMapping(value = "/tourGuide/list/{page}/{limit}")
    public Result getTourGuideList(@PathVariable("page") int page,
                                   @PathVariable("limit") int limit,
                                   @RequestParam(value = "batchId", required = false) String batchId,
                                   @RequestParam(value = "tourGuideName", required = false) String tourGuideName) {
        PageInfo<TourGuideVO> pageInfo = batchDetailService.getTourGuideList(page, limit, batchId, tourGuideName);
        return Result.success(pageInfo);
    }

    //获取领队列表
    //游客分配车辆信息
    @PostMapping(value = "/car/assignTourGuide")
    public Result tourGuideAssignCar(@RequestBody BusTourGuideDto busTouristDto) {
        batchDetailService.tourGuideAssignCar(busTouristDto);
        return Result.success();
    }


    //获取当前批次游客已经分配汽车的用户
    @GetMapping(value = "/assignedCar/tourGuide/list")
    public Result getAssignedCarTourGuideList(@RequestParam("carId") String carId,
                                            @RequestParam("batchId") String batchId) {
        List<BusTourGuideInfo> touristList = batchDetailService.getAssignedCarTourGuideList(carId, batchId);
        return Result.success(touristList);
    }
}
