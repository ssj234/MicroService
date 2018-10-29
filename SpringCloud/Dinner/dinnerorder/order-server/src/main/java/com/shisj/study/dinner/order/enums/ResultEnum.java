package com.shisj.study.dinner.product.enums;

public enum  ResultEnum {

    SUCCESS(0,"成功"),
    PRODUCT_ID_NOTEXIST(1,"产品不存在"),
    PRODUCT_OUT_SELL(2,"产品已下架"),
    PRODUCT_NOT_ENOUGHT(3,"库存不足");

    private int code;
    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
