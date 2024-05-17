package com.travel.spzx.model.entity.order;

import com.travel.spzx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class BusTourGuideInfo extends BaseEntity {
    private String bus_id;
    private int tour_guide_id;
}
