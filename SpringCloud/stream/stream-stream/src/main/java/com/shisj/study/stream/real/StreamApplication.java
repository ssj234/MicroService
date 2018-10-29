package com.shisj.study.stream.real;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
public class StreamApplication {
    public static void main(String args[]){
        SpringApplication.run(StreamApplication.class,args);
    }
}
