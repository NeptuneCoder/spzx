package com.atguigu.spzx.cart.service;

import com.atguigu.spzx.model.entity.h5.CartInfo;

import java.util.List;

public interface CartService {

    void addToCart(String skuId, String skuNum);

    List<CartInfo> getCartList();

    void deleteCart(String skuId);

    void deleteCartAll();

    void checkCart(String skuId, String isChecked);

    void changeCartNum(String skuId, String skuNum);

    void checkAllCart(String isChecked);

    List<CartInfo> getAllChecked();

    void deleteChecked();
}
