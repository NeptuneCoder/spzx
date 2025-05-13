package com.travel.spzx.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.travel.spzx.manager.mapper.SettingInfoMapper;
import com.travel.spzx.manager.service.SettingInfoService;
import com.travel.spzx.model.dto.config.SettingInfoDTO;
import com.travel.spzx.model.entity.config.SettingInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingInfoServiceImpl implements SettingInfoService {
    @Autowired
    private SettingInfoMapper settingInfoMapper;

    @Override
    public void saveSettingInfo(SettingInfoDTO settingInfoDto) {
        SettingInfo settingInfo = new SettingInfo();
        BeanUtils.copyProperties(settingInfoDto, settingInfo);
        SettingInfo settingInfoResult = settingInfoMapper.getSettingInfoByKey(settingInfoDto.getKey());
        if (settingInfoResult == null) {
            settingInfoMapper.saveSettingInfo(settingInfo);
        } else {
            settingInfoMapper.updateSettingInfo(settingInfo);
        }

    }

    @Override
    public SettingInfo getSettingInfoByKey(String key) {

        return settingInfoMapper.getSettingInfoByKey(key);
    }
}
