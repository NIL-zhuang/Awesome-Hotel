package com.example.hotel.vo;

import java.util.List;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/24 15:53
 */
public class QuestionVO {

    Integer id;
    Integer userId;
    Integer hotelId;
    String question;
    String userName;
    List<AnswerVO> answers;

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public String getQuestion() {
        return question;
    }

    public String getUserName() {
        return userName;
    }

    public List<AnswerVO> getAnswers() {
        return answers;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAnswers(List<AnswerVO> answers) {
        this.answers = answers;
    }


}
