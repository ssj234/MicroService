package com.shisj.study.springboot.hello.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 用户模型
 */
public class User {

    @Min(value= 18,message = "too small,values is {value}!")
    private int id;

    @NotBlank(message = "cannot be null!")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("User{id=%d,name=%s}",id,name);
    }
}
