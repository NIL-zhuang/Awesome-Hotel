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
    String changeDate;
    Integer change;
    double now;
    String reason;

    // 申诉内容
    String argue;
    // 0表示正常，1表示正在申诉，2表示申诉成功，-1表示申诉失败
    int status;

    public Credit() {
    }

    public Credit(int userId, int change, double now, String reason) {
        this.userId = userId;
        this.change = change;
        this.now = now;
        this.reason = reason;
        Date cur = new Date(System.currentTimeMillis());
        changeDate = new SimpleDateFormat("yyyy-MM-dd").format(cur);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setChange(Integer change) {
        this.change = change;
    }

    public Integer getChange() {
        return change;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public double getNow() {
        return now;
    }

    public void setNow(double now) {
        this.now = now;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getArgue() {
        return argue;
    }

    public void setArgue(String argue) {
        this.argue = argue;
    }
}
