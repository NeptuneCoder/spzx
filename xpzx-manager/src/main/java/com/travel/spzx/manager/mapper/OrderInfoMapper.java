package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.dto.order.OrderDto;
import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.entity.order.OrderStatistics;
import com.travel.spzx.model.vo.order.OrderInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper {
    OrderStatistics selectOrderStatistics(String createTime);


    List<OrderInfoVo> findList(OrderDto orderDto);
}
