package com.example.hotel.po;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/24 15:52
 */
public class Question {
    Integer id;
    Integer userID;
    Integer hotelID;
    String question;

    public Integer getId() {
        return id;
    }

    public Integer getUserID() {
        return userID;
    }

    public Integer getHotelID() {
        return hotelID;
    }

    public String getQuestion() {
        return question;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setHotelID(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
