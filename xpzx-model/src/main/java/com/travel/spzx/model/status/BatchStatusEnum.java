package com.travel.spzx.model.status;

import lombok.Getter;

@Getter
public enum BatchStatusEnum {
    //可以报名
    CAN_APPLY(0, "可以报名"),
    //停止报名
    STOP_APPLY(1, "停止报名"),
    //已满员
    WAITING(2, "待出行"),
    //出行中
    //什么时候触发该状态的产生？
    TRAVELING(3, "出行中"),
    //TODO
    FINISHED(4, "已完成"),
    //以下状态为没有成团的原因
    //未成团
    UNFINISHED(5, "未成团"),//TODO 未成团如何触发？
    //解散
    DISSOLVED(6, "管理员解散");


    private int code;
    private String desc;

    BatchStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
