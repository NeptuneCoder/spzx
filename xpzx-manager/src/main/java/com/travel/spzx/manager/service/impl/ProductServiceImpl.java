package com.travel.spzx.manager.service.impl;

import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.manager.mapper.BatchInfoMapper;
import com.travel.spzx.manager.mapper.ProductDetailsMapper;
import com.travel.spzx.manager.mapper.ProductMapper;
import com.travel.spzx.manager.mapper.ProductSkuMapper;
import com.travel.spzx.manager.service.ProductService;
import com.travel.spzx.model.dto.product.ProductDto;
import com.travel.spzx.model.entity.product.BatchItem;
import com.travel.spzx.model.entity.product.Product;
import com.travel.spzx.model.entity.product.ProductDetails;
import com.travel.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;


    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private BatchInfoMapper batchInfoMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;


    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page, limit);
        PageInfo<Product> pageInfo = new PageInfo<>(productMapper.findByPage(productDto));
        return pageInfo;
    }

    @Transactional
    @Override
    public void save(Product product) {
        // 检查参数是否合规
        if (product.getName() == null || product.getName().trim().length() == 0) {
            throw GuiguException.build("商品名称不能为空");
        }


        if (product.getDetailsImageUrls() == null || product.getDetailsImageUrls().trim().length() == 0) {
            throw GuiguException.build("商品详情图片不能为空");
        }
        //出发时间，出发地点，集合时间不能为空
        if (product.getDepartureTime() == null) {
            throw GuiguException.build("出发时间不能为空");
        }

        if (product.getAssembleTime() == null) {
            throw GuiguException.build("集合时间不能为空");
        }
        // 保存商品数据
        product.setStatus(0);              // 设置上架状态为0
        product.setAuditStatus(0);         // 设置审核状态为0
        int productId = productMapper.save(product);
        System.out.println("productId=" + productId);
        List<BatchItem> batchInfo = product.getBatchInfo();
        batchInfo.forEach(v -> {
            System.out.println("批次信息:" + v);
            v.setProductId(product.getId());
            //处理一下开始时间爱你和结束时间
            batchStartTimeSub8(v);

            batchInfoMapper.save(v);
            System.out.println("插入批次后返回id：" + v.getId());
        });

        // 保存商品sku数据
        List<ProductSku> productSkuList = product.getProductSkuList();
        if (productSkuList == null || productSkuList.size() == 0) {
            throw GuiguException.build("商品sku不能为空");
        }
        for (int i = 0, size = productSkuList.size(); i < size; i++) {
            // 获取ProductSku对象
            ProductSku productSku = productSkuList.get(i);
            productSku.setSkuCode(product.getId() + "_" + i);       // 构建skuCode

            productSku.setProductId(product.getId());               // 设置商品id
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            productSku.setSaleNum(0);                               // 设置销量
            productSku.setStatus(0);

            Integer stockNum = productSku.getStockNum();// 设置排序
            productSkuMapper.save(productSku);                    // 保存数据

        }

        // 保存商品详情数据
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(productDetails);

    }

    @Override
    public Product getById(Long id) {

        // 根据id查询商品数据
        Product product = productMapper.selectById(id);

        // 根据商品的id查询sku数据
        List<ProductSku> productSkuList = productSkuMapper.selectByProductId(id);
        product.setProductSkuList(productSkuList);
        List<BatchItem> batchItems = batchInfoMapper.selectByProductId(id);
        System.out.println("productId:" + id);
        System.out.println("批次结果batchItems:" + batchItems.toString());
        product.setBatchInfo(batchItems);
        // 根据商品的id查询商品详情数据
        ProductDetails productDetails = productDetailsMapper.selectByProductId(product.getId());
        product.setDetailsImageUrls(productDetails.getImageUrls());

        // 返回数据
        return product;
    }

    @Transactional
    @Override
    public void updateById(Product product) {
        // 更新商品数据
        // 修改商品基本数据
        productMapper.updateById(product);

        // 修改商品的sku数据
        List<ProductSku> productSkuList = product.getProductSkuList();
        productSkuList.forEach(productSku -> {
            productSkuMapper.updateById(productSku);
        });

        product.getBatchInfo()
                .forEach(v -> {
                    System.out.println("批次信息:" + v);
                    if (v.getId() == null) {
                        v.setProductId(product.getId());
                        batchStartTimeSub8(v);
                        batchInfoMapper.save(v);
                    } else {
                        batchInfoMapper.updateById(v);
                    }

                });
        // 修改商品的详情数据
        ProductDetails productDetails = productDetailsMapper.selectByProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.updateById(productDetails);
    }

    private static void batchStartTimeSub8(BatchItem v) {
        Date startTime = v.getStartTime();
        Long startTimeLong = startTime.getTime() - 7 * 60 * 60 * 1000;
        Date startTimeNew = new Date(startTimeLong);
        v.setStartTime(startTimeNew);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        productMapper.deleteById(id);                   // 根据id删除商品基本数据
        productSkuMapper.deleteByProductId(id);         // 根据商品id删除商品的sku数据
        productDetailsMapper.deleteByProductId(id);     // 根据商品的id删除商品的详情数据
    }

    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
        //auditStatus == 1 审核通过，auditStatus == -1 审核不通过
        //auditStatus == 1 审核通过，auditStatus == -1 审核不通过
        if (auditStatus == 1) {
            //检查参数是否合规
            Product resProduct = productMapper.selectById(id);
            //出发点点
            if (resProduct == null) {
                throw GuiguException.build("商品不存在");
            }
            //查询批次信息
            List<BatchItem> batchItems = batchInfoMapper.selectByProductId(id);
            if (batchItems == null || batchItems.size() == 0) {
                throw GuiguException.build("批次信息不能为空");
            }
//            if (resProduct.getDetailsImageUrls() == null || resProduct.getDetailsImageUrls().split(",").length == 0) {
//                throw GuiguException.build("商品详情图片不能为空");
//            }
            if (resProduct.getPlaceToStartId() == null || resProduct.getPlaceToStartId() == 0) {
                throw GuiguException.build("出发点不能为空");
            }
            //批次信息是否完整
            //商品详情图片是否完整
            //商品是否存在未完成的批次
        }


        product.setId(id);
        if (auditStatus == 1) {
            product.setAuditStatus(1);
            product.setAuditMessage("审批通过");
        } else {
            product.setAuditStatus(-1);
            product.setAuditMessage("审批不通过");
        }
        productMapper.updateById(product);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        if (status == 1) {
            product.setStatus(1);
        } else {
            //TODO 如果是下架还要判断当前商品是否存在未完成的批次
            product.setStatus(-1);
        }
        productMapper.updateById(product);
    }

    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }
}
