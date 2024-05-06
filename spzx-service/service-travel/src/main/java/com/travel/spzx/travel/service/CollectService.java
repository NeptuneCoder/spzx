package com.travel.spzx.travel.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.collect.Collect;

public interface CollectService {
    PageInfo<Collect> queryCollectList(int page, int limit);

    int isCollect(Long productId);

    void collect(Long productId);

    void cancelCollect(Long productId);
}
