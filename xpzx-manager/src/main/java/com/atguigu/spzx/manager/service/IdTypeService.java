package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.IdType.IdTypeInfoDto;
import com.atguigu.spzx.model.entity.IdType.IdTypeInfo;
import com.github.pagehelper.PageInfo;

public interface IdTypeService {
    void save(IdTypeInfo data);

    PageInfo<IdTypeInfo> findAll(int page, int limit);

    void delete(Integer id);

    void update(IdTypeInfo data);
}
