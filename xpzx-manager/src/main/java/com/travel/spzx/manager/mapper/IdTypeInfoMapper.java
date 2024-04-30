package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.IdType.IdTypeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IdTypeInfoMapper {
    void save(IdTypeInfo data);

    List<IdTypeInfo> findAll();


    void delete(Integer id);

    void update(IdTypeInfo data);
}
