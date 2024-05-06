package com.travel.spzx.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.collect.Collect;
import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.spzx.travel.mapper.CollectMapper;
import com.travel.spzx.travel.service.CollectService;
import com.travel.xpzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Override
    public PageInfo<Collect> queryCollectList(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<Collect> list = collectMapper.queryCollectList();
        return new PageInfo<>(list);
    }


    @Override
    public int isCollect(Long productId) {
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        Collect collect = collectMapper.queryCollect(userInfo.getId(), productId);
        if (collect == null) {
            return 0;
        }
        int collectState = collect.getStatus();
        return collectState;
    }

    @Override
    public void collect(Long productId) {
        //先判断是否有这条数据，如果没有则插入数据
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        Collect collect = collectMapper.queryCollect(userInfo.getId(), productId);
        if (collect == null) {
            collect = new Collect();
            collect.setUserId(userInfo.getId());
            collect.setProductId(productId);
            collect.setStatus(1);
            collectMapper.insertCollect(collect);
        } else {
            collectMapper.updateCollect(userInfo.getId(), productId, 1);
        }
    }

    @Override
    public void cancelCollect(Long productId) {
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        Collect collect = collectMapper.queryCollect(userInfo.getId(), productId);
        if (collect == null) {
            collect = new Collect();
            collect.setUserId(userInfo.getId());
            collect.setProductId(productId);
            collect.setStatus(0);
            collectMapper.insertCollect(collect);
        } else {
            collectMapper.updateCollect(userInfo.getId(), productId, 0);
        }
    }
}
