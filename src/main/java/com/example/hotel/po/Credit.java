package com.example.hotel.po;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/24 15:52
 */
public class Credit {
    Integer id;
    Integer userId;
    Integer change;
    Integer now;

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

    public Integer getNow() {
        return now;
    }

    public void setNow(Integer now) {
        this.now = now;
    }
}
