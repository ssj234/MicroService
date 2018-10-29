package com.shisj.study.feign.client.controller;


import com.shisj.study.feign.client.client.FeignServerClient;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/feign")
public class FeignClientController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;


    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello1")
    public String hello1(){
        // 1.直接指定ip去调用远程的服务，
        // 服务直接写死，多个ip需要负载均衡
        RestTemplate restTemplate =  new RestTemplate();
        String ret = restTemplate.getForObject("http://127.0.0.1:9871/feign/hello",String.class);
        return String.format("feignclient1:[feignserver return: %s]",ret);
    }


    @GetMapping("/hello2")
    public String hello2(){
        // 2. loadBalancerClient 可以根据服务选择服务器实例，这样不需要写死
        ServiceInstance serviceInstance = loadBalancerClient.choose("FEIGNSERVER");
        String url = String.format("http://%s:%d",serviceInstance.getHost(),serviceInstance.getPort());
        String ret = new RestTemplate().getForObject(url + "/feign/hello",String.class);
        return String.format("feignclient2:[feignserver return: %s]",ret);
    }

    @GetMapping("/hello3")
    public String hello3(){
        // 3. 利用注解为restTemplate添加负载均衡
        String ret = restTemplate.getForObject(  "http://FEIGNSERVER/feign/hello",String.class);
        return String.format("feignclient3:[feignserver return: %s]",ret);
    }


    @Autowired
    private FeignServerClient feignServerClient;
    @GetMapping("/hello4")
    public String hello4(){
        // 4. 利用FeignClient
        String ret = feignServerClient.hello();
        return String.format("feignclient4:[feignserver return: %s]",ret);
    }
}
