package com.travel.spzx.manager.service;

import com.travel.spzx.model.entity.IdType.IdTypeInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IdTypeService {
    void save(IdTypeInfo data);

    PageInfo<IdTypeInfo> findAll(int page, int limit);
    List<IdTypeInfo> getAllList();

    void delete(Integer id);

    void update(IdTypeInfo data);


}
