package com.travel.spzx.model.vo.batch;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "领队信息")
@Data
public class TourGuideVo {

    private String nickname;

    private String phone;
}
