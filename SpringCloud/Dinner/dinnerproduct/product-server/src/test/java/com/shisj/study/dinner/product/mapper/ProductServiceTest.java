package com.shisj.study.dinner.product.mapper;


import com.shisj.study.dinner.product.dataobject.ProductInfo;
import com.shisj.study.dinner.product.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    public void findOne() throws Exception {

        List<ProductInfo> productList = productService.findAllProductByStatus(0);
        Assert.assertTrue(((List) productList).size() > 0);
    }
}
