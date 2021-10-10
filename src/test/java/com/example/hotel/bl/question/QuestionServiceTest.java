package com.example.hotel.bl.question;

import com.example.hotel.po.Answer;
import com.example.hotel.vo.AnswerVO;
import com.example.hotel.vo.QuestionForm;
import com.example.hotel.vo.QuestionVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/17 15:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    private final String question = "等游戏开始我就把你们都鲨了";
    private final String answer = "及时雨送姜";
    private final String answerName = "姜承禄";

    private final QuestionForm form = new QuestionForm() {{
        setHotelId(5);
        setQuestion(question);
        setUserId(3);
        setUserName("TheShy");
    }};

    private AnswerVO answerVO = new AnswerVO() {{
        setAnswer(answer);
        setQuestionId(22);
        setUserId(4);
        setUserName(answerName);
    }};


    @Test
    @Transactional
    public void addQuestion() {
        questionService.addQuestion(form);
        List<QuestionVO> questionVOS = questionService.getHotelQuestion(5);
        assertEquals(questionVOS.size(), 1);
    }

    @Test
    @Transactional
    public void annulQuestion() {
        questionService.addQuestion(form);
        List<QuestionVO> questionVOS = questionService.getHotelQuestion(5);
        QuestionVO questionVO = questionVOS.get(0);
        int id = questionVO.getId();
        questionService.annulQuestion(id);
        assertEquals(questionService.getHotelQuestion(5).size(), 0);

    }

    @Test
    @Transactional
    public void getHotelQuestion() {
        questionService.addQuestion(form);
        QuestionVO questionVO = questionService.getHotelQuestion(5).get(0);
        answerVO.setQuestionId(questionVO.getId());
        answerService.addAnswer(answerVO);
        questionVO = questionService.getHotelQuestion(5).get(0);
        assertEquals(questionVO.getAnswers().size(), 1);
        assertEquals(questionVO.getAnswers().get(0).getAnswer(), answer);
        assertEquals(questionVO.getAnswers().get(0).getUserName(), answerName);
        assertEquals(questionVO.getQuestion(), question);
    }

    @Test
    @Transactional
    public void getUserQuestion() {
        questionService.addQuestion(form);
        QuestionVO questionVO = questionService.getHotelQuestion(5).get(0);
        answerVO.setQuestionId(questionVO.getId());
        answerService.addAnswer(answerVO);
        questionVO = questionService.getUserQuestion(3).get(0);
        assertEquals(questionVO.getAnswers().size(), 1);
        assertEquals(questionVO.getAnswers().get(0).getAnswer(), answer);
        assertEquals(questionVO.getAnswers().get(0).getUserName(), answerName);
        assertEquals(questionVO.getQuestion(), question);
    }
}