package com.shisj.study.dinner.product;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients()
@MapperScan("com.shisj.study.dinner.product.mapper")
public class ProductServerApplication {

    public  static void main(String args[]){
        SpringApplication.run(ProductServerApplication.class,args);
    }
}
