package com.shisj.study.springboot.hello.controller;

import com.shisj.study.springboot.hello.domain.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/aspect")
public class AspectController {



    @GetMapping("/buy")
    public User buy(User user) throws Exception {
        System.out.print("add user" + user.toString());
        return user;
    }
}
