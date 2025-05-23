package com.travel.spzx.manager.service;

import com.travel.spzx.model.entity.assemble.AssembleLoc;
import com.github.pagehelper.PageInfo;

public interface AssembleLocService {
    void save(AssembleLoc assembleLoc);

    void update(AssembleLoc assembleLoc);

    PageInfo<AssembleLoc> findByPage(Integer page, Integer limit);

    void delete(Integer id);
}
