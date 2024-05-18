package com.travel.spzx.model.vo.batch;

import com.travel.spzx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class TouristVO extends BaseEntity {
    /**
     * ti.id,
     * ti.name,
     * ti.id_card_no,
     * ti.sex,
     * ti.phone,
     * ti.age_type
     */

    private String name;
    private String idCardNo;
    private String sex;
    private String phone;
    private String ageType;

    private String carId;
    private String carNo;

}
