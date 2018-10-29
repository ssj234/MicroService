package com.shisj.study.zuul.gateway.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class ZuulGatewayController {

    private RestTemplate restTemplate =  new RestTemplate();

    @GetMapping("/gw")
    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(name = "default_executionTimeoutInMilliseconds",value = "2000")
            }
    )
    public String getWay(){
       String  cnt = restTemplate.getForObject("http://127.0.0.1:9999/baned",String.class);
       return "gw: " + cnt;
    }

    @GetMapping("/gw2")
    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),
            }
    )
    public String getWay2(){
        String  cnt = restTemplate.getForObject("http://127.0.0.1:9999/baned",String.class);
        return "gw2: " + cnt;
    }

    public String defaultFallback() {
        return "sorry,sysytem error!";
    }
}
