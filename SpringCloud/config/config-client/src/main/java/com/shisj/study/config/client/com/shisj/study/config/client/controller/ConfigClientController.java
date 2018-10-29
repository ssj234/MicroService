package com.shisj.study.config.client.com.shisj.study.config.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${helloName}")
    private String  helloName;

    @GetMapping("/hello")
    public String hello(){
        return  "hello," + helloName;
    }
}
