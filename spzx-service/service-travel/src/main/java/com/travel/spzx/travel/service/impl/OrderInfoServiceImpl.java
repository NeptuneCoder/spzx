package com.travel.spzx.travel.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.common.constant.RedisConstantKey;
import com.travel.spzx.common.exception.GuiguException;

import com.travel.spzx.model.dto.h5.OrderInfoDto;
import com.travel.spzx.model.dto.trip.TripInfoDto;
import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.entity.order.OrderItem;
import com.travel.spzx.model.entity.order.OrderLog;
import com.travel.spzx.model.status.OrderStateEnum;
import com.travel.spzx.model.entity.product.BatchItem;
import com.travel.spzx.model.entity.product.Product;
import com.travel.spzx.model.entity.product.ProductSku;
import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.spzx.model.vo.h5.TradeVo;

import com.travel.spzx.travel.mapper.*;
import com.travel.spzx.travel.service.OrderInfoService;
import com.travel.spzx.travel.utils.OrderStatusHelper;
import com.travel.xpzx.utils.AuthContextUtil;
import com.travel.xpzx.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private BatchInfoMapper batchInfoMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 这个是根据购物车界面中，点击立即下单调用的接口
     *
     * @return
     */
    @Override
    public TradeVo getTrade() {

        // 获取当前登录的用户的id
        //Long userId = AuthContextUtil.getUserInfo().getId();
        //
        //todo 立刻下单 获取选中的购物项列表数据
//        List<CartInfo> cartInfoList = cartFeignClient.getAllChecked();
        List<OrderItem> orderItemList = new ArrayList<>();
//        for (CartInfo cartInfo : cartInfoList) {        // 将购物项数据转换成功订单明细数据
//            OrderItem orderItem = new OrderItem();
//            orderItem.setSkuId(cartInfo.getSkuId());
//            orderItem.setSkuName(cartInfo.getSkuName());
//            orderItem.setSkuNum(cartInfo.getSkuNum());
//            orderItem.setSkuPrice(cartInfo.getCartPrice());
//            orderItem.setThumbImg(cartInfo.getImgUrl());
//            orderItemList.add(orderItem);
//        }

        //todo 计算总金额
        BigDecimal totalAmount = new BigDecimal(0);
//        for (OrderItem orderItem : orderItemList) {
//            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
//        }
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);
        return tradeVo;

    }


    //业务接口实现
    @Transactional
    @Override
    public Long createOrder(OrderInfoDto orderInfoDto) {

        Product product = productMapper.selectById(orderInfoDto.getProductId());
        if (product == null) {
            throw GuiguException.build("商品不存在");
        }
        // 数据校验
        List<TripInfoDto> tripsList = orderInfoDto.getTrips();
        if (CollectionUtils.isEmpty(tripsList)) {
            throw GuiguException.build("出行人不能为空");
        }
//        OrderItem
        //校验商品数量是否充足

        List<ProductSku> productSkus = productSkuMapper.selectByProductId(orderInfoDto.getProductId());
        if (productSkus.isEmpty()) {
            throw GuiguException.build("商品基本信息不存在");
        }

        //校验库存是否充足
        BatchItem batchItem = batchInfoMapper.selectByBatchId(Long.valueOf(orderInfoDto.getBatchId()));
        if (batchItem == null) {
            throw GuiguException.build("当前批次无库存");
        }
        if (batchItem.getStatus() == 1) {
            throw GuiguException.build("该批次停止报名");
        }

        if ((batchItem.getTotalNum() - batchItem.getSaleNum()) < orderInfoDto.getAdultNum() + orderInfoDto.getChildNum()) {
            throw GuiguException.build("剩余库存不足");
        }

        if (orderInfoDto.getAdultNum() == 0) {
            throw GuiguException.build("成人数量不能为空");
        }
        if (orderInfoDto.getChildNum() < 0 || orderInfoDto.getChildNum() < 0) {
            throw GuiguException.build("购买数量异常");
        }

        //校验是否有优惠券
        //更新批次中销售数量
        batchInfoMapper.updateSaleNum(batchItem.getId(), orderInfoDto.getAdultNum() + orderInfoDto.getChildNum());
        ProductSku productSku = productSkus.get(0);

        //TODO 构建订单项数据
        OrderInfo orderInfo = new OrderInfo();

        // 构建订单数据，保存订单
        UserInfo userInfo = AuthContextUtil.getUserInfo();


        //用户id
        orderInfo.setUserId(userInfo.getId());
        //用户昵称
        orderInfo.setNickName(userInfo.getNickName());
        orderInfo.setProductId(orderInfoDto.getProductId());
        orderInfo.setSkuId(productSku.getId());
        orderInfo.setBatchId(batchItem.getId());
        orderInfo.setLinkMan(orderInfoDto.getLinkMan());
        orderInfo.setLinkTel(orderInfoDto.getLinkTel());
        orderInfo.setAdultPrice(productSku.getSalePrice());
        orderInfo.setChildPrice(productSku.getChildSalePrice());
        orderInfo.setAdultNum(orderInfoDto.getAdultNum());
        orderInfo.setChildNum(orderInfoDto.getChildNum());
        //订单编号
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));

        BigDecimal childSalePrice = productSku.getChildSalePrice();
        BigDecimal salePrice = productSku.getSalePrice();
        BigDecimal adultAmount = salePrice.multiply(new BigDecimal(orderInfoDto.getAdultNum()));
        BigDecimal childAmount = childSalePrice.multiply(new BigDecimal(orderInfoDto.getChildNum()));
        BigDecimal totalAmount = adultAmount.add(childAmount);
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        //是否购买保险
        orderInfo.setInsurance(0L);
