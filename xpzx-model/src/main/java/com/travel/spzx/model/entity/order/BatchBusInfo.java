package com.travel.spzx.model.entity.order;

import com.travel.spzx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class BatchBusInfo extends BaseEntity {
    private String carId;
    private String batchId;
    private String carNo;
    private String seatNum;
    private String driverName;
    private String driverPhone;
    private String remark;
    private int touristNum;
    private int tourGuideNum;
}
