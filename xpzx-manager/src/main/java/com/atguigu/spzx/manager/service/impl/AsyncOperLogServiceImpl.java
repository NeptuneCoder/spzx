package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.log.service.AsyncOperLogService;
import com.atguigu.spzx.manager.mapper.AsyncOperLogMapper;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {
    @Autowired
    AsyncOperLogMapper asyncOperLogMapper;

    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        //TODO  写入数据库
        asyncOperLogMapper.insert(sysOperLog);
    }
}
