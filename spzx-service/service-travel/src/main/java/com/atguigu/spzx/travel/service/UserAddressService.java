package com.atguigu.spzx.travel.service;

import com.atguigu.spzx.model.entity.user.UserAddress;

import java.util.List;

public interface UserAddressService {
    List<UserAddress> findUserAddressList();

    UserAddress getUserAddress(Long userAddressId);
}
