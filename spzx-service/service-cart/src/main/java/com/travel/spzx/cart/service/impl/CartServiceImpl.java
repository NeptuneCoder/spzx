package com.travel.spzx.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.travel.spzx.cart.mapper.ProductSkuMapper;
import com.travel.spzx.cart.service.CartService;
import com.travel.spzx.model.entity.h5.CartInfo;
import com.travel.spzx.model.entity.product.ProductSku;
import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.xpzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //    @Autowired
//    private ProductFeignClient productFeignClient;
    @Autowired
    private ProductSkuMapper productSkuMapper;


    private String getCartKey(Long userId) {
        //定义key user:cart:userId
        return "user:cart:" + userId;
    }

    @Override
    public void addToCart(String skuId, String skuNum) {
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        Long userId = userInfo.getId();
        //TODO 调用商品服务，获取商品信息，判断库存是否充足，如果充足则添加到购物车中
        //如果已经存在则更新数量，不存在则新增一条记录

        String cartKey = getCartKey(userId);

        //获取缓存对象
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        CartInfo cartInfo = null;
        if (cartInfoObj != null) {       //  如果购物车中有该商品，则商品数量 相加！
            cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            cartInfo.setSkuNum(cartInfo.getSkuNum() + Integer.parseInt(skuNum));
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        } else {

            // 当购物车中没用该商品的时候，则直接添加到购物车！
            cartInfo = new CartInfo();

            // 购物车数据是从商品详情得到 {skuInfo}
            ProductSku productSku = productSkuMapper.getById(Long.parseLong(skuId));
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(Integer.parseInt(skuNum));
            cartInfo.setSkuId(Long.parseLong(skuId));
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());

        }

        // 将商品数据存储到购物车中
        redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));
    }

    @Override
    public List<CartInfo> getCartList() {

        // 获取当前登录的用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        // 获取数据
        List<Object> cartInfoList = redisTemplate.opsForHash().values(cartKey);

        if (!CollectionUtils.isEmpty(cartInfoList)) {
            List<CartInfo> infoList = cartInfoList.stream().map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class)).sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime())).collect(Collectors.toList());
            return infoList;
        }

        return new ArrayList<>();
    }

    @Override
    public void deleteCart(String skuId) {
        // 获取当前登录的用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        // 删除数据
        //以下两行代码的区别？
        // redisTemplate.opsForHash()和redisTemplate.delete()的区别？
        // redisTemplate.opsForHash().delete(cartKey, skuId);
        // redisTemplate.delete(cartKey);

        // 两者的区别：
        // opsForHash()是操作hash表的，可以直接删除指定的key-value对；

        // delete()是删除整个hash表，所以需要先获取hash表的所有key，然后逐个删除。

        // 所以，这里应该使用delete()方法，因为hash表的key是skuId，所以直接删除即可。
        redisTemplate.opsForHash().delete(cartKey, skuId);


    }


    @Override
    public void checkCart(String skuId, String isChecked) {
        // 获取当前登录的用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        if (redisTemplate.opsForHash().hasKey(cartKey, skuId)) {
            // 获取数据
            Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, skuId);
            if (cartInfoObj != null) {
                CartInfo cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
                cartInfo.setIsChecked(Integer.parseInt(isChecked));
                redisTemplate.opsForHash().put(cartKey, skuId, JSON.toJSONString(cartInfo));
            }
        }
    }

    @Override
    public void changeCartNum(String skuId, String skuNum) {
        // 获取当前登录的用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        if (redisTemplate.opsForHash().hasKey(cartKey, skuId)) {
            // 获取数据
            Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, skuId);
            if (cartInfoObj != null) {
                CartInfo cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
                cartInfo.setSkuNum(Integer.parseInt(skuNum));
                redisTemplate.opsForHash().put(cartKey, skuId, JSON.toJSONString(cartInfo));
            }
        }
    }

    @Override
    public void checkAllCart(String isChecked) {
        // 获取当前登录的用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        List<Object> cartInfoList = redisTemplate.opsForHash().values(cartKey);
        if (!CollectionUtils.isEmpty(cartInfoList)) {
            cartInfoList.stream().map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class)).forEach(cartInfo -> {
                cartInfo.setIsChecked(Integer.parseInt(isChecked));
                redisTemplate.opsForHash().put(cartKey, String.valueOf(cartInfo.getSkuId()), JSON.toJSONString(cartInfo));
            });
        }
    }

    @Override
    public List<CartInfo> getAllChecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);       // 获取所有的购物项数据
        if (!CollectionUtils.isEmpty(objectList)) {
            List<CartInfo> cartInfoList = objectList.stream().map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class)).filter(cartInfo -> cartInfo.getIsChecked() == 1).collect(Collectors.toList());
            return cartInfoList;
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteChecked() {
        // 获取当前登录的用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        redisTemplate.opsForHash().values(cartKey)
                .stream()
                .map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                .forEach(cartInfo -> redisTemplate.opsForHash().delete(cartKey, String.valueOf(cartInfo.getSkuId())));
    }

    @Override
    public void deleteCartAll() {
        // 获取当前登录的用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);
        redisTemplate.delete(cartKey);
    }

}
