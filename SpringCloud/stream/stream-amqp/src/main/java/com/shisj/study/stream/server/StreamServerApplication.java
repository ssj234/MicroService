package com.shisj.study.stream.server;


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class StreamServerApplication {
    public static void main(String args[]){
        SpringApplication.run(StreamServerApplication.class,args);
    }
}