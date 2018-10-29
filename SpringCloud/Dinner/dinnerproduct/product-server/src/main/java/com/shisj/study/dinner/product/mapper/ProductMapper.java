package com.shisj.study.dinner.product.mapper;

import com.shisj.study.dinner.product.dataobject.ProductInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ProductMapper {
    /**
     * 查询所有的指定状态的产品
     * @return
     */
    public List<ProductInfo> findAllProductByStatus(int status);

    /**
     * 查询指定id的产品
     * @param map
     * @return
     */
    public List<ProductInfo> findAllProductByIdList(Map map);

    /**
     * 减少库存
     * @param productInfo
     */
    public  void decreaseCount(ProductInfo productInfo);
}
