package com.travel.spzx.model.entity.order;

import com.travel.spzx.model.entity.base.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class BatchBusInfoDto  {
    private List<String> carIds;
    private String  batchId;
}
