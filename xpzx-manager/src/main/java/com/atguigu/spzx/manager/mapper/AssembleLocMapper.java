package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.assemble.AssembleLoc;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssembleLocMapper {


    void save(AssembleLoc assembleLoc);

    void update(AssembleLoc assembleLoc);

    List<AssembleLoc> findByPage();

    void delete(Integer id);
}
