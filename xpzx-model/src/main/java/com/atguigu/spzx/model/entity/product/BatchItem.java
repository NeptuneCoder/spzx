package com.atguigu.spzx.model.entity.product;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@Data
public class BatchItem extends BaseEntity {
    @Schema(description = "产品ID")
    private Long productId;
    @Schema(description = "总量")
    private String totalNum;
    @Schema(description = "成团人数")
    private String successNum;

    @Schema(description = "批次开始时间")
    private Date startTime;
    @Schema(description = "批次结束时间")
    private Date endTime;

    private String[] time;

    public String[] getTime() {
        // 创建SimpleDateFormat对象，并指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 使用SimpleDateFormat对象将日期对象格式化为字符串
        String startDate = sdf.format(startTime);
        String endDate = sdf.format(endTime);
        time = new String[]{startDate, endDate};
        return time;
    }

    private String remark;

    @Schema(description = "是否游客报名该批次时是否需要审核：需要审核：0:未审核 1:审核 ")
    private int audit;

    private boolean auditStatus;

    public boolean isAuditStatus() {
        return audit == 1;
    }

    private int status;

}
