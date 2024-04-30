package com.travel.spzx.user.service;

import com.travel.spzx.model.entity.user.UserAddress;

import java.util.List;

public interface UserAddressService {
    List<UserAddress> findUserAddressList();

    UserAddress getUserAddress(Long userAddressId);
}
