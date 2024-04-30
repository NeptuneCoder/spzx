package com.travel.spzx.manager.task;

import cn.hutool.core.date.DateUtil;
import com.travel.spzx.manager.mapper.OrderInfoMapper;
import com.travel.spzx.manager.mapper.OrderStatisticsMapper;
import com.travel.spzx.model.entity.order.OrderStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class OrderStatisticsTask {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    //cron表达式：秒 分 时 日 月 周 年(可选)
    @Scheduled(cron = "0/10 * * *  * ?")  // 定义定时任务，使用@Scheduled注解指定调度时间表达式
    public void helloWorldTask() {
        String createTime = DateUtil.offsetDay(new Date(), -1).toString(new SimpleDateFormat("yyyy-MM-dd"));
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(createTime);
//        if (orderStatistics != null) {
//            orderStatisticsMapper.insert(orderStatistics);
//        }
    }
}
