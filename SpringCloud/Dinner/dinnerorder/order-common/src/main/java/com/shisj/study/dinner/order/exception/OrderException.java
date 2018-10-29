package com.shisj.study.dinner.order.exception;

import com.shisj.study.dinner.order.enums.ResultEnum;

public class OrderException extends RuntimeException {

    private Integer code;
    public OrderException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
