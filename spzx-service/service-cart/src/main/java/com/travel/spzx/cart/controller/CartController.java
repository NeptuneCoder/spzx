package com.travel.spzx.cart.controller;

import com.travel.spzx.cart.service.CartService;
import com.travel.spzx.model.entity.h5.CartInfo;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order/cart")
public class CartController {
    @Autowired
    CartService cartService;


    @GetMapping("/auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable("skuId") String skuId, @PathVariable("skuNum") String skuNum) {
        cartService.addToCart(skuId, skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @GetMapping("/auth/cartList")
    public Result<List<CartInfo>> cartList() {
        List<CartInfo> cartInfoList = cartService.getCartList();
        return Result.build(cartInfoList, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/auth/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable("skuId") String skuId) {
        cartService.deleteCart(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/auth/clearCart")
    public Result deleteCartAll() {
        cartService.deleteCartAll();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * @param skuId
     * @param isChecked 0:未选中 1:选中
     * @return
     */
    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable("skuId") String skuId,
                            @PathVariable("isChecked") String isChecked) {
        cartService.checkCart(skuId, isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/auth/changeCartNum/{skuId}/{skuNum}")
    public Result changeCartNum(@PathVariable("skuId") String skuId,

                                @PathVariable("skuNum") String skuNum) {
        cartService.changeCartNum(skuId, skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/auth/checkAllCart/{isChecked}")
    public Result checkAllCart(@PathVariable("isChecked") String isChecked) {
        cartService.checkAllCart(isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 提供远程调用的接口,用于订单中点击结算时获取选中的购物车信息
     *
     * @return
     */
    @Operation(summary = "选中的购物车")
    @GetMapping(value = "/auth/getAllCkecked")
    public List<CartInfo> getAllCkecked() {
        List<CartInfo> cartInfoList = cartService.getAllChecked();
        return cartInfoList;
    }

    @Operation(summary = "删除购物车")

    @GetMapping(value = "/auth/deleteChecked")
    public Result deleteChecked() {
        cartService.deleteChecked();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
