package com.shisj.study.dinner.product.dto;

import java.util.List;

public class DecreaseProductDto {

    String orderId;
    List<DecreaseProductInput> decreaseProductInputList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<DecreaseProductInput> getDecreaseProductInputList() {
        return decreaseProductInputList;
    }

    public void setDecreaseProductInputList(List<DecreaseProductInput> decreaseProductInputList) {
        this.decreaseProductInputList = decreaseProductInputList;
    }
}
