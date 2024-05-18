package com.travel.spzx.model.entity.order;

import com.travel.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "给游客飞陪车辆信息")
@Data
public class BusTourGuideDto extends BaseEntity {
    private String carId;
    private String batchId;
    private List<String> tourGuideIds;
}
