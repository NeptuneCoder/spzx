package com.travel.spzx.model.vo.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.spzx.model.entity.product.BatchItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class BatchDetailVo {
    private BatchInfoVo batchInfo;
}
