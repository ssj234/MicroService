package com.shisj.study.dinner.order.VO;

import com.shisj.study.dinner.order.enums.ResultEnum;

public class ResultVO<T> {
    private int code;
    private String msg;
    private T data;

    public static ResultVO success(Object data) {
        ResultVO result = new ResultVO();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}