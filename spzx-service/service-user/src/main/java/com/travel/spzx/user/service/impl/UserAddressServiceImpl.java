package com.travel.spzx.user.service.impl;

import com.travel.spzx.model.entity.user.UserAddress;
import com.travel.spzx.user.mapper.UserAddressMapper;
import com.travel.spzx.user.service.UserAddressService;
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
        System.out.println("userId:" + userId);
        return userAddressMapper.findByUserId(userId);
    }

    @Override
    public UserAddress getUserAddress(Long userAddressId) {
        return userAddressMapper.getUserAddress(userAddressId);
    }
}
