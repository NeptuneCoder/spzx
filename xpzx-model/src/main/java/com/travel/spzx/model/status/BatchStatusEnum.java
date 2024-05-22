package com.travel.spzx.model.status;

public enum BatchStatusEnum {

    //待出行
    WAITING(1, "待出行"),
    //出行中
    //什么时候触发该状态的产生？
    TRAVELING(2, "出行中"),
    //TODO
    FINISHED(3, "已完成"),
    //以下状态为没有成团的原因
    //未成团
    UNFINISHED(4, "未成团"),//TODO 未成团如何触发？
    //解散
    DISSOLVED(5, "管理员解散");


    private int value;
    private String desc;

    BatchStatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }


}
