package com.shisj.study.springboot.hello.service;

import com.shisj.study.springboot.hello.mybatis.dao.CutUserDao;
import com.shisj.study.springboot.hello.mybatis.model.CutUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private CutUserDao cutUserDao;


    @Override
    public CutUser addUser(String openId) {
        CutUser cutUser = new CutUser();
        cutUser.setOpenid(openId);
        cutUserDao.saveCutUser(openId);
        return cutUser;
    }
}
