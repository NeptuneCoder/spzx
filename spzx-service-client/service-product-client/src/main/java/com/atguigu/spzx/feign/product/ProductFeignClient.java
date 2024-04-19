package com.atguigu.spzx.feign.product;

import com.atguigu.spzx.model.dto.product.SkuSaleDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 该类注释
 */
//该类的作用是为了暴露对外提供的远程调用接口
@FeignClient(value = "service-product")
public interface ProductFeignClient {
    @GetMapping("/api/product/getBySkuId/{skuId}")
    ProductSku getBySkuId(@PathVariable("skuId") Long skuId);

    @PostMapping("/api/product/updateSkuSaleNum")
    Boolean updateSkuSaleNum(@RequestBody List<SkuSaleDto> skuSaleDtoList);
}
