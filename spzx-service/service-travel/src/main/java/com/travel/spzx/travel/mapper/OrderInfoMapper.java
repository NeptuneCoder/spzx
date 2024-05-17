package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.entity.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderInfoMapper {
    void save(OrderInfo orderInfo);

    OrderInfo getById(Long orderId);

    List<OrderInfo> findUserPage(@Param("userId") Long userId, @Param("orderStatus") String orderStatus);


    OrderInfo getByOrderNo(String orderNo);

    void updateById(OrderInfo orderInfo);

    void updateOrderStatus(@Param("id") Long id, @Param("orderStatus") Integer orderStatus, @Param("cancelReason") String cancelReason);


    List<OrderInfo> findWaitPayOrder();

    void mockPaySuccess(@Param("orderId") Long id, @Param("status") Integer status);

    int findOrderStatusById(@Param("orderId")  Long orderId);
}
