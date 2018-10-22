package com.shisj.study.springboot.hello.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 当网页端一个请求发送到后台时，后台的控制视图层通过@RequestMapping映射相应的视图方法，
     * 如果在视图方法上用注解@ResponseBody标识后，
     * 方法执行完后返回的内容会返回到请求页面的body上，直接显示在网页上。
     * @param req
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map jsonErrorHandler(Exception exception){
        Map ret = new HashMap();
        ret.put("msg",exception.getMessage());
        ret.put("code","99999");
        return  ret;
    }
}
