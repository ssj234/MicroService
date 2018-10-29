package com.shisj.study.zuul.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZuulProductController {

    @GetMapping("/add")
    public String add(){
        return "add";
    }

    @GetMapping("/baned")
    public String baned(){
        return "baned";
    }
}
