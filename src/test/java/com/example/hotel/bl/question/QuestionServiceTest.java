package com.example.hotel.bl.question;

import com.example.hotel.vo.QuestionForm;
import com.example.hotel.vo.QuestionVO;
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

    private final String question = "等游戏开始我就把你们都鲨了";

    private final QuestionForm form = new QuestionForm() {{
        setHotelId(5);
        setQuestion(question);
        setUserId(3);
        setUserName("TheShy");
    }};


    @Test
    @Transactional
    public void addQuestion() {
        questionService.addQuestion(form);
        List<QuestionVO> questionVOS = questionService.getHotelQuestion(5);
        assertEquals(questionVOS.size(), 1);
    }

    @Test
    public void annulQuestion() {
    }

    @Test
    public void getHotelQuestion() {
    }

    @Test
    public void getUserQuestion() {
    }
}