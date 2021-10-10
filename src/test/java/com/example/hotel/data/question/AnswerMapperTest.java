package com.example.hotel.data.question;

import com.example.hotel.po.Answer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnswerMapperTest {

    @Autowired
    private AnswerMapper answerMapper;

    private final Answer answer = new Answer() {{
        setAnswer("这啥啊");
        setQuestionId(5);
        setUserId(3);
        setUserName("128");
    }};

    @Test
    @Transactional
    public void addAnswer() {
        int val = answerMapper.addAnswer(answer);
        assertEquals(val, 1);
    }

    @Test
    @Transactional
    public void getAllAnswers() {
        answerMapper.addAnswer(answer);
        assertEquals(answerMapper.getAllAnswers().size(), 1);
    }

    @Test
    @Transactional
    public void getQuestionAnswers() {
        answerMapper.addAnswer(answer);
        assertEquals(answerMapper.getQuestionAnswers(5).size(), 1);
    }

    @Test
    @Transactional
    public void annualAnswer() {
        answerMapper.addAnswer(answer);
        int id = answerMapper.getAllAnswers().get(0).getAnswerId();
        answerMapper.annulAnswer(id);
        assertEquals(answerMapper.getAllAnswers().size(), 0);
    }
}