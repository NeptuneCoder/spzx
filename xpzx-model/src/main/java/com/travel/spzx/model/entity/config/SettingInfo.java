package com.travel.spzx.model.entity.config;

import com.travel.spzx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class SettingInfo extends BaseEntity {

    private String key;
    private String content;
    private String remark;

}
