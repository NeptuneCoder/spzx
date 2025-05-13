package com.travel.spzx.travel.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.dto.h5.OrderInfoDto;
import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.status.OrderStateEnum;
import com.travel.spzx.model.vo.h5.TradeVo;

public interface OrderInfoService {
    TradeVo getTrade();

    Long createOrder(OrderInfoDto orderInfoDto);

    OrderInfo getOrderInfo(Long orderId);

    TradeVo buy(Long skuId);

    PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, String orderStatus);

    OrderInfo getOrderInfoByOrderNo(String orderNo);

    void updateOrderStatus(String orderNo, Integer orderStatus);

    void updateOrderInfo(Long orderId, OrderStateEnum orderStatus,String note);

    OrderInfo orderDetail(Long orderId);

    void cancelTimeOutOrder();

    OrderInfo cancelOrder(Long orderId, String cancelReason);

    void mockPay(Long id);

    boolean receipt(Long orderId);
}
