package com.example.hotel.data.question;

import com.example.hotel.po.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/24 10:48
 */
@Mapper
@Repository
public interface AnswerMapper {
    int addAnswer(Answer answer);

    List<Answer> getAllAnswers();

    List<Answer> getQuestionAnswers(@Param("questionId") Integer questionId);

    int annulAnswer(@Param("answerId") Integer answerId);
}
