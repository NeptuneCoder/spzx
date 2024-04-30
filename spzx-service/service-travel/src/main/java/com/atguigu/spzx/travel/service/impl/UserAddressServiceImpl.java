package com.atguigu.spzx.travel.service.impl;

import com.atguigu.spzx.model.entity.user.UserAddress;

import com.atguigu.spzx.travel.mapper.UserAddressMapper;
import com.atguigu.spzx.travel.service.UserAddressService;
import com.atguigu.xpzx.utils.AuthContextUtil;
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
