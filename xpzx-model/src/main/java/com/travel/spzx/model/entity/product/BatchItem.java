package com.travel.spzx.model.entity.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class BatchItem extends BaseEntity {

    @Schema(description = "产品ID")
    private Long productId;
    @Schema(description = "产品名称")
    private String productName;
    @Schema(description = "总量")
    private Integer totalNum;
    @Schema(description = "成团人数")
    private Integer successNum;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "批次开始时间")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "批次结束时间")
    private Date endTime;

    private String[] time;
    @Schema(description = "批次在商品详情界面中需要的字段")
    private String dateStr;


    public String[] getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 创建SimpleDateFormat对象，并指定日期格式
        // 使用SimpleDateFormat对象将日期对象格式化为字符串
        if (startTime != null && endTime != null) {
            String startDate = sdf.format(startTime);
            String endDate = sdf.format(endTime);
            time = new String[]{startDate, endDate};
            return time;
        }
        return new String[0];
    }

    private String remark;

    @Schema(description = "是否游客报名该批次时是否需要审核：需要审核：0:未审核 1:审核 ")
    private int audit;

    private boolean auditStatus;

    public boolean isAuditStatus() {
        return audit == 1;
    }

    private int status;

    private String statusStr;

    @Schema(description = "行程状态：0:已成团 1:可报名 2:已满员")
    private int tripStatus;

    @Schema(description = "销量")
    private Integer saleNum;


    @Schema(description = "持续时间")
    private String duration;

    @Schema(description = "出发时间")
    private String startDate;
    @Schema(description = "出发时间")
    private String startWeak;

    @Schema(description = "结束时间")
    private String endDate;
    @Schema(description = "结束时间")
    private String endWeak;

    private List<BatchItem> children;
    private boolean hasChildren;

}
