package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.dto.order.OrderDto;
import com.travel.spzx.model.entity.order.OrderItem;
import com.travel.spzx.model.entity.order.OrderStatistics;
import com.travel.spzx.model.vo.order.OrderInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    List<OrderItem> findOrderItemList(@Param("orderId") String  orderId);
}
