package com.shisj.study.springboot.hello;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//@MapperScan(basePackages = {"com.shisj.study.springboot.mybatis.mapper"})
@MapperScan("com.shisj.study.springboot.hello.mybatis.mapper")
public class App {

    public  static void main(String args[]){
        SpringApplication.run(App.class,args);
    }
}
