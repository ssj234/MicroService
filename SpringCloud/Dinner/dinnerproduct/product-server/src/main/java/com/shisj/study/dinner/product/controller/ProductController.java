package com.shisj.study.dinner.product.controller;


import com.shisj.study.dinner.order.dto.OrderCreatedDTO;
import com.shisj.study.dinner.order.dto.OrderSendDTO;
import com.shisj.study.dinner.product.VO.ProductInfoVO;
import com.shisj.study.dinner.product.VO.ResultVO;
import com.shisj.study.dinner.product.dataobject.ProductInfo;
import com.shisj.study.dinner.product.dto.DecreaseProductInput;
import com.shisj.study.dinner.product.dto.ProductInfoOutput;
import com.shisj.study.dinner.product.enums.ProductStatusEnum;
import com.shisj.study.dinner.product.enums.ResultEnum;
import com.shisj.study.dinner.product.exception.ProductException;
import com.shisj.study.dinner.product.message.StreamClient;
import com.shisj.study.dinner.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@EnableBinding(StreamClient.class)
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public ResultVO list(){
        List<ProductInfoVO> productInfoVOList = new ArrayList<>();
        List<ProductInfo> productInfoList =  productService.findAllProductByStatus(ProductStatusEnum.IN_SELL.getCode());
        for(ProductInfo productInfo:productInfoList){
            ProductInfoVO productInfoVO = new ProductInfoVO();
            BeanUtils.copyProperties(productInfo,productInfoVO);
            productInfoVOList.add(productInfoVO);
        }
        return ResultVO.success(productInfoVOList);
    }

    @PostMapping("/info4Order")
    public List<ProductInfoOutput> info(@RequestBody() List<String> productIdList){

        List<ProductInfoOutput> ProductInfoOutputList = new ArrayList<>();
        // 查询
        List<ProductInfo> productInfoList = productService.findAllProductByStatus(productIdList);
        for(ProductInfo productInfo:productInfoList){
            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
            BeanUtils.copyProperties(productInfo,productInfoOutput);
            ProductInfoOutputList.add(productInfoOutput);
        }
        // 包装后返回
        return ProductInfoOutputList;
    }

    @Transactional
    public String decrease(List<DecreaseProductInput> decreaseProductInputList){
        List<String> idList = decreaseProductInputList.stream().map(DecreaseProductInput::getProductId).collect(Collectors.toList());
        // 查询
        List<ProductInfo> productInfoList = productService.findAllProductByStatus(idList);
        Map<String,ProductInfo> productInfoMap = productInfoList.stream().collect(Collectors.toMap(ProductInfo::getId, productInfo -> productInfo));

        for(DecreaseProductInput decreaseProductInput : decreaseProductInputList){ // 要减去库存的产品
            String productId = decreaseProductInput.getProductId();
            ProductInfo productInfo = productInfoMap.get(productId); // db info
            if(productInfo == null){
                throw  new ProductException(ResultEnum.PRODUCT_ID_NOTEXIST);
            }
            if(productInfo.getStatus() == ProductStatusEnum.OUT_SELL.getCode()){
                throw  new ProductException(ResultEnum.PRODUCT_OUT_SELL);
            }
            if(productInfo.getCount() - decreaseProductInput.getProductQuantity() <0){
                throw  new ProductException(ResultEnum.PRODUCT_NOT_ENOUGHT);
            }

            productInfo.setCount(decreaseProductInput.getProductQuantity());
            productService.decreaseCount(productInfo);
        }

        return "ok";
    }



    @Transactional
    public void decreaseProduct(List<OrderSendDTO> orderSendDTOList){
        List<String> idList = orderSendDTOList.stream().map(OrderSendDTO::getId).collect(Collectors.toList());
        // 查询
        List<ProductInfo> productInfoList = productService.findAllProductByStatus(idList);
        Map<String,ProductInfo> productInfoMap = productInfoList.stream().collect(Collectors.toMap(ProductInfo::getId, productInfo -> productInfo));

        for(OrderSendDTO orderSendDTO : orderSendDTOList){ // 要减去库存的产品
            String productId = orderSendDTO.getId();
            ProductInfo productInfo = productInfoMap.get(productId); // db info
            if(productInfo == null){
                throw  new ProductException(ResultEnum.PRODUCT_ID_NOTEXIST);
            }
            if(productInfo.getStatus() == ProductStatusEnum.OUT_SELL.getCode()){
                throw  new ProductException(ResultEnum.PRODUCT_OUT_SELL);
            }
            if(productInfo.getCount() - orderSendDTO.getCount() <0){
                throw  new ProductException(ResultEnum.PRODUCT_NOT_ENOUGHT);
            }

            productInfo.setCount(orderSendDTO.getCount());
            productService.decreaseCount(productInfo);
        }

    }


    /**
     * 收到了mq消息，减少库存
     * @param orderCreatedDTO
     */
    @StreamListener(StreamClient.orderCreated)
    @SendTo(StreamClient.decreasedProduct)
    public String process(OrderCreatedDTO orderCreatedDTO){
        // 数据库减少库存
        this.decreaseProduct(orderCreatedDTO.getOrderSendDTOList());
        return orderCreatedDTO.getOrderId();
    }

}
