package com.example.hotel.bl.question;

import com.example.hotel.vo.QuestionForm;
import com.example.hotel.vo.QuestionVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/14 15:54
 */
public interface QuestionService {
    ResponseVO addQuestion(QuestionForm questionForm);

    ResponseVO annulQuestion(Integer questionId);

    List<QuestionVO> getHotelQuestion(Integer hotelId);

    List<QuestionVO> getUserQuestion(Integer userId);

}
