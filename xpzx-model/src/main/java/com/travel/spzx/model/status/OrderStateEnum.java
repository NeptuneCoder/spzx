package com.travel.spzx.model.status;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * summary: 支付状态枚举
 * author: 杨海
 * date: 2024/5/11
 */
@Getter // 提供获取属性值的getter方法
public enum OrderStateEnum {
    WaitPay(0, "待支付"), //
    PaySuccess(1, "支付成功"), //
    //订单过期


    //待消费
    PayFailed(2, "支付失败"), //


    UserCancel(-1, "用户取消"), //

    PayTimeOut(-2, "超时未支付取消"), //


    SignInSuccess(3, "已签到"),
    //部分签到（order_info和order_item存一对多的关系时，order_info的状态为部分签到，order_item的状态为未签到
    PartSignIn(16, "部分签到"), // 在订单中显示几人签到，几人未签到

    UnSignIn(4, "未签到"), //

    //待评价,该状态由领队触发，表示当前行程已经结束，用户可以进行评价功能
    //出行完成
    TravelComplete(14, "出行完成"), //
    //部分出现完成
    PartTravelComplete(19, "部分出行完成"), //

    OperatorCancelRefund(12, "操作员取消退款"), //
    //未成团取消退款
    GroupCancelRefund(13, "未成团取消退款"), //

    //订单完成
    OrderTimeOut(15, "订单超时"), //

    WaitComment(5, "待评价"), //
    PartComment(17, "部分评价"), //
    PartCommentSuccess(18, "部分评价成功"), //

    CommentSuccess(6, "评价成功"), //

    ApplyRefund(7, "申请退款"), //
    //同意退款
    RefundAgree(8, "同意退款"), //


    Refunding(9, "退款中"), //
    //拒绝退款
    RefundRefuse(10, "拒绝退款"), //

    RefundSuccess(11, "退款成功"), //
    //操作员取消退款



    //未成团取消


    RefundFailed(-3, "退款失败"), //
    //操作员取消
    OperatorCancel(-4, "操作员取消"), //
    CommentTimeOut(-5, "评价超时"), //

    ;

    private Integer code;      // 业务状态码
    private String message;    // 响应消息

    private OrderStateEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
