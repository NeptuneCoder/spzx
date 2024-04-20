package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.city.CityInfo;
import com.atguigu.spzx.model.entity.richTxt.RichTxtInfo;
import com.github.pagehelper.PageInfo;

public interface RichTxtService {
    void save(RichTxtInfo data);

    void delete(Integer id);

    PageInfo<RichTxtInfo> findByPage(Integer page, Integer limit);

    void update(RichTxtInfo cityInfo);

}
