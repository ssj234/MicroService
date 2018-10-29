package com.shisj.study.dinner.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderStreamNewClient {
    String orderCreated = "orderCreated";
    String decreasedProduct = "decreasedProduct";

    @Output(OrderStreamNewClient.orderCreated)
    MessageChannel output();

    @Input(OrderStreamNewClient.decreasedProduct)
    SubscribableChannel input();
}
