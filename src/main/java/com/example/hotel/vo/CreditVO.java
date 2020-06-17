package com.example.hotel.vo;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/24 10:58
 */
public class CreditVO {
    Integer id;
    Integer userId;
    String changeDate;
    Integer change;
    double now;
    String reason;
    int status;
    String argue;

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

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String date) {
        this.changeDate = date;
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
