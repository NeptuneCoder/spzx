package com.travel.spzx.travel.service.impl;

import com.travel.spzx.travel.service.OrderInfoService;
import com.travel.spzx.travel.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private OrderInfoService orderInfoService;

    @Override
    public void mockPay(Long id) {
        orderInfoService.mockPay(id);

    }
}
