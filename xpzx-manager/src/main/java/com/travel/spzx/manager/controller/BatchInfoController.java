package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.BatchInfoService;
import com.travel.spzx.model.vo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/batchInfo")
public class BatchInfoController {

    @Autowired
    private BatchInfoService batchInfoService;

    // 批量添加批次信息
    @GetMapping(value = "/getBatchInfoByProductId/{productId}")
    public Result getBatchInfoByProductId(@PathVariable("productId") Long productId) {
        return Result.success(batchInfoService.getBatchInfoByProductId(productId));
    }

}
