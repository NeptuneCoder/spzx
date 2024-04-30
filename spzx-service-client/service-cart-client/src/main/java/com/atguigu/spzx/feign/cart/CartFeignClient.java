package com.atguigu.spzx.feign.cart;

import com.atguigu.spzx.model.entity.h5.CartInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "service-cart")//指定提供服务的微服务名称
public interface CartFeignClient {
    //这里的地址需要完整的地址，否者调用不到
    @GetMapping(value = "/api/order/cart/auth/getAllCkecked")
    public List<CartInfo> getAllChecked();

    @GetMapping("/api/order/cart/auth/deleteChecked")
    void deleteChecked();

}
