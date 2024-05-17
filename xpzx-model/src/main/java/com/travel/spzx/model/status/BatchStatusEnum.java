package com.travel.spzx.model.status;

public enum BatchStatusEnum {
    INIT(0, "新建"),
    //已成团
    GROUPING(4, "已成团"),
    //待出行
    WAITING(5, "待出行"),
    //出行中
    TRAVELING(6, "出行中"),
    FINISHED(1, "已完成"),
    //以下状态为没有成团的原因
    //未成团
    UNFINISHED(2, "未成团"),
    //解散
    DISSOLVED(3, "管理员解散");


    private int value;
    private String desc;

    BatchStatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }


}
