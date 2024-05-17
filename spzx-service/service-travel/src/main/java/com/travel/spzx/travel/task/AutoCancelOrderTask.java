package com.travel.spzx.travel.task;

import cn.hutool.core.date.DateUtil;
import com.travel.spzx.travel.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class AutoCancelOrderTask {
    @Autowired
    private OrderInfoService orderInfoService;

    //查询所有未支付且创建时间超过30分钟的订单，并将订单状态改为取消
    //cron表达式：秒 分 时 日 月 周 年(可选)
    @Scheduled(cron = "0/5 * * *  * ?")  // 定义定时任务，使用@Scheduled注解指定调度时间表达式
    public void cancelPayTimeOutTask() {
//        String createTime = DateUtil.offsetDay(new Date(), -1).toString(new SimpleDateFormat("yyyy-MM-dd"));
//        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(createTime);
//        if (orderStatistics != null) {
//            orderStatisticsMapper.insert(orderStatistics);
//        }
        //查询超过30分钟且支付状态未待支付的订单
        orderInfoService.cancelTimeOutOrder();
        System.out.println("cancelPayTimeOut Task");
    }
}
