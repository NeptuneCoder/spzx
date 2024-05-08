package com.travel.spzx.travel.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.common.exception.GuiguException;

import com.travel.spzx.model.dto.h5.OrderInfoDto;
import com.travel.spzx.model.dto.trip.TripInfoDto;
import com.travel.spzx.model.entity.h5.CartInfo;
import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.entity.order.OrderItem;
import com.travel.spzx.model.entity.order.OrderLog;
import com.travel.spzx.model.entity.product.BatchItem;
import com.travel.spzx.model.entity.product.Product;
import com.travel.spzx.model.entity.product.ProductSku;
import com.travel.spzx.model.entity.trip.TripInfo;
import com.travel.spzx.model.entity.user.UserAddress;
import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.travel.spzx.model.vo.h5.TradeVo;

import com.travel.spzx.travel.mapper.*;
import com.travel.spzx.travel.service.OrderInfoService;
import com.travel.xpzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    /**
     * //这里的地址需要完整的地址，否者调用不到
     *
     * @GetMapping(value = "/api/order/cart/auth/getAllCkecked")
     * public List<CartInfo> getAllChecked();
     * @GetMapping("/api/order/cart/auth/deleteChecked") void deleteChecked();
     */
//    @Autowired
//    private CartFeignClient cartFeignClient;


    /**
     * public interface ProductFeignClient {
     *
     * @GetMapping("/api/product/getBySkuId/{skuId}") ProductSku getBySkuId(@PathVariable("skuId") Long skuId);
     * //更新批次中销售数量
     * @PostMapping("/api/product/updateSkuSaleNum") Boolean updateSkuSaleNum(@RequestBody List<SkuSaleDto> skuSaleDtoList);
     * }
     */
//    @Autowired
//    private ProductFeignClient productFeignClient;

    /**
     * @GetMapping("/api/user/userAddress/auth/getUserAddress/{id}") UserAddress getUserAddress(@PathVariable("id") Long userAddressId);
     */
//    @Autowired
//    private UserFeignClient userFeignClient;

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
        List<BatchItem> batchItems = batchInfoMapper.selectByProductId(Long.valueOf(orderInfoDto.getProductId()));
        if (batchItems.isEmpty()) {
            throw GuiguException.build("当前批次无库存");
        }
        BatchItem batchItem = batchItems.get(0);
        System.out.println("batchItem == "+JSON.toJSONString(batchItem));
        System.out.println(batchItem.getTotalNum());
        System.out.println(batchItem.getSaleNum());
        System.out.println(orderInfoDto.getAdultNum() );
        System.out.println(orderInfoDto.getChildNum());
        if ((batchItem.getTotalNum() - batchItem.getSaleNum()) < orderInfoDto.getAdultNum() + orderInfoDto.getChildNum()) {
            throw GuiguException.build("剩余库存不足");
        }

        ProductSku productSku = productSkus.get(0);

        //TODO 构建订单项数据
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setProductId(orderInfoDto.getProductId());
        // 构建订单数据，保存订单
        UserInfo userInfo = AuthContextUtil.getUserInfo();


        //用户id
        orderInfo.setUserId(userInfo.getId());
        //用户昵称
        orderInfo.setNickName(userInfo.getNickName());
        orderInfo.setProductId(orderInfoDto.getProductId());
        orderInfo.setProductName(product.getName());
        orderInfo.setSkuId(productSku.getId());
        orderInfo.setSkuName(productSku.getSkuName());
        orderInfo.setBatchId(batchItem.getId());
        orderInfo.setLinkMan(orderInfo.getLinkMan());
        orderInfo.setLinkTel(orderInfo.getLinkTel());
        orderInfo.setAdultNum(orderInfoDto.getAdultNum());
        orderInfo.setChildNum(orderInfoDto.getChildNum());
        orderInfo.setTotalNum(orderInfoDto.getAdultNum() + orderInfoDto.getChildNum());
        orderInfo.setAdultPrice(productSku.getSalePrice());
        orderInfo.setChildPrice(productSku.getChildSalePrice());

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


        orderInfo.setPayType(1);
        orderInfo.setOrderStatus(0);
        orderInfo.setRemark(orderInfoDto.getRemark());

        orderInfoMapper.save(orderInfo);


        //保存订单明细
        for (TripInfoDto tripInfoDto : tripsList) {
            OrderItem item = new OrderItem();
            item.setOrderId(orderInfo.getId());
            item.setBatchId(batchItem.getId());
            item.setProductId(orderInfoDto.getProductId());
            item.setSkuId(productSku.getId());
            item.setSkuName(productSku.getSkuName());
            item.setAdultPrice(productSku.getSalePrice());
            item.setChildPrice(productSku.getChildSalePrice());
            item.setTripId(tripInfoDto.getId());
            orderItemMapper.save(item);
        }

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

    @Override
    public PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, String orderStatus) {
        PageHelper.startPage(page, limit);
        Long userId = AuthContextUtil.getUserInfo().getId();
        List<OrderInfo> orderInfoList = orderInfoMapper.findUserPage(userId, orderStatus);

        orderInfoList.forEach(orderInfo -> {
            List<OrderItem> orderItem = orderItemMapper.findByOrderId(orderInfo.getId());
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

    @Override
    public void updateOrderStatus(String orderNo, Integer orderStatus) {
        // 更新订单状态
        OrderInfo orderInfo = orderInfoMapper.getByOrderNo(orderNo);
        orderInfo.setOrderStatus(1);
        orderInfo.setPayType(orderStatus);
        orderInfo.setPaymentTime(new Date());
        orderInfoMapper.updateById(orderInfo);

        // 记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(1);
        orderLog.setNote("支付宝支付成功");
        orderLogMapper.save(orderLog);
    }
}
