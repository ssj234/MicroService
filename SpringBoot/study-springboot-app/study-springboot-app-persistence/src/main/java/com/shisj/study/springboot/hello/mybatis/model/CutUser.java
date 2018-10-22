package com.shisj.study.springboot.hello.mybatis.model;

public class CutUser {

    private Integer userid;

    private String openid;

    private String nickname;

    private String mobilephone;

    private Integer sex;

    private String head;

    /**
     * @return userId
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * @return openId
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * @param openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * @return nickName
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return mobilePhone
     */
    public String getMobilephone() {
        return mobilephone;
    }

    /**
     * @param mobilephone
     */
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    /**
     * @return sex
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @return head
     */
    public String getHead() {
        return head;
    }

    /**
     * @param head
     */
    public void setHead(String head) {
        this.head = head;
    }
}