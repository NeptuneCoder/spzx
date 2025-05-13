package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.SettingInfoService;
import com.travel.spzx.model.dto.config.SettingInfoDTO;
import com.travel.spzx.model.entity.config.SettingInfo;
import com.travel.spzx.model.vo.common.Result;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag("系统配置功能")
@RestController
@RequestMapping(value = "/admin/system/config")
public class SettingInfoController {
    @Autowired
    private SettingInfoService settingInfoService;

    @PostMapping(value = "/save")
    public Result saveSettingInfo(@RequestBody SettingInfoDTO settingInfo) {
        settingInfoService.saveSettingInfo(settingInfo);
        // TODO 保存系统配置信息
        return Result.success();
    }

    @GetMapping(value = "/detail/{key}")
    public Result getSettingInfo(@PathVariable("key") String key) {
        SettingInfo settingInfo = settingInfoService.getSettingInfoByKey(key);
        return Result.success(settingInfo);
    }
}
