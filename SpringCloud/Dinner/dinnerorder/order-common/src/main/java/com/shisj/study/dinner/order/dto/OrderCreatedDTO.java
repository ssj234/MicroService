package com.shisj.study.dinner.order.dto;

import java.util.List;

/**
 * 创建的订单内容
 */
public class OrderCreatedDTO {

    private String orderId;
    private List<OrderSendDTO> orderSendDTOList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<OrderSendDTO> getOrderSendDTOList() {
        return orderSendDTOList;
    }

    public void setOrderSendDTOList(List<OrderSendDTO> orderSendDTOList) {
        this.orderSendDTOList = orderSendDTOList;
    }
}
