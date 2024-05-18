package com.travel.spzx.manager.service.impl;

import com.travel.spzx.manager.service.HomeService;
import com.travel.spzx.model.vo.home.HomeVo;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {
    @Override
    public HomeVo getHomeData() {
        HomeVo homeVo = new HomeVo();
        return homeVo;
    }
}