//        orderInfo.setInsuranceNo("");
        orderInfo.setPayType(1);
        orderInfo.setOrderStatus(OrderStateEnum.WaitPay.getCode());
        orderInfo.setRemark(orderInfoDto.getRemark());
        orderInfoMapper.save(orderInfo);

        //保存订单明细
        for (TripInfoDto tripInfoDto : tripsList) {
            OrderItem item = new OrderItem();
            item.setOrderId(orderInfo.getId());
            item.setProductId(orderInfoDto.getProductId());
            item.setSkuId(productSku.getId());
            item.setBatchId(batchItem.getId());
            item.setTripId(tripInfoDto.getId());
            orderItemMapper.save(item);
        }
        //TODO 将订单保存到redis中
        redisTemplate.opsForValue().set(RedisConstantKey.ORDER_INFO_KEY + orderInfo.getId(), String.valueOf(orderInfo.getId()), 1 * 60 * 1000, TimeUnit.MILLISECONDS);

        //记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);
        // TODO 远程调用service-cart微服务接口清空购物车数据
//        cartFeignClient.deleteChecked();
        return orderInfo.getId();
    }

    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return orderInfoMapper.getById(orderId);
    }

    //业务接口实现
    @Override
    public TradeVo buy(Long skuId) {
        // 查询商品
//        ProductSku productSku = productFeignClient.getBySkuId(skuId);
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
//        orderItem.setSkuId(skuId);
//        orderItem.setSkuName(productSku.getSkuName());
//        orderItem.setSkuNum(1);
//        orderItem.setSkuPrice(productSku.getSalePrice());
//        orderItem.setThumbImg(productSku.getThumbImg());
//        orderItemList.add(orderItem);

        //TODO  计算总金额
//        BigDecimal totalAmount = productSku.getSalePrice();
        TradeVo tradeVo = new TradeVo();
//        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);

        // 返回
        return tradeVo;
    }

    /**
     * { orderState: 0, title: '全部', isRender: false },
     * { orderState: 1, title: '待付款', isRender: false },
     * { orderState: 2, title: '已付款', isRender: false },
     * { orderState: 3, title: '待评价', isRender: false },
     * { orderState: 4, title: '退款', isRender: false },
     *
     * @param page
     * @param limit
     * @param orderStatus
     * @return
     */
    @Override
    public PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, String orderStatus) {
        PageHelper.startPage(page, limit);
        Long userId = AuthContextUtil.getUserInfo().getId();
        String orderState = orderStatus.equals("-1") ? null : orderStatus;
        System.out.println("orderState==" + orderState);
        List<OrderInfo> orderInfoList = orderInfoMapper.findUserPage(userId, orderState);

        orderInfoList.forEach(orderInfo -> {
            computeSliderUrl(orderInfo);
            List<OrderItem> orderItem = orderItemMapper.findByOrderId(orderInfo.getId());
            orderItem.forEach(item -> {
                String tripCardNo = item.getTripCardNo();
                if (tripCardNo != null && !tripCardNo.isEmpty()) {
                    String res = tripCardNo.replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1****$2");
                    System.out.println("tripCardNo==" + res);
                    item.setTripCardNo(res);
                }
            });
            orderInfo.setOrderItemList(orderItem);
        });

        return new PageInfo<>(orderInfoList);
    }

    @Override
    public OrderInfo getOrderInfoByOrderNo(String orderNo) {
        OrderInfo orderInfo = orderInfoMapper.getByOrderNo(orderNo);
        List<OrderItem> orderItemList = orderItemMapper.findByOrderId(orderInfo.getId());
        orderInfo.setOrderItemList(orderItemList);
        return orderInfo;
    }

    /**
     * 用于支付完成后，第三方支付回调更新订单状态
     *
     * @param orderNo
     * @param orderStatus
     */
    @Override
    public void updateOrderStatus(String orderNo, Integer orderStatus) {
        // 更新订单状态
        OrderInfo orderInfo = orderInfoMapper.getByOrderNo(orderNo);
        orderInfo.setOrderStatus(orderStatus);
        orderInfo.setPayType(1);
        orderInfo.setPaymentTime(new Date());
        orderInfoMapper.updateById(orderInfo);

        // 记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(1);
        orderLog.setNote("支付宝支付成功");
        orderLogMapper.save(orderLog);
    }


    @Override
    public OrderInfo orderDetail(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.getById(orderId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startWeek = DateUtils.getCurrentWeek(sdf.format(orderInfo.getAssembleDate()));
        orderInfo.setAssembleWeek(startWeek);
        long time = orderInfo.getCreateTime().getTime();
        long now = new Date().getTime();
        //计算订单创建时间到现在是否超过30分钟
        if (now - time > 1800000) {
            orderInfo.setOrderStatus(OrderStateEnum.PayTimeOut.getCode());
        }
        long timeDiff = (now - time) / 1000;
        //30分钟
        long timeLeft = 1800 - timeDiff;
        if (timeLeft < 0) {
            orderInfo.setOrderStatus(OrderStateEnum.PayTimeOut.getCode());
        }
        orderInfo.setCountdown(timeLeft > 0 ? timeLeft : 0);

        computeSliderUrl(orderInfo);
        List<OrderItem> orderItemList = orderItemMapper.findByOrderId(orderInfo.getId());
        orderItemList.forEach(v -> {
            String tripCardNo = v.getTripCardNo();
            if (tripCardNo != null && !tripCardNo.isEmpty()) {
                String res = tripCardNo.replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1********$2");
                System.out.println("tripCardNo==" + res);
                v.setTripCardNo(res);
            }
        });
        orderInfo.setOrderItemList(orderItemList);
        return orderInfo;
    }

    private static void computeSliderUrl(OrderInfo orderInfo) {
        String sliderUrls = orderInfo.getSliderUrls();
        if (sliderUrls.contains(",")) {
            String[] urls = sliderUrls.split(",");
            orderInfo.setSliderUrls(urls[0]);
        }
    }

    @Transactional
    @Override
    public void cancelTimeOutOrder() {
        // 查询超时订单
        List<OrderInfo> waitPayOrder = orderInfoMapper.findWaitPayOrder();
        for (OrderInfo orderInfo : waitPayOrder) {
            this.updateOrderInfo(orderInfo.getId(), OrderStateEnum.PayTimeOut);
        }

    }

    @Override
    public OrderInfo cancelOrder(Long orderId, String cancelReason) {

        this.updateOrderStatus(orderId, OrderStateEnum.UserCancel, cancelReason);
        return this.orderDetail(orderId);
    }

    /**
     * 用于订单超时未支付，自动取消订单
     *
     * @param orderId
     * @param orderStatus
     */
    @Transactional
    @Override
    public void updateOrderInfo(Long orderId, OrderStateEnum orderStatus) {

        this.updateOrderStatus(orderId, orderStatus, "");
        // 记录日志
        if (orderStatus == OrderStateEnum.PayTimeOut || orderStatus == OrderStateEnum.UserCancel || orderStatus == OrderStateEnum.PayFailed) {
            OrderInfo orderInfo = orderInfoMapper.getById(orderId);
            int saleNum = orderInfo.getAdultNum() + orderInfo.getChildNum();
            //TODO 将库存回滚
            batchInfoMapper.updateSaleNum(orderInfo.getBatchId(), -saleNum);

        }
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderId);
        orderLog.setProcessStatus(orderStatus.getCode());
        orderLog.setNote(orderStatus.getMessage());
        orderLogMapper.save(orderLog);
        //TODO 发送超时未支付通知
    }

    @Autowired
    private OrderStatusHelper orderStatusHelper;

    private void updateOrderStatus(Long orderId, OrderStateEnum orderStatus, String note) {
        //如果note不为空，则使用note信息备注
        int orderStatusCode = orderInfoMapper.findOrderStatusById(orderId);
        orderStatusHelper.isValidNextStatus(orderStatusCode, orderStatus.getCode());
        orderInfoMapper.updateOrderStatus(orderId, orderStatus.getCode(), StrUtil.isEmpty(note) ? orderStatus.getMessage() : note);

    }

    @Override
    public void mockPay(Long id) {
        //检查订单状态是否是待支付，如果不是则报错
        OrderInfo orderInfo = orderInfoMapper.getById(id);
        Integer orderStatus = orderInfo.getOrderStatus();
        orderStatusHelper.isValidNextStatus(orderStatus, OrderStateEnum.PaySuccess.getCode());
        orderInfoMapper.mockPaySuccess(id, OrderStateEnum.PaySuccess.getCode());
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(id);
        orderLog.setProcessStatus(OrderStateEnum.PaySuccess.getCode());
        orderLog.setNote("模拟支付成功");
        orderLogMapper.save(orderLog);
    }
}
