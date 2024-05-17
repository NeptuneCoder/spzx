package com.travel.spzx.model.vo.product;

import com.travel.spzx.model.vo.protocol.ProtocolVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderProductInfoVo {
    private String productId;
    private String name;
    private String feature;
    private String startDate;
    private String endDate;
    private String startWeak;
    private String endWeak;
    private String duration;
    private BigDecimal adultPrice;
    private BigDecimal childPrice;
    @Schema(description = "剩余数量")
    private int surplusNum;

    private String assembleAddress;
    private String assembleTime;


    private List<ProtocolVo> protocolVoList;

}
