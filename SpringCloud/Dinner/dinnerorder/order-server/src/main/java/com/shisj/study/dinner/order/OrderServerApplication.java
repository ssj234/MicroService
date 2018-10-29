package com.shisj.study.dinner.order;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.shisj.study.dinner.order.mapper")
@EnableFeignClients(basePackages = {"com.shisj.study.dinner.product.client"})
public class OrderServerApplication {
    public  static void main(String args[]){
        SpringApplication.run(OrderServerApplication.class,args);
    }
}
