package com.shisj.study.dinner.order.enums;

public enum OrderStatusEnum {

    STATUS_INIT(99,"初始化"),
    STATUS_CONFIRM(88,"已确认"),
    STATUS_CANCEL(1,"已取消"),
    STATUS_DONE(0,"完成"),;
    private int code;
    private String message;

    OrderStatusEnum(int code, String message) {
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
