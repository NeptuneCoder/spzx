package com.travel.spzx.travel.utils;

import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.model.status.OrderStateEnum;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WaitPay(0, "待支付"), //
 * PaySuccess(1, "支付成功"), //
 * //待消费
 * PayFailed(2, "支付失败"), //
 * SignInSuccess(3, "签到完成"),
 * <p>
 * UnSignIn(4, "未签到"), //
 * <p>
 * //待评价
 * WaitComment(4, "待评价"), //
 * <p>
 * CommentSuccess(5, "评价成功"), //
 * <p>
 * ApplyRefund(6, "申请退款"), //
 * //同意退款
 * RefundAgree(7, "同意退款"), //
 * <p>
 * <p>
 * Refunding(8, "退款中"), //
 * //拒绝退款
 * RefundRefuse(9, "拒绝退款"), //
 * <p>
 * RefundSuccess(10, "退款成功"), //
 * //操作员取消退款
 * OperatorCancelRefund(11, "操作员取消退款"), //
 * //未成团取消退款
 * GroupCancelRefund(12, "未成团取消退款"), //
 * <p>
 * <p>
 * RefundFailed(11, "退款失败"), //
 * <p>
 * CommentTimeOut(-5, "评价超时"), //
 * <p>
 * UserCancel(-1, "用户取消"), //
 * //未成团取消
 * <p>
 * PayTimeOut(-2, "超时未支付取消"), //
 * GroupCancel(-3, "未成团取消"), //
 * //操作员取消
 * OperatorCancel(-4, "操作员取消"), //
 */
@Component
public class OrderStatusHelper {
    Map<Integer, List<Integer>> sysStatusMap = new HashMap<>();

    public OrderStatusHelper() {
        initOrderStatusMap();
    }

    private void initOrderStatusMap() {
        sysStatusMap.put(OrderStateEnum.WaitPay.getCode(),
                Arrays.asList(OrderStateEnum.PaySuccess.getCode(),
                        OrderStateEnum.PayFailed.getCode(),
                        OrderStateEnum.UserCancel.getCode(),
                        OrderStateEnum.PayTimeOut.getCode(),
                        OrderStateEnum.OrderTimeOut.getCode()));

        sysStatusMap.put(OrderStateEnum.PaySuccess.getCode(),
                Arrays.asList(OrderStateEnum.ApplyAllRefund.getCode(),
                        OrderStateEnum.SignInSuccess.getCode(),
                        OrderStateEnum.UnSignIn.getCode(),
                        OrderStateEnum.GroupCancelRefund.getCode(),
                        OrderStateEnum.OperatorCancel.getCode()));

        //申请退款
        sysStatusMap.put(OrderStateEnum.ApplyAllRefund.getCode(),
                Arrays.asList(OrderStateEnum.AllRefundAgree.getCode(),
                        OrderStateEnum.AllRefundRefuse.getCode()));

        sysStatusMap.put(OrderStateEnum.AllRefundAgree.getCode(),
                Arrays.asList(OrderStateEnum.AllRefundSuccess.getCode()));
        sysStatusMap.put(OrderStateEnum.AllRefundRefuse.getCode(),
                Arrays.asList(OrderStateEnum.RefundFailed.getCode()));
        //签到成功
        sysStatusMap.put(OrderStateEnum.SignInSuccess.getCode(),
                Arrays.asList(OrderStateEnum.TravelComplete.getCode()));
        //未签到
        sysStatusMap.put(OrderStateEnum.UnSignIn.getCode(),
                Arrays.asList(OrderStateEnum.ApplyAllRefund.getCode()));

        //已完成
        sysStatusMap.put(OrderStateEnum.TravelComplete.getCode(),
                Arrays.asList(OrderStateEnum.CommentSuccess.getCode(),
                        OrderStateEnum.CommentTimeOut.getCode()));
        //未成团取消
        ////管理员取消

    }


    public boolean isValidNextStatus(int currentStatus, int nextCode) {
        boolean contains = sysStatusMap.getOrDefault(currentStatus, Arrays.asList()).contains(nextCode);
        System.out.println("当前状态：" + currentStatus + "，下一状态：" + nextCode + "，是否合法：" + contains);
        if (!contains) {
            throw GuiguException.build("订单状态异常，请检查当前状态是否正确");
        }

        return contains;
    }
}
