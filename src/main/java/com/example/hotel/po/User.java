package com.example.hotel.po;

import com.example.hotel.enums.UserType;
import com.example.hotel.enums.VIPType;

public class User {
    private Integer id;
    private String email;
    private String password;
    private String userName;
    private String phoneNumber;
    private double credit;
    private UserType userType;
    private String birthday;
    private String corporation;
    private Integer annulTime = 3;
    private String portrait;
    private String jobNumber;
    private Integer hotelID;
    private VIPType vipType;

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getJobNumber() {
        return this.jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    } // 只有这个没被调用？为什么？

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCorporation() {
        return this.corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public Integer getAnnulTime() {
        return this.annulTime;
    }

    public void setAnnulTime(Integer annulTime) {
        this.annulTime = annulTime;
    }

    public Integer getHotelID() {
        return hotelID;
    }

    public void setHotelID(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public VIPType getVipType() {
        return vipType;
    }

    public void setVipType(VIPType vipType) {
        this.vipType = vipType;
    }

}
