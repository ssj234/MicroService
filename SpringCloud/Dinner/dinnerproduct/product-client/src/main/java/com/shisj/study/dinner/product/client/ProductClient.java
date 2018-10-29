package com.shisj.study.dinner.product.client;

import com.shisj.study.dinner.product.dto.DecreaseProductInput;
import com.shisj.study.dinner.product.dto.ProductInfoOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("PRODUCT")
public interface ProductClient {

    // 暴露接口1： 获取产品信息
    @PostMapping("/product/info4Order")
    List<ProductInfoOutput> info(@RequestBody() List<String> productIdList);

    // 暴露接口1： 减少库存
    @PostMapping("/product/decrease4Order")
    public String decrease(@RequestBody List<DecreaseProductInput> decreaseProductInputList);

}
