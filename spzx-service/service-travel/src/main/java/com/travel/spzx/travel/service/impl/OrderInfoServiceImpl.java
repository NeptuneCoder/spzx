package com.travel.spzx.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.common.exception.GuiguException;

import com.travel.spzx.model.dto.h5.OrderInfoDto;
import com.travel.spzx.model.entity.h5.CartInfo;
import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.entity.order.OrderItem;
import com.travel.spzx.model.entity.order.OrderLog;
import com.travel.spzx.model.entity.product.ProductSku;
import com.travel.spzx.model.entity.user.UserAddress;
import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.travel.spzx.model.vo.h5.TradeVo;

import com.travel.spzx.travel.mapper.OrderInfoMapper;
import com.travel.spzx.travel.mapper.OrderItemMapper;
import com.travel.spzx.travel.mapper.OrderLogMapper;
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
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        // 数据校验
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        if (CollectionUtils.isEmpty(orderItemList)) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }

        for (OrderItem orderItem : orderItemList) {
            //TODO 校验商品是否存在
//            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId());
//            if (null == productSku) {
//                throw new GuiguException(ResultCodeEnum.DATA_ERROR);
//            }
            System.out.println(orderItem.toString());
            //todo 校验库存
//            if (orderItem.getSkuNum().intValue() > productSku.getStockNum().intValue()) {
//                throw new GuiguException(ResultCodeEnum.STOCK_LESS);
//            }
        }

        // 构建订单数据，保存订单
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        OrderInfo orderInfo = new OrderInfo();
        //订单编号
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        //用户id
        orderInfo.setUserId(userInfo.getId());
        //用户昵称
        orderInfo.setNickName(userInfo.getNickName());
        //TODO 用户收货地址信息
//        UserAddress userAddress = userFeignClient.getUserAddress(orderInfoDto.getUserAddressId());
//        orderInfo.setReceiverName(userAddress.getName());
//        orderInfo.setReceiverPhone(userAddress.getPhone());
//        orderInfo.setReceiverTagName(userAddress.getTagName());
//        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
//        orderInfo.setReceiverCity(userAddress.getCityCode());
//        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
//        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        //订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);
        orderInfoMapper.save(orderInfo);

        //保存订单明细
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.save(orderItem);
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
