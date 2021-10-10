package com.example.hotel.vo;

/**
 * @Author: chenyizong
 * @Date: 2020-03-03
 */
public class UserInfoVO {
    private String password;
    private String userName;
    private String phoneNumber;
    private String corporation;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    } // 又是这里？？？

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }
}
