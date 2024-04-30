package com.travel.spzx.common.log.service;

import com.travel.spzx.model.entity.system.SysOperLog;

public interface AsyncOperLogService {
    void saveSysOperLog(SysOperLog sysOperLog);
}
