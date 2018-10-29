package com.shisj.study.dinner.product.service;

import com.shisj.study.dinner.product.dataobject.ProductInfo;
import com.shisj.study.dinner.product.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    public List<ProductInfo> findAllProductByStatus(int status);

    public List<ProductInfo> findAllProductByStatus(List<String> idList);

    public void decreaseCount(ProductInfo productInfo);
}
