package com.travel.spzx.travel.config;

import com.travel.spzx.common.constant.RedisConstantKey;
import com.travel.spzx.travel.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpirationListener implements MessageListener {

    @Autowired
    private OrderInfoService orderInfoService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("Redis key expiration listener received message: " + message);

        //将超时的订单状态改为超时未支付
        String msg = message.toString();
        if (msg.contains(RedisConstantKey.ORDER_INFO_KEY)) {
            String[] split = msg.split(":");
            if (split.length == 3) {
                String orderId = split[2];
                System.out.println("订单超时，订单号：" + orderId);
                //TODO 更新订单状态未超时未支付
//                orderInfoService.updateOrderInfo(orderId, PayStateEnum.PayTimeOut);
            }
        }

    }
}
