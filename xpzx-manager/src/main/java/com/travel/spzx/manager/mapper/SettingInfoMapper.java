package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.dto.config.SettingInfoDTO;
import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.config.SettingInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SettingInfoMapper {


    void saveSettingInfo(SettingInfo settingInfo);

    SettingInfo getSettingInfoByKey(String key);

    void updateSettingInfo(SettingInfo settingInfo);

}
