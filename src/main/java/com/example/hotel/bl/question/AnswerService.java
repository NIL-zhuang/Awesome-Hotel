package com.example.hotel.bl.question;

import com.example.hotel.po.Answer;
import com.example.hotel.vo.AnswerVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/14 16:29
 */
public interface AnswerService {
    ResponseVO addAnswer(AnswerVO answerVO);

    List<AnswerVO> getAllAnswers();

    List<AnswerVO> getQuestionAnswers(Integer questionId);

    ResponseVO annulAnswer(Integer answerId);
}
