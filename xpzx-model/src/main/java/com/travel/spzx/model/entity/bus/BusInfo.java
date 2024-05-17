package com.travel.spzx.model.entity.bus;

import com.travel.spzx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class BusInfo extends BaseEntity {
    private String carNo;
    private int seatNum;
    private String driverName;
    private String driverPhone;
    private String remark;
}
