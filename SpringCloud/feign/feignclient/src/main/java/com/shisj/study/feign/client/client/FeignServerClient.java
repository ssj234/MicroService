package com.shisj.study.feign.client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 定义服务的接口
 */

@FeignClient(name = "FEIGNSERVER")
@RequestMapping("/feign")
public interface FeignServerClient {

    @GetMapping("/hello")
    String hello();
}
