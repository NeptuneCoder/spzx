package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.IdType.IdTypeInfoDto;
import com.atguigu.spzx.model.entity.IdType.IdTypeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IdTypeInfoMapper {
    void save(IdTypeInfo data);

    List<IdTypeInfo> findAll();


    void delete(Integer id);

    void update(IdTypeInfo data);
}
