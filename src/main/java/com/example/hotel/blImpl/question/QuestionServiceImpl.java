package com.example.hotel.blImpl.question;

import com.example.hotel.bl.question.AnswerService;
import com.example.hotel.bl.question.QuestionService;
import com.example.hotel.data.question.QuestionMapper;
import com.example.hotel.po.Question;
import com.example.hotel.vo.QuestionForm;
import com.example.hotel.vo.QuestionVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/14 15:55
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerService answerService;

    @Override
    public ResponseVO addQuestion(QuestionForm questionForm) {
        Question question = new Question() {{
            setUserID(questionForm.getUserId());
            setHotelID(questionForm.getHotelId());
            setQuestion(questionForm.getQuestion());
        }};
        questionMapper.addQuestion(question);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO annulQuestion(Integer questionId) {
        questionMapper.annulQuestion(questionId);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<QuestionVO> getHotelQuestion(Integer hotelId) {
        List<Question> questions = questionMapper.getHotelQuestion(hotelId);
        return getQuestionVOS(questions);
    }

    @Override
    public List<QuestionVO> getUserQuestion(Integer userId) {
        List<Question> questions = questionMapper.getUserQuestion(userId);
        return getQuestionVOS(questions);
    }

    /**
     * 这里每一个questionVO里的answers都已经get
     */
    private List<QuestionVO> getQuestionVOS(List<Question> questions) {
        List<QuestionVO> questionVOS = new ArrayList<>();
        for (Question question : questions) {
            QuestionVO questionVO = new QuestionVO();
            BeanUtils.copyProperties(question, questionVO);
            questionVO.setAnswers(answerService.getQuestionAnswers(question.getId()));
            questionVOS.add(questionVO);
        }
        return questionVOS;
    }
}
