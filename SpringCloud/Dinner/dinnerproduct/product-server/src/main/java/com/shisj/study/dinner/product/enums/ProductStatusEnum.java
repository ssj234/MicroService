package com.shisj.study.dinner.product.enums;

public enum ProductStatusEnum {

    OUT_SELL(0,"下架"),IN_SELL(1,"上架");;

    private int code;
    private String msg;

    ProductStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
