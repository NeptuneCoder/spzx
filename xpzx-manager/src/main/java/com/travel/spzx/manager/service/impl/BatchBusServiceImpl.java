package com.travel.spzx.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.travel.spzx.manager.mapper.BatchBusMapper;
import com.travel.spzx.manager.mapper.BatchDetailMapper;
import com.travel.spzx.manager.service.BatchBusService;
import com.travel.spzx.model.entity.order.BatchBusInfo;
import com.travel.spzx.model.entity.order.BatchBusInfoDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BatchBusServiceImpl implements BatchBusService {
    @Autowired
    private BatchBusMapper batchBusMapper;


    @Override
    public List<BatchBusInfo> findByPage(String batchId) {
        List<BatchBusInfo> brandList = batchBusMapper.findByBatchId(batchId);
        brandList.forEach(brand -> {
            brand.setTourGuideNum(batchBusMapper.queryTourGuideNumByCarIdBatchId(brand.getCarId(), batchId));
            brand.setTouristNum(batchBusMapper.queryTouristNumByCarIdBatchId(brand.getCarId(), batchId));
        });
        return brandList;
    }


    @Override
    public void save(BatchBusInfoDto data) {
        System.out.println(JSON.toJSONString(data));
        //查询当前批次已经绑定的车辆

        List<BatchBusInfo> batchBusList = batchBusMapper.findByBatchId(data.getBatchId());
        //如果删除了之前的车辆，则需要将之前绑定的用户也要一起删除
        batchBusList.forEach(batchBus -> {
            if (!data.getCarIds().contains(batchBus.getCarId())) {
                System.out.println("删除车辆" + batchBus.getCarId());
//                batchBusMapper.deleteByPrimaryKey(batchBus.getCarId(), data.getBatchId());
                //删除当前批次中已经绑定的游客信息
                batchDetailMapper.deleteBusTourist(batchBus.getCarId(), data.getBatchId());
                batchDetailMapper.deleteBusTourGuide(batchBus.getCarId(), data.getBatchId());
            }
        });

        // 先删除原有数据
        batchBusMapper.deleteByBatchId(data.getBatchId());

        data.getCarIds().forEach(carId -> {
            BatchBusInfo bbi = new BatchBusInfo();
            bbi.setCarId(carId);
            bbi.setBatchId(data.getBatchId());
            batchBusMapper.insertSelective(bbi);
        });
    }

    @Autowired
    private BatchDetailMapper batchDetailMapper;

    @Transactional
    @Override
    public void delete(String id, String carId, String batchId) {
        batchBusMapper.deleteByPrimaryKey(id, batchId);
        //删除当前批次中已经绑定的游客信息
        batchDetailMapper.deleteBusTourist(carId, batchId);
        batchDetailMapper.deleteBusTourGuide(carId, batchId);
    }

    @Override
    public void update(BatchBusInfo brand) {
        batchBusMapper.updateByPrimaryKeySelective(brand);
    }


}
