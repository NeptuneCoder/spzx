package com.travel.spzx.order.service;

import com.travel.spzx.model.dto.h5.OrderInfoDto;
import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.vo.h5.TradeVo;
import com.github.pagehelper.PageInfo;

public interface OrderInfoService {
    TradeVo getTrade();

    Long submitOrder(OrderInfoDto orderInfoDto);

    OrderInfo getOrderInfo(Long orderId);

    TradeVo buy(Long skuId);

    PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, String orderStatus);

    OrderInfo getOrderInfoByOrderNo(String orderNo);

    void updateOrderStatus(String orderNo, Integer orderStatus);
}
