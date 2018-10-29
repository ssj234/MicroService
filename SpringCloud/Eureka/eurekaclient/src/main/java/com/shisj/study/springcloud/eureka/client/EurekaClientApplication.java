package com.shisj.study.springcloud.eureka.client;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class EurekaClientApplication {


    @Autowired
    private EurekaClient discoveryClient;

    @RequestMapping("/")
    public String home() {
        return "Hello world";
    }

    @GetMapping("/all")
    public String serviceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("EUREKA-SERVER", false);
        return instance.getHomePageUrl();
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class,args);
    }

}