package com.travel.spzx.manager.service.impl;

import cn.hutool.core.lang.Pair;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.manager.mapper.BatchBusMapper;
import com.travel.spzx.manager.mapper.BatchDetailMapper;
import com.travel.spzx.manager.service.BatchDetailService;
import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.order.*;
import com.travel.spzx.model.vo.batch.TouristVO;
import com.travel.spzx.model.vo.guide.TourGuideVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BatchDetailServiceImpl implements BatchDetailService {

    @Autowired
    private BatchDetailMapper batchDetailMapper;

    @Autowired
    private BatchBusMapper batchBusMapper;

    @Override
    public PageInfo<TouristVO> getTouristList(int page, int limit, Integer batchId, String touristName) {
        PageHelper.startPage(page, limit);
        List<TouristVO> touristVOS = batchDetailMapper.querySuccessTouristByBatchId(batchId, touristName);
        PageInfo<TouristVO> pageInfo = new PageInfo<>(touristVOS);
        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void touristAssignCar(BusTouristDto busTouristDto) {

        //TODO  查询当前批次的所有车辆信息
        List<BatchBusInfo> carList = batchBusMapper.findByBatchId(busTouristDto.getBatchId());
        carList.forEach(bus -> {
            bus.setTourGuideNum(batchBusMapper.queryTourGuideNumByCarIdBatchId(bus.getCarId(), busTouristDto.getBatchId()));
            bus.setTouristNum(batchBusMapper.queryTouristNumByCarIdBatchId(bus.getCarId(), busTouristDto.getBatchId()));
        });
        //判断当前批次是否包含该车辆
        if (carList.stream().noneMatch(bus -> bus.getCarId().equals(busTouristDto.getCarId()))) {
            throw GuiguException.build("当前批次不包含该车辆");
        }

        //判断当前车辆是否还有空余座位
        BatchBusInfo batchBusInfo = carList.stream().filter(bus -> bus.getCarId().equals(busTouristDto.getCarId())).findFirst().get();
        //查询当前车辆分配的导游数量
        int tourGuideNum = batchDetailMapper.queryTourGuideNumByCarAndBatchId(busTouristDto.getCarId(), busTouristDto.getBatchId());
        if ((Integer.valueOf(batchBusInfo.getSeatNum()) - tourGuideNum) < busTouristDto.getTouristIds().size()) {
            throw GuiguException.build("当前车辆没有足够的座位");
        }

        //给游客分配车辆
        //TODO 该车的位置数量是否满足分配的游客数量+导游数量

        //TODO 判断当前用户是否已经分配给了其他车辆，如果分配了就取消掉之前的分配
        //TODO  检查用户是否分配到了其他车辆中，如果分配了则进行取消
        List<BatchBusInfo> otherCarList = carList.stream().filter(bus -> !bus.getCarId().equals(busTouristDto.getCarId())).collect(Collectors.toList());
        System.out.println("当前批次有分配了几辆车？" + (otherCarList.size() + 1));
//        busTouristDto.getTouristIds().stream().flatMap(item1 -> otherCarList.stream().map(item2 -> new Pair(item1, item2)));
        otherCarList.forEach(bus -> {
            busTouristDto.getTouristIds().forEach(touristId -> {
                batchDetailMapper.delAssignOhterBusTourist(touristId, bus.getCarId(), bus.getBatchId());
            });
        });

        batchDetailMapper.deleteBusTourist(busTouristDto.getCarId(), busTouristDto.getBatchId());
        busTouristDto.getTouristIds().forEach(touristId -> {
            BusTouristInfo busTouristInfo = new BusTouristInfo();
            busTouristInfo.setCarId(busTouristDto.getCarId());
            busTouristInfo.setBatchId(busTouristDto.getBatchId());
            busTouristInfo.setTourId(touristId);
            batchDetailMapper.saveBusTourist(busTouristInfo);
        });
    }

    /**
     * 获取指定车辆指定批次的已分配乘客列表
     *
     * @param carId
     * @param batchId
     * @return
     */
    @Override
    public List<BusTouristInfo> getAssignedCarTouristList(String carId, String batchId) {
        List<BusTouristInfo> touristList = batchDetailMapper.queryAssignedCarTourist(carId, batchId);
        return touristList;
    }

    @Override
    public PageInfo<TourGuideVO> getTourGuideList(int page, int limit, String batchId, String tourGuideName) {
        PageHelper.startPage(page, limit);
        List<TourGuideVO> tourGuideVOS = batchDetailMapper.queryTourGuideList(batchId, tourGuideName);
        PageInfo<TourGuideVO> pageInfo = new PageInfo<>(tourGuideVOS);
        return pageInfo;
    }

    @Override
    public void tourGuideAssignCar(BusTourGuideDto busTouristDto) {
        //TODO  查询当前批次的所有车辆信息
        List<BatchBusInfo> carList = batchBusMapper.findByBatchId(busTouristDto.getBatchId());
        carList.forEach(bus -> {
            bus.setTouristNum(batchBusMapper.queryTouristNumByCarIdBatchId(bus.getCarId(), busTouristDto.getBatchId()));
            bus.setTourGuideNum(batchBusMapper.queryTourGuideNumByCarIdBatchId(bus.getCarId(), busTouristDto.getBatchId()));
        });
        System.out.println("当前批次有几辆车？" + JSON.toJSONString(carList));
        //判断当前批次是否包含该车辆
        if (carList.stream().noneMatch(bus -> bus.getCarId().equals(busTouristDto.getCarId()))) {
            throw GuiguException.build("当前批次不包含该车辆");
        }

        //判断当前车辆是否还有空余座位
        BatchBusInfo batchBusInfo = carList.stream().filter(bus -> bus.getCarId().equals(busTouristDto.getCarId())).findFirst().get();
        //查询当前车辆分配的导游数量
        int touristNum = batchDetailMapper.queryTouristNumByCarAndBatchId(busTouristDto.getCarId(), busTouristDto.getBatchId());
        if ((Integer.valueOf(batchBusInfo.getSeatNum()) - touristNum) < busTouristDto.getTourGuideIds().size()) {
            throw GuiguException.build("当前车辆没有足够的座位");
        }

        //获取同意批次的其他车辆信息分配的改领队
        List<BatchBusInfo> otherCarList = carList.stream().filter(bus -> !bus.getCarId().equals(busTouristDto.getCarId())).collect(Collectors.toList());
        System.out.println("当前批次有分配了几辆车？" + (otherCarList.size() + 1));
//        busTouristDto.getTouristIds().stream().flatMap(item1 -> otherCarList.stream().map(item2 -> new Pair(item1, item2)));
        otherCarList.forEach(bus -> {
            busTouristDto.getTourGuideIds().forEach(tourGuideId -> {
                batchDetailMapper.delAssignOtherBusTourGuide(tourGuideId, bus.getCarId(), bus.getBatchId());
            });
        });

//删除当前车辆中所有的记录
        batchDetailMapper.deleteBusTourGuide(busTouristDto.getCarId(), busTouristDto.getBatchId());
        busTouristDto.getTourGuideIds().forEach(tourGuideId -> {
            BusTourGuideInfo busTouristInfo = new BusTourGuideInfo();
            busTouristInfo.setCarId(busTouristDto.getCarId());
            busTouristInfo.setBatchId(busTouristDto.getBatchId());
            busTouristInfo.setTourGuideId(tourGuideId);
            batchDetailMapper.saveBusTourGuide(busTouristInfo);
        });
    }

    @Override
    public List<BusTourGuideInfo> getAssignedCarTourGuideList(String carId, String batchId) {
        List<BusTourGuideInfo> touristList = batchDetailMapper.queryAssignedCarTourGuide(carId, batchId);
        return touristList;
    }
}
