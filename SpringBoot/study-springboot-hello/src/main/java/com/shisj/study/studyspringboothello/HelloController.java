package com.shisj.study.studyspringboothello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Value("${cupSize}")
    private String cupSize;

    @Value("${age}")
    private int age;

    @Value("${content}")
    private String content;

    @Autowired
    private GirlProperties girlProperties;

    @RequestMapping(value = {"/hello","/hi"},method=RequestMethod.GET)
    public String say(String name){
        // String.format( " my name is : %s,cupSize is %s,age is %d!" , name,cupSize,age)
        return "Hello SpringBoot!"
                + girlProperties.getContent();
    }

    @RequestMapping(value = {"/say/{id}"},method=RequestMethod.GET)
    public String hi(@PathVariable("id") Integer uid){
        // String.format( " my name is : %s,cupSize is %s,age is %d!" , name,cupSize,age)
        return "id is " + uid;
    }

//    @RequestMapping(value = {"/say0"},method=RequestMethod.GET)
//    @PostMapping
//    @PutMapping
//    @DeleteMapping
    @GetMapping(value = {"/say0"})
    public String hii(@RequestParam(value = "id",required = false,defaultValue = "0") Integer uid){
        // String.format( " my name is : %s,cupSize is %s,age is %d!" , name,cupSize,age)
        return "id is " + uid;
    }

    @PostMapping(value = {"/say1"})
    public String hii2(@RequestBody Map<String,Object> reqMap){
        int uid = Integer.valueOf(reqMap.get("id").toString());
        return "[post]id is " + uid;
    }

    @GetMapping(value = {"/say2"})
    public List<GirlProperties> hii(){
        // String.format( " my name is : %s,cupSize is %s,age is %d!" , name,cupSize,age)
        List ret = new ArrayList();
        GirlProperties gp = new GirlProperties();
        gp.setAge(16);
        gp.setCupSize("E");
        gp.setContent("This is a girl!");
        ret.add(gp);
        return ret;
    }
}
