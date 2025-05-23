package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    List<UserAddress> findByUserId(Long userId);

    UserAddress getUserAddress(Long userAddressId);
}
