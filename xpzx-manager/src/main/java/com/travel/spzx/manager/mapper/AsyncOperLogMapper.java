package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AsyncOperLogMapper {
    void insert(SysOperLog sysOperLog);
}
