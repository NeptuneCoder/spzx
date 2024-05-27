package com.travel.xpzx.utils;

import com.travel.spzx.model.entity.product.BatchItem;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class BatchUtils {


    public static void computeTripState(BatchItem v) {

        //TODO 处理成团状态
        Integer totalNum = v.getTotalNum();
        Integer successNum = v.getSuccessNum();
        //0:已成团 1:可报名 2:已满员
        if (Objects.equals(v.getSaleNum(), totalNum)) {
            v.setTripStatus(2);
            v.setStatusStr("已满员");
            //已满员
        } else if (v.getSaleNum() >= successNum) {
            v.setTripStatus(0);
            v.setStatusStr("已成团");
            //已成团
        } else {
            //可以报名
            v.setTripStatus(1);
            v.setStatusStr("可报名");
        }
    }

    public static void computeBaseInfo(BatchItem v, String[] timeArray) {
        String startDate = timeArray[0];
        String startWeek = DateUtils.getCurrentWeek(startDate);
        String[] startDateArray = startDate.split("-");
        String[] endDateArray = timeArray[1].split("-");
        String res = startDateArray[1] + "/" + startDateArray[2] + "(" + startWeek + ")-" + endDateArray[1] + "/" + endDateArray[2];
        v.setDateStr(res);
        v.setStartDate(DateUtils.string2Date(startDate));
        v.setStartWeak(startWeek);
        String endDate = timeArray[1];
        String endWeek = DateUtils.getCurrentWeek(endDate);
        v.setEndDate(DateUtils.string2Date(endDate));
        v.setEndWeak(endWeek);
    }

    @NotNull
    public static String computeDuration(String[] strings) {
        if (strings.length != 2) {
            return "0天";
        }
        if (strings[0].equals(strings[1])) {
            return "1天";
        }
        String[] startTimeSplit = strings[0].split("-");
        LocalDate date1 = LocalDate.of(Integer.valueOf(startTimeSplit[0]), Integer.valueOf(startTimeSplit[1]), Integer.valueOf(startTimeSplit[2]));
        String[] endTimeSplit = strings[1].split("-");
        LocalDate date2 = LocalDate.of(Integer.valueOf(endTimeSplit[0]), Integer.valueOf(endTimeSplit[1]), Integer.valueOf(endTimeSplit[2]));
        long between = ChronoUnit.DAYS.between(date1, date2) + 1;
        return between == 1 ? "1天" : between + "天" + (between - 1) + "夜";
    }
}
