package com.shisj.study.stream.real.controller;

import com.shisj.study.stream.real.dto.DTOObject;
import com.shisj.study.stream.real.message.StreamClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class StreamController {

    @Autowired
    private  StreamClient streamClient;

    @GetMapping("/send")
    public String  send(){
        String message = new Date().toString();
        streamClient.output().send(MessageBuilder.withPayload(message).build());
        return "ok";
    }

    @GetMapping("/send2")
    public String  send2(){
        streamClient.output().send(MessageBuilder.withPayload(new DTOObject("stream",1)).build());
        return "ok";
    }
}
