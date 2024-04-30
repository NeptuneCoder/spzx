package com.travel.spzx.travel.service.impl;

import com.travel.spzx.model.entity.user.UserAddress;

import com.travel.spzx.travel.mapper.UserAddressMapper;
import com.travel.spzx.travel.service.UserAddressService;
import com.travel.xpzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {


    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> findUserAddressList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        return userAddressMapper.findByUserId(userId);
    }

    @Override
    public UserAddress getUserAddress(Long userAddressId) {
        return userAddressMapper.getUserAddress(userAddressId);
    }
}
