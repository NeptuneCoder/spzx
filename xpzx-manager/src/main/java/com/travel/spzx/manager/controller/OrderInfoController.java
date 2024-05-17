package com.travel.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.manager.service.OrderInfoService;
import com.travel.spzx.model.dto.order.OrderDto;
import com.travel.spzx.model.dto.order.OrderStatisticsDto;
import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.travel.spzx.model.vo.order.OrderInfoVo;
import com.travel.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @GetMapping("/getOrderStatisticsData")
    public Result<OrderStatisticsVo> getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        OrderStatisticsVo orderStatisticsVo = orderInfoService.getOrderStatisticsData(orderStatisticsDto);
        return Result.build(orderStatisticsVo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/list/{page}/{limit}")
    public Result<PageInfo<OrderInfoVo>> list(@PathVariable("page") int page, @PathVariable("limit") int limit, OrderDto orderDto) {
        return Result.build(orderInfoService.list(page, limit, orderDto), ResultCodeEnum.SUCCESS);
    }
}
