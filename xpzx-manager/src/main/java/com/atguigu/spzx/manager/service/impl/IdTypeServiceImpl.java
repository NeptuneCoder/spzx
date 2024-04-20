package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.IdTypeInfoMapper;
import com.atguigu.spzx.manager.service.IdTypeService;
import com.atguigu.spzx.model.dto.IdType.IdTypeInfoDto;
import com.atguigu.spzx.model.entity.IdType.IdTypeInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdTypeServiceImpl implements IdTypeService {
    @Autowired
    private IdTypeInfoMapper idTypeInfoMapper;

    @Override
    public void save(IdTypeInfo data) {
        idTypeInfoMapper.save(data);
    }

    @Override
    public PageInfo<IdTypeInfo> findAll(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<IdTypeInfo> list = idTypeInfoMapper.findAll();
        PageInfo<IdTypeInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    @Override
    public void delete(Integer id) {
        idTypeInfoMapper.delete(id);
    }

    @Override
    public void update(IdTypeInfo data) {
        idTypeInfoMapper.update(data);
    }
}
