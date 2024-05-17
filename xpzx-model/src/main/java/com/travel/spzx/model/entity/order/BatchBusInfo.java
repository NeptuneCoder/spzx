package com.travel.spzx.model.entity.order;

import com.travel.spzx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class BatchBusInfo extends BaseEntity {
    private String car_id;
    private int batch_id;
}
