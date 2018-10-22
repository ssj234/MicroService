package com.shisj.study.springboot.hello.controller;

import com.shisj.study.springboot.hello.domain.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/valid")
public class ValidController {


    @GetMapping("/t1")
    public User valid(@Valid User user, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){

            throw new Exception(bindingResult.getFieldError().getField()+" "+bindingResult.getFieldError().getDefaultMessage());
        }
        System.out.print("add user" + user.toString());
        return user;
    }

    @GetMapping("/t2")
    public User valid2(@Validated User user) throws Exception {
        System.out.print("add user" + user.toString());
        return user;
    }
}
