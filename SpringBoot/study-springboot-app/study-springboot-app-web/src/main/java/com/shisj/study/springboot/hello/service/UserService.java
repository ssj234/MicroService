package com.shisj.study.springboot.hello.service;

import com.shisj.study.springboot.hello.mybatis.model.CutUser;

public interface UserService {
    public CutUser addUser(String openId);
}
