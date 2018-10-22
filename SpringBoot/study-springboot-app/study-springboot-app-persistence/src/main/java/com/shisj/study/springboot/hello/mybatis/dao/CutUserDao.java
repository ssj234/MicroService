package com.shisj.study.springboot.hello.mybatis.dao;

import com.shisj.study.springboot.hello.mybatis.model.CutUser;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CutUserDao {

    private final SqlSession sqlSession;

    @Autowired
    public CutUserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int saveCutUser(String id) {
        CutUser cutUser = new CutUser();
        cutUser.setOpenid(id);
        return this.sqlSession.insert("insertCutUser", cutUser);
    }

}
