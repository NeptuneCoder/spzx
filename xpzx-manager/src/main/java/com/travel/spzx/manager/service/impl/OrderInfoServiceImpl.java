package com.travel.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.manager.mapper.OrderInfoMapper;
import com.travel.spzx.manager.mapper.OrderItemMapper;
import com.travel.spzx.manager.mapper.OrderStatisticsMapper;
import com.travel.spzx.manager.service.OrderInfoService;
import com.travel.spzx.model.dto.order.OrderDto;
import com.travel.spzx.model.dto.order.OrderStatisticsDto;
import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.entity.order.OrderItem;
import com.travel.spzx.model.entity.order.OrderStatistics;
import com.travel.spzx.model.vo.order.OrderInfoVo;
import com.travel.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {

        // 查询统计结果数据
        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(orderStatisticsDto);

        //日期列表
        List<String> dateList = orderStatisticsList.stream().map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd")).collect(Collectors.toList());

        //统计金额列表
        List<BigDecimal> amountList = orderStatisticsList.stream().map(OrderStatistics::getTotalAmount).collect(Collectors.toList());

        // 创建OrderStatisticsVo对象封装响应结果数据
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(amountList);

        // 返回数据
        return orderStatisticsVo;
    }

    @Override
    public PageInfo<OrderInfoVo> list(int page, int limit, OrderDto orderDto) {
        PageHelper.startPage(page, limit);
        List<OrderInfoVo> res = orderInfoMapper.findList(orderDto);
        res.forEach(OrderInfoVo::convertStatusText);
        return new PageInfo<>(res);
    }

    @Override
    public OrderInfoVo orderItemList(String orderId) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(orderId);
        List<OrderInfoVo> res = orderInfoMapper.findList(orderDto);
        if (res.isEmpty()) {
            return null;
        }
        OrderInfoVo orderInfoVo = res.get(0);
        orderInfoVo.convertStatusText();
        orderInfoVo.convertBatchStatusText();
        List<OrderItem> orderItemList = orderItemMapper.findOrderItemList(orderId);
        orderItemList.forEach(OrderItem::convertStatusText);
        orderInfoVo.setOrderItemList(orderItemList);
        return orderInfoVo;
    }
}
