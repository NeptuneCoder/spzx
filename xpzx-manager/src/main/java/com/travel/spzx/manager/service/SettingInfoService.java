package com.travel.spzx.manager.service;

import com.travel.spzx.model.dto.config.SettingInfoDTO;
import com.travel.spzx.model.entity.config.SettingInfo;

public interface SettingInfoService {
    void saveSettingInfo(SettingInfoDTO settingInfo);

    SettingInfo getSettingInfoByKey(String key);
}
