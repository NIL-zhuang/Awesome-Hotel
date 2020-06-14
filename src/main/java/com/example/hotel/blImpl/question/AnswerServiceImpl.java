package com.example.hotel.blImpl.question;

import com.example.hotel.bl.question.AnswerService;
import com.example.hotel.data.question.AnswerMapper;
import com.example.hotel.po.Answer;
import com.example.hotel.vo.AnswerVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/14 16:36
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    private static final String ADD_SUCCESS = "答案添加成功";
    private static final String ADD_FAIL = "答案添加失败";
    private static final String ANNUL_SUCCESS = "答案删除成功";

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public ResponseVO addAnswer(AnswerVO answerVO) {
        try {
            Answer answer = new Answer();
            BeanUtils.copyProperties(answerVO, answer);
            answerMapper.addAnswer(answer);
            return ResponseVO.buildSuccess(ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(ADD_FAIL);
        }
    }

    @Override
    public List<AnswerVO> getAllAnswers() {
        List<Answer> answers = answerMapper.getAllAnswers();
        return getAnswerVOS(answers);
    }

    @Override
    public List<AnswerVO> getQuestionAnswers(Integer questionId) {
        List<Answer> answers = answerMapper.getQuestionAnswers(questionId);
        return getAnswerVOS(answers);
    }

    private List<AnswerVO> getAnswerVOS(List<Answer> answers) {
        List<AnswerVO> answerVOS = new ArrayList<>();
        for (Answer answer : answers) {
            AnswerVO answerVO = new AnswerVO();
            BeanUtils.copyProperties(answer, answerVO);
            answerVOS.add(answerVO);
        }
        return answerVOS;
    }

    @Override
    public ResponseVO annulAnswer(Integer answerId) {
        answerMapper.annualAnswer(answerId);
        return ResponseVO.buildSuccess(ANNUL_SUCCESS);
    }
}
