package com.shisj.study.dinner.product.service.impl;

import com.shisj.study.dinner.product.dataobject.ProductInfo;
import com.shisj.study.dinner.product.mapper.ProductMapper;
import com.shisj.study.dinner.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductInfo> findAllProductByStatus(int status) {
        return productMapper.findAllProductByStatus(status);
    }

    @Override
    public List<ProductInfo> findAllProductByStatus(List<String> idList) {
        Map map = new HashMap<>();
        map.put("idList",idList);
        List<ProductInfo> productInfoList = productMapper.findAllProductByIdList(map);
        return productInfoList;
    }

    @Override
    public void decreaseCount(ProductInfo productInfo) {
        productMapper.decreaseCount(productInfo);
    }
}
