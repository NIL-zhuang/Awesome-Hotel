package com.example.hotel.data.question;

import com.example.hotel.po.Question;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionMapperTest {

    @Autowired
    private QuestionMapper questionMapper;

    private final Question question = new Question() {{
        setUserID(10);
        setQuestion("test");
        setHotelID(10);
    }};

    @Test
    @Transactional
    public void addQuestion() {
        int val = questionMapper.addQuestion(question);
        Assert.assertEquals(val, 1);
    }

    @Test
    @Transactional
    public void annualQuestion() {
        questionMapper.addQuestion(question);
        Question question = questionMapper.getHotelQuestion(10).get(0);
        questionMapper.annulQuestion(question.getId());
        Assert.assertEquals(questionMapper.getHotelQuestion(10).size(), 0);
    }

    @Test
    @Transactional
    public void getHotelQuestion() {
        questionMapper.addQuestion(question);
        Assert.assertEquals(questionMapper.getHotelQuestion(10).size(), 1);
    }

    @Test
    @Transactional
    public void getUserQuestion() {
        questionMapper.addQuestion(question);
        Assert.assertEquals(questionMapper.getUserQuestion(10).size(), 1);
    }
}