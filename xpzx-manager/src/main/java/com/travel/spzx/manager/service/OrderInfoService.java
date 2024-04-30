package com.travel.spzx.manager.service;

import com.travel.spzx.model.dto.order.OrderStatisticsDto;
import com.travel.spzx.model.vo.order.OrderStatisticsVo;

public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);


}
