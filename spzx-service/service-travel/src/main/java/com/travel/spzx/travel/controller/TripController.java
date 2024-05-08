package com.travel.spzx.travel.controller;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.trip.TripInfo;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.travel.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tripInfo/auth")
public class TripController {
    @Autowired
    private TripService tripService;

    @PostMapping("/addTrip")
    public Result addTrip(@RequestBody TripInfo tripInfo) {
        tripService.addTrip(tripInfo);
        return Result.success();
    }

    @GetMapping("/list/{page}/{limit}")
    public Result<PageInfo<TripInfo>> list(@PathVariable("page") int page, @PathVariable("limit") int limit) {
        PageInfo<TripInfo> pageInfo = tripService.list(page, limit);
        return Result.success(pageInfo);
    }

    @GetMapping("/detail/{tripId}")
    public Result<TripInfo> getDetail(@PathVariable("tripId") String tripId) {
        TripInfo tripInfo = tripService.getDetail(tripId);
        return Result.success(tripInfo);
    }

    @PutMapping("/update")
    public Result updateTrip(@RequestBody TripInfo tripInfo) {
        tripService.updateTrip(tripInfo);
        return Result.success();
    }

    @DeleteMapping("/delete/{tripId}")
    public Result deleteTrip(@PathVariable("tripId") String tripId) {
        tripService.deleteTrip(tripId);
        return Result.success();
    }


}
