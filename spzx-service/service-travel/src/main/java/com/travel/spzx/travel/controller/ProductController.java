package com.travel.spzx.travel.controller;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.dto.product.PageInfoDto;
import com.travel.spzx.model.dto.product.ProductDto;
import com.travel.spzx.model.entity.product.Product;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.product.OrderProductInfoVo;
import com.travel.spzx.travel.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "产品相关接口", description = "获取产品列表，获取产品详情等")
@RestController
@RequestMapping("/api/product/goods/")
public class ProductController {
    @Autowired
    private ProductService productService;

    //获取产品列表
    @Operation(summary = "获取产品列表", description = "获取产品列表")
    @RequestMapping("list/{page}/{limit}")
    public Result<PageInfo<Product>> getProductList(@PathVariable("page") int page, @PathVariable("limit") int limit, ProductDto productDto) {

        PageInfo<Product> productList = productService.getProductList(page, limit, productDto);

        return Result.success(productList);
    }

    @Operation(summary = "获取产品详情", description = "获取产品详情")
    @RequestMapping("detail/{id}/{category2Id}")
    public Result<Product> getProductDetail(@PathVariable("id") Long id, @PathVariable("category2Id") Long category2Id) {
        Product product = productService.getProductDetail(id, category2Id);
        return Result.success(product);
    }

    @Operation(summary = "获取产品详情", description = "获取产品详情")
    @RequestMapping("detail/order/{id}/{batchId}")
    public Result<OrderProductInfoVo> getDetailByProductIdAndBatchId(@PathVariable("id") Long id, @PathVariable("batchId") Long batchId) {
        System.out.println("id:" + id + " batchId:" + batchId);
        OrderProductInfoVo product = productService.getDetailByProductIdAndBatchId(id, batchId);
        return Result.success(product);
    }
}
