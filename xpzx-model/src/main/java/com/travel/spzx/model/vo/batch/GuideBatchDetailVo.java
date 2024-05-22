package com.travel.spzx.model.vo.batch;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;


@Data
@Schema(description = "领队端批次详情")
public class GuideBatchDetailVo {
    @Schema(description = "批次id")
    private Integer batchId;
    @Schema(description = "车型No")
    private String carNo;
    @Schema(description = "汽车id")
    private Integer carId;
    @Schema(description = "产品名字")
    private String productName;
    //开始年 ，结束年，开始日期，结束日期，时长，
    //startYear ,endYear, startDate, endDate, startWeek,duration,
    private String startYear;
    private String endYear;
    private String startDate;
    private String startDateStr;
    private String endDate;
    private String endDateStr;
    private Date startTime;
    private Date endTime;
    private int startWeek;
    private String startWeekStr;

    private int endWeek;
    private String endWeekStr;
    private String duration;

    // 已报名人数，多少成人，多少儿童，
    //applyNum,adultNum,childNum,
    private int totalNum;
    private int adultNum;
    private int childNum;
    // 批次状态（是否结束，待出行）出发地点，集合时间，出发时间
    @Schema(description = "批次状态")// -待出行，1-出行中，2-已结束,3-未成团,4-管理员取消
    private String batchStatus;
    private String statusStr;
    @Schema(description = "集合时间")
    private String assembleTime;

    @Schema(description = "出发地点")
    private String cityName;
    private String address;
    @Schema(description = "出发时间")
    private String departureTime;


    public String[] getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 创建SimpleDateFormat对象，并指定日期格式
        // 使用SimpleDateFormat对象将日期对象格式化为字符串
        if (startTime != null && endTime != null) {
            String startDate = sdf.format(startTime);
            String endDate = sdf.format(endTime);
            String[] time = new String[]{startDate, endDate};
            return time;
        }
        return new String[0];
    }

    ;
}
