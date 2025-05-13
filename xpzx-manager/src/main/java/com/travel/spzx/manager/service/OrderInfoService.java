package com.travel.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.dto.order.OrderDto;
import com.travel.spzx.model.dto.order.OrderStatisticsDto;
import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.vo.order.OrderInfoVo;
import com.travel.spzx.model.vo.order.OrderStatisticsVo;
import org.mockito.internal.matchers.Or;

public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);


    PageInfo<OrderInfoVo> list(int page, int limit, OrderDto orderDto);

    OrderInfoVo orderItemList(String orderId);
}
