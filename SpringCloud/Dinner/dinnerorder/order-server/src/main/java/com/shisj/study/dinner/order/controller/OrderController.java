package com.shisj.study.dinner.order.controller;


import com.shisj.study.dinner.order.VO.ResultVO;
import com.shisj.study.dinner.order.dataobject.OrderDetail;
import com.shisj.study.dinner.order.dataobject.OrderMaster;

import com.shisj.study.dinner.order.dto.OrderCreatedDTO;

import com.shisj.study.dinner.order.dto.OrderSendDTO;
import com.shisj.study.dinner.order.enums.OrderStatusEnum;
import com.shisj.study.dinner.order.enums.ResultEnum;
import com.shisj.study.dinner.order.exception.OrderException;
import com.shisj.study.dinner.order.form.OrderCreateForm;

import com.shisj.study.dinner.order.message.OrderStreamNewClient;
import com.shisj.study.dinner.order.service.OrderService;
import com.shisj.study.dinner.order.util.KeyGenerator;
import com.shisj.study.dinner.product.client.ProductClient;
import com.shisj.study.dinner.product.dto.DecreaseProductInput;
import com.shisj.study.dinner.product.dto.ProductInfoOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@EnableBinding(OrderStreamNewClient.class)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderStreamNewClient orderStreamClient;

    @PostMapping("/save")
    @Transactional
    public ResultVO create(@RequestBody List<OrderCreateForm> orderCreateFormList){

        // 1. 获取产品信息，检查是否存在
        List<String> idList = orderCreateFormList.stream().map(OrderCreateForm::getProductId).collect(Collectors.toList());
        List<ProductInfoOutput>  productInfoOutputList = productClient.info(idList);

        // 2.
        Map<String,OrderCreateForm> productInfoMap = orderCreateFormList.stream().collect(Collectors.toMap(OrderCreateForm::getProductId, orderCreateForm -> orderCreateForm));

        BigDecimal sumAmount = new BigDecimal("0");

        String orderId = KeyGenerator.gen();
        List<DecreaseProductInput> decreaseProductInputList = new ArrayList<>();
        for(ProductInfoOutput productInfoOutput : productInfoOutputList){
            OrderCreateForm orderCreateForm = productInfoMap.get(productInfoOutput.getId());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(productInfoOutput.getId());
            orderDetail.setProductPrice(productInfoOutput.getPrice());
            orderDetail.setProductName(productInfoOutput.getName());
            orderDetail.setProductIcon(productInfoOutput.getIcon());
            orderDetail.setCount(orderCreateForm.getCount());
            orderService.saveOrderDetail(orderDetail);

            sumAmount = sumAmount.add(
                new BigDecimal(orderCreateForm.getCount()).multiply(productInfoOutput.getPrice())
            );

            DecreaseProductInput decreaseProductInput = new DecreaseProductInput();
            decreaseProductInput.setProductId(productInfoOutput.getId());
            decreaseProductInput.setProductQuantity(orderCreateForm.getCount());
            decreaseProductInputList.add(decreaseProductInput);
        }
        // 2. 创建订单
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setStatus(OrderStatusEnum.STATUS_INIT.getCode());
        orderMaster.setOrderId(orderId);
        orderMaster.setPriceSum(sumAmount);
        orderService.saveOrderMaster(orderMaster);

        // 3. 减少库存
        productClient.decrease(decreaseProductInputList);

        return ResultVO.success("ok");
    }


    @PostMapping("/saveNew")
    public ResultVO createUseMQ(@RequestBody List<OrderCreateForm> orderCreateFormList){

        // 1. 获取产品信息
        List<String> idList = orderCreateFormList.stream().map(OrderCreateForm::getProductId).collect(
                Collectors.toList());
        List<ProductInfoOutput>  productInfoOutputList = productClient.info(idList);
        Map<String,ProductInfoOutput> productInfoMap = productInfoOutputList.stream().collect(
                Collectors.toMap(ProductInfoOutput::getId, productInfoOutput -> productInfoOutput));
        String orderId = KeyGenerator.gen();
        BigDecimal sumAmount = new BigDecimal("0");
        List<OrderSendDTO> orderSendDTOList = new ArrayList<>();
        for(OrderCreateForm orderCreateForm : orderCreateFormList){
            ProductInfoOutput productInfoOutput = productInfoMap.get(orderCreateForm.getProductId());
            if(productInfoOutput == null){
                throw  new OrderException(ResultEnum.PRODUCT_ID_NOTEXIST);
            }
            if(productInfoOutput.getCount() - orderCreateForm.getCount() < 0){
                throw  new OrderException(ResultEnum.PRODUCT_NOT_ENOUGHT);
            }

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(productInfoOutput.getId());
            orderDetail.setProductPrice(productInfoOutput.getPrice());
            orderDetail.setProductName(productInfoOutput.getName());
            orderDetail.setProductIcon(productInfoOutput.getIcon());
            orderDetail.setCount(orderCreateForm.getCount());
            orderService.saveOrderDetail(orderDetail);

            sumAmount = sumAmount.add(
                    new BigDecimal(orderCreateForm.getCount()).multiply(productInfoOutput.getPrice())
            );

            OrderSendDTO orderSendDTO = new OrderSendDTO();
            orderSendDTO.setId(productInfoOutput.getId());
            orderSendDTO.setCount(orderCreateForm.getCount());
            orderSendDTOList.add(orderSendDTO);
        }
        // 2. 创建订单
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setStatus(OrderStatusEnum.STATUS_INIT.getCode());
        orderMaster.setOrderId(orderId);
        orderMaster.setPriceSum(sumAmount);
        orderService.saveOrderMaster(orderMaster);

        // 2. 向MQ发送消息，减少库存
        OrderCreatedDTO orderDecreaseDTO = new OrderCreatedDTO();
        orderDecreaseDTO.setOrderId(orderId);
        orderDecreaseDTO.setOrderSendDTOList(orderSendDTOList);
        orderStreamClient.output().send(MessageBuilder.withPayload(orderDecreaseDTO).build());

        return ResultVO.success("ok");
    }

    @StreamListener(OrderStreamNewClient.decreasedProduct)
    public void process(String orderId){
        // 根据反馈确认或取消订单
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderId);
        orderMaster.setStatus(OrderStatusEnum.STATUS_CONFIRM.getCode());
        orderService.updateOrderMaster(orderMaster);
    }
}
