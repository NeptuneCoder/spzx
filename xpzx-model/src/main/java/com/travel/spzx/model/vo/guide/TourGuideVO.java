package com.travel.spzx.model.vo.guide;

import com.travel.spzx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class TourGuideVO extends BaseEntity {
    /**
     * ti.id,
     * ti.name,
     * ti.id_card_no,
     * ti.sex,
     * ti.phone,
     * ti.age_type
     */

    private String nickname;
    private String phone;
    private String level;

    private String carId;
    private String carNo;

}
