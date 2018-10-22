package com.shisj.study.springboot.hello.aspect;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
public class HttpAspect {

    private final  static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.shisj.study.springboot.hello.controller.AspectController.*(..))")
    public void log(){
    }

    @Before("log())")
    public void log1(JoinPoint joinpoint){
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        logger.info("insert record");
        if(attrs != null){
            HttpServletRequest request = attrs.getRequest();
            logger.info("url={}",request.getRequestURL());
            logger.info("method={}",request.getMethod());
            logger.info("ip={}",request.getRemoteAddr());
        }
        // 获取类和方法
        logger.info("class_method={}",joinpoint.getSignature().getDeclaringTypeName()+"."+joinpoint.getSignature().getName());
        // 获取参数
        logger.info("args={}",joinpoint.getArgs());
    }

    @After("log())")
    public void log2(){
        logger.info("update record");
    }

    @AfterReturning(pointcut = "log()",returning = "object")
    public void doAfterReturning(Object object){
        logger.info("response={}",object);
    }
}
