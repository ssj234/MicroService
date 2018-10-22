package com.shisj.study.springboot.hello.controller;

import com.shisj.study.springboot.hello.domain.User;
import com.shisj.study.springboot.hello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserRepository userRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/person/save")
    public User save(@RequestParam("name") String name){
        User user = new User();
        user.setName(name);
        userRepository.save(user);
        return  user;
    }

    @GetMapping("/person/{id}")
    public User get(@PathVariable("id") int id) throws  Exception{
        User user = new User();
        user.setId(id);

//        throw new Exception("ddd");
        String aaa = stringRedisTemplate.opsForValue().get("aaa");
        if(aaa == null || aaa.trim().length() == 0){
            stringRedisTemplate.opsForValue().set("aaa","aaa-value");
        }
        user.setName("id="+id + "" + aaa);
        return  user;
    }
}
