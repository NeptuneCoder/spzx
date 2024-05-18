package com.travel.spzx.model.entity.order;

import com.travel.spzx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class BusTourGuideInfo extends BaseEntity {
    private String tourGuideId;

    private String carId;
    private String nickname;
    private String batchId;

    private String carNo;
}
