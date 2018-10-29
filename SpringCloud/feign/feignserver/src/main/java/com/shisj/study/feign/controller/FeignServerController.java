package com.shisj.study.feign.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class FeignServerController {

    @GetMapping("/hello")
    public String hello(){
        System.out.println("received message!");
        return "Hello,Fiegn!";
    }
}
