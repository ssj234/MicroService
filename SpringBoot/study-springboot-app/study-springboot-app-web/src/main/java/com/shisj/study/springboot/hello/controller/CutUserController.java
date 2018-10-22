package com.shisj.study.springboot.hello.controller;

import com.shisj.study.springboot.hello.mybatis.mapper.CutUserMapper;
import com.shisj.study.springboot.hello.service.UserService;
import com.shisj.study.springboot.hello.mybatis.model.CutUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cutuser")
class CutUserController {

    @Autowired
    public CutUserMapper cutUserMapper;

    @GetMapping("/save/{openId}")
    public CutUser save(@PathVariable("openId") String openId){
        CutUser cutUser = new CutUser();
        cutUser.setOpenid(openId);
        cutUserMapper.insertCutUser(cutUser);
        return cutUser;
    }
}
