package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    void save(OrderItem orderItem);

    List<OrderItem> findByOrderId(@Param("orderId") Long id);


    void cancelOrderItem(@Param("orderId") Long orderId);
}
