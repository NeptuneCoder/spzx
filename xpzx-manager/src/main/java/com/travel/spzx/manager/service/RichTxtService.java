package com.travel.spzx.manager.service;

import com.travel.spzx.model.entity.richTxt.RichTxtInfo;
import com.github.pagehelper.PageInfo;

public interface RichTxtService {
    void save(RichTxtInfo data);

    void delete(Integer id);

    PageInfo<RichTxtInfo> findByPage(Integer page, Integer limit);

    void update(RichTxtInfo cityInfo);

}
