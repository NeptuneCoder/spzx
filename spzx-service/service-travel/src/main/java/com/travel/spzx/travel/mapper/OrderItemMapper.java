package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    void save(OrderItem orderItem);

    List<OrderItem> findOrderItemByOrderId(@Param("orderId") Long id);

    void updateOrderItemOrderStatus(@Param("orderId") Long orderId, @Param("orderStatus") Integer orderStatus);

    List<OrderItem> queryCurBatchAllOrderItem(@Param("batchId") Long batchId);
}
