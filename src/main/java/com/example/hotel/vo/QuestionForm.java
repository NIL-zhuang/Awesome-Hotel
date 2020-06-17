package com.example.hotel.vo;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/15 20:39
 */
public class QuestionForm {
    Integer userId;
    Integer hotelId;
    String question;
    String userName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
