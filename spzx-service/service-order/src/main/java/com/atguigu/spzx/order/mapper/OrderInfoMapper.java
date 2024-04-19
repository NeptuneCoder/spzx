package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.entity.order.OrderInfo;
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
}
