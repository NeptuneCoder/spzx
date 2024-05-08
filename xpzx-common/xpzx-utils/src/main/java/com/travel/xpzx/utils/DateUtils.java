package com.travel.xpzx.utils;

import java.util.Date;

public class DateUtils {
    //根据年月日 2024-05-5 返回当前的周几
    public static String getCurrentWeek(String date) {
        String[] dateArr = date.split("-");
        int year = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]);
        int day = Integer.parseInt(dateArr[2]);
        int week = 0;
        if (month == 1 || month == 2) {
            year--;
            month += 12;
        }
        int c = year / 100;
        int y = year % 100;
        int m = month;
        int d = day;
        int a = (14 - m) / 12;
        int b = y + c - a;
        int jd = d + (13 * (m + 12 * a) / 5) + 365 * b + c / 4 + 30 * (m - 1) / 12;
        int z = jd % 7;
        if (z == 0) {
            week = 7;
        } else {
            week = z;
        }
        switch (week) {
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 7:
                return "星期日";
            default:
                return "星期日";
        }
    }

    public static String string2Date(String startDate) {
        String[] dateArr = startDate.split("-");

        int month = Integer.parseInt(dateArr[1]);
        int day = Integer.parseInt(dateArr[2]);
        return month + "/" + day;
    }

    //根据年月日

}
