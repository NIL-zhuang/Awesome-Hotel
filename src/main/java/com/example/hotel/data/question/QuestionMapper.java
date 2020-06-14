package com.example.hotel.data.question;

import com.example.hotel.po.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/24 10:49
 */
@Mapper
@Repository
public interface QuestionMapper {
    int addQuestion(Question question);

    /**
     * 只改available属性，不改question值
     */
    int annulQuestion(@Param("id") int questionId);

    List<Question> getHotelQuestion(@Param("hotelId") int hotelId);

    List<Question> getUserQuestion(@Param("userId") int userId);
}
