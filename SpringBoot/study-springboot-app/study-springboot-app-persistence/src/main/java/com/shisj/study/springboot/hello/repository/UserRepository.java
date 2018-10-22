package com.shisj.study.springboot.hello.repository;


import com.shisj.study.springboot.hello.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {

    Map<Integer,User> store = new ConcurrentHashMap<Integer,User>();

    private final static AtomicInteger idGen = new AtomicInteger(0);

    public boolean save(User user){
        user.setId(idGen.incrementAndGet());
        store.put(user.getId(),user);
        return true;
    }

}
