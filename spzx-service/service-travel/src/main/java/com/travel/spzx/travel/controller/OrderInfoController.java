package com.travel.spzx.travel.controller;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.dto.h5.OrderInfoDto;
import com.travel.spzx.model.dto.order.CancelReasonDto;
import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.travel.spzx.model.vo.h5.TradeVo;
import com.travel.spzx.travel.service.OrderInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@Tag(name = "订单管理")
@RestController
@RequestMapping(value = "/api/order/orderInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @GetMapping("/auth/getOrderInfoByOrderNo/{orderNo}")
    public Result<OrderInfo> getOrderInfoByOrderNo(@PathVariable("orderNo") String orderNo) {
        return Result.build(orderInfoService.getOrderInfoByOrderNo(orderNo), ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "提交订单")
    @PostMapping("auth/createOrder")
    public Result<Long> submitOrder
            (@Parameter(name = "orderInfoDto", description = "请求参数实体类", required = true) @RequestBody @Valid OrderInfoDto
                     orderInfoDto) {
        Long orderId = orderInfoService.createOrder(orderInfoDto);
        return Result.build(orderId, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "通过订单id获取订单详情")
    @GetMapping("/auth/detail/{orderId}")
    public Result<OrderInfo> detail(@PathVariable("orderId") Long orderId) {
        OrderInfo orderInfo = orderInfoService.orderDetail(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "获取订单信息")
    @GetMapping("auth/{orderId}")
    public Result<OrderInfo> getOrderInfo
            (@Parameter(name = "orderId", description = "订单id", required = true) @PathVariable("orderId") Long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    ///api/order/orderInfo/auth/${id}/cancel
    @Operation(summary = "获取订单信息")
    @PutMapping("auth/{orderId}/cancel")
    public Result<OrderInfo> cancelOrder
    (@Parameter(name = "orderId", description = "订单id", required = true) @PathVariable("orderId") Long orderId, @RequestBody CancelReasonDto cancelReason) {
        OrderInfo orderInfo = orderInfoService.cancelOrder(orderId, cancelReason.getCancelReason());
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "申请退款")
    @PutMapping("auth/{orderId}/receipt")
    public Result receipt
            (@Parameter(name = "orderId", description = "订单id", required = true) @PathVariable("orderId") Long orderId) {
        boolean orderInfo = orderInfoService.receipt(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "立即购买")
    @GetMapping("auth/buy/{skuId}")
    public Result<TradeVo> buy
            (@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable("skuId") Long skuId) {
        TradeVo tradeVo = orderInfoService.buy(skuId);
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取订单分页列表")
    @GetMapping("auth/list")
    public Result<PageInfo<OrderInfo>> list(
            @Parameter(name = "page", description = "当前页码", required = true)
            @RequestParam(name = "page") Integer page,
            @Parameter(name = "limit", description = "每页记录数", required = true)
            @RequestParam(name = "limit") Integer limit,
            @Parameter(name = "orderStatus", description = "订单状态", required = false)
            @RequestParam(required = false, defaultValue = "", name = "orderState") String orderState) {
        PageInfo<OrderInfo> pageInfo = orderInfoService.findUserPage(page, limit, orderState);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/auth/updateOrderStatusPayed/{orderNo}/{orderStatus}")
    public Result updateOrderStatus(@PathVariable(value = "orderNo") String
                                            orderNo, @PathVariable(value = "orderStatus") Integer orderStatus) {
        orderInfoService.updateOrderStatus(orderNo, orderStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
