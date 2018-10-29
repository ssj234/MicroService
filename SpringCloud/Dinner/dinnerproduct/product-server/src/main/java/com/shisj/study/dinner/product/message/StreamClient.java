package com.shisj.study.dinner.product.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 发送： 已经减少库存
 * 接受： 订单已经创建，需要减少库存
 */
public interface StreamClient {
    String orderCreated = "orderCreated";
    String decreasedProduct = "decreasedProduct";

    @Output(StreamClient.decreasedProduct)
    MessageChannel output();

    @Input(StreamClient.orderCreated)
    SubscribableChannel input();
}
