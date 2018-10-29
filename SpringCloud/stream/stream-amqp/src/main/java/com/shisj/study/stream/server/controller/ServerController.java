package com.shisj.study.stream.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ServerController {

    @Autowired
    AmqpTemplate amqpTemplate;

    @GetMapping("/send")
    public String send(@RequestParam("content") String content,@RequestParam("key") String key) {
//        amqpTemplate.convertAndSend("streamQueue","server send " + content);
        amqpTemplate.convertAndSend("myExchange",key,"server send " + content);
        return "server send ok!";
    }



    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("fruitQueue"),
            key = {"fruit"},
            exchange = @Exchange("myExchange")
    ))
    public void processFruit(String message){
        log.info("receive processFruit：" +message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("computerQueue"),
            key = {"compouter"},
            exchange = @Exchange("myExchange")
    ))
    public void processComputer(String message){
        log.info("receive processComputer ：" +message);
    }
}
