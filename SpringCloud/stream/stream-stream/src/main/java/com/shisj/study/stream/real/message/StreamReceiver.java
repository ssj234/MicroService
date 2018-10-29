package com.shisj.study.stream.real.message;


import com.shisj.study.stream.real.dto.DTOObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

    @StreamListener("myMessage1")
    public void process(String message){
      log.info("StreamReceiver={}" , message);
    }
//
    @StreamListener("myMessage")
    @SendTo("myMessage1")
    public String process2(DTOObject dtoObject){
        log.info("StreamReceiver name is {}" , dtoObject.getName());
        return "handle complete!";
    }
}
