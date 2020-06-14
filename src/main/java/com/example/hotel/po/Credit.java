package com.example.hotel.po;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/24 15:52
 */
public class Credit {
    Integer id;
    Integer userId;
    String date;
    Integer change;
    double now;
    String reason;

    public Credit(int userId, int change, double now, String reason){
        this.userId = userId;
        this.change = change;
        this.now = now;
        this.reason = reason;
        Date cur = new Date(System.currentTimeMillis());
        date = new SimpleDateFormat("yyyy-MM-dd").format(cur);
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setChange(Integer change) {
        this.change = change;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getChange() {
        return change;
    }

    public Integer getUserId() {
        return userId;
    }

    public double getNow() {
        return now;
    }

    public void setNow(double now) {
        this.now = now;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
