package com.example.hotel.bl.question;

import com.example.hotel.vo.AnswerVO;
import com.example.hotel.vo.ResponseVO;
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
public class AnswerServiceTest {
    private final String ADD_SUCCESS = "答案添加成功";

    @Autowired
    private AnswerService answerService;

    private final AnswerVO answerVO = new AnswerVO() {{
        setQuestionId(8);
        setUserName("小姜");
        setUserId(5);
        setAnswer("天神下饭");
    }};

    @Test
    @Transactional
    public void addAnswer() {
        ResponseVO responseVO = answerService.addAnswer(answerVO);
        assertEquals(responseVO.getContent(), ADD_SUCCESS);
    }

    @Test
    @Transactional
    public void getAllAnswers() {
        answerService.addAnswer(answerVO);
        List<AnswerVO> answerVOList = answerService.getAllAnswers();
        assertEquals(answerVOList.size(), 1);
    }

    @Test
    @Transactional
    public void getQuestionAnswers() {
        answerService.addAnswer(answerVO);
        List<AnswerVO> answerVOList = answerService.getQuestionAnswers(8);
        assertEquals(answerVOList.size(), 1);
    }

    @Test
    @Transactional
    public void annulAnswer() {
        answerService.addAnswer(answerVO);
        AnswerVO answer = answerService.getAllAnswers().get(0);
        answerService.annulAnswer(answer.getAnswerId());
        assertEquals(answerService.getAllAnswers().size(), 0);
    }
}