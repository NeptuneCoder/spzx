package com.travel.spzx.manager.service.impl;

import com.travel.spzx.manager.mapper.RichTxtInfoMapper;
import com.travel.spzx.manager.service.RichTxtService;
import com.travel.spzx.model.entity.richTxt.RichTxtInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RichTxtServiceImpl implements RichTxtService {
    @Autowired
    private RichTxtInfoMapper richTxtInfoMapper;

    @Override
    public void save(RichTxtInfo data) {
        // TODO Auto-generated method stub
        String resultTxt = StringEscapeUtils.escapeXml11(data.getContent());
        data.setContent(resultTxt);
        System.out.println(resultTxt);
        richTxtInfoMapper.save(data);
    }

    @Override
    public void delete(Integer id) {
        richTxtInfoMapper.delete(id);
    }

    @Override
    public PageInfo<RichTxtInfo> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<RichTxtInfo> collect = richTxtInfoMapper.findAll().stream().map(richTxtInfo -> {
            String content = StringEscapeUtils.unescapeXml(richTxtInfo.getContent());
            richTxtInfo.setContent(content);
            return richTxtInfo;
        }).collect(Collectors.toList());
        PageInfo<RichTxtInfo> pageInfo = new PageInfo<>(collect);
        return pageInfo;
    }

    @Override
    public void update(RichTxtInfo cityInfo) {
        // TODO Auto-generated method stub
        String resultTxt = StringEscapeUtils.escapeXml11(cityInfo.getContent());
        cityInfo.setContent(resultTxt);
        richTxtInfoMapper.update(cityInfo);
    }
}
