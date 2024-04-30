package com.travel.spzx.manager.service.impl;

import com.travel.spzx.manager.mapper.AssembleLocMapper;
import com.travel.spzx.manager.service.AssembleLocService;
import com.travel.spzx.model.entity.assemble.AssembleLoc;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssembleLocServiceImpl implements AssembleLocService {

    @Autowired
    private AssembleLocMapper assembleLocMapper;

    @Override
    public void save(AssembleLoc assembleLoc) {
        assembleLocMapper.save(assembleLoc);
    }

    @Override
    public void update(AssembleLoc assembleLoc) {
        assembleLocMapper.update(assembleLoc);
    }

    @Override
    public PageInfo<AssembleLoc> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<AssembleLoc> assembleLocs = assembleLocMapper.findByPage();
        return PageInfo.of(assembleLocs);
    }

    @Override
    public void delete(Integer id) {
        assembleLocMapper.delete(id);
    }
}
