package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.BatchInfoService;
import com.travel.spzx.model.entity.product.BatchItem;
import com.travel.spzx.model.vo.common.Result;
import com.travel.xpzx.utils.BatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/batchInfo")
public class BatchInfoController {

    @Autowired
    private BatchInfoService batchInfoService;

    // 批量添加批次信息
    @GetMapping(value = "/getBatchInfoByProductId/{productId}/{page}/{limit}")
    public Result getBatchInfoByProductId(@PathVariable("productId") Long productId,
                                          @PathVariable("page") int page,
                                          @PathVariable("limit") int limit) {
        return Result.success(batchInfoService.getBatchInfoByProductId(productId, page, limit));
    }

    @GetMapping(value = "/list/{page}/{limit}")
    public Result list(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        return Result.success(batchInfoService.list(page, limit));
    }


    @GetMapping(value = "/findAll")
    public Result findAll(@RequestParam("productId") String productId) {
        List<BatchItem> all = batchInfoService.findAll(productId);
        return Result.success(all);
    }

    @GetMapping(value = "/detail/{batchId}")
    public Result getDetail(@PathVariable("batchId") String batchId) {
        BatchItem all = batchInfoService.getDetailByBatchId(batchId);
        return Result.success(all);
    }


}
