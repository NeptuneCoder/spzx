package com.travel.spzx.feign.user;

import com.travel.spzx.model.entity.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-user")
public interface UserFeignClient {

    @GetMapping("/api/user/userAddress/auth/getUserAddress/{id}")
    UserAddress getUserAddress(@PathVariable("id") Long userAddressId);
}
