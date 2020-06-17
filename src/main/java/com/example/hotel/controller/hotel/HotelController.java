package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelSearchService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.question.AnswerService;
import com.example.hotel.bl.question.QuestionService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private HotelSearchService hotelSearchService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;


    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelForm hotelForm) throws ServiceException {
        hotelService.addHotel(hotelForm);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/all")
    public ResponseVO retrieveAllHotels() {
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestParam HotelRoom hotelRoom) {
        roomService.insertRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess(true);
    }

    //如果end超过当前酒店数量，则返回从start到最后一个酒店
    @GetMapping("/retrieveHotels")
    public ResponseVO retrieveHotels(@RequestParam(value = "start") Integer start, @RequestParam(value = "end") Integer end){   //左闭右开,返回区间内的hotel组成的list
        return ResponseVO.buildSuccess(hotelService.retrieveHotels(start,end));
    }

    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }

    @GetMapping("/{hotelId}/getAvailableRoom")
    public ResponseVO getAvailableRoom(@PathVariable Integer hotelId,
                                       @RequestParam(value = "startTime") String startTime,
                                       @RequestParam(value = "endTime") String endTime) {
        return ResponseVO.buildSuccess(hotelService.retrieveAvailableHotelDetails(hotelId, startTime, endTime));
    }


    @PostMapping("/searchHotel")
    public ResponseVO searchHotel(@RequestBody SearchBodyVO searchBody) {
        return ResponseVO.buildSuccess(hotelSearchService.searchHotel(searchBody));
    }

    // 准备删除这段代码
    @PostMapping("/{hotelId}/registerHotelMembership")
    public ResponseVO registerHotelMembership(@PathVariable Integer hotelId) {
        System.out.println(hotelId);
        return ResponseVO.buildSuccess(true);
    }

    @PostMapping("/{hotelId}/deleteHotel")
    public ResponseVO deleteHotel(@PathVariable Integer hotelId) {
        hotelService.deleteHotel(hotelId);
        return ResponseVO.buildSuccess(true);
    }

    @PostMapping("/addQuestion")
    public ResponseVO addQuestion(@RequestBody QuestionForm questionForm) {
        return questionService.addQuestion(questionForm);
    }

    @PostMapping("/{questionId}/annulQuestion")
    public ResponseVO annulQuestion(@PathVariable Integer questionId) {
        return questionService.annulQuestion(questionId);
    }

    @GetMapping("/{hotelId}/getHotelQuestion")
    public ResponseVO getHotelQuestion(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(questionService.getHotelQuestion(hotelId));
    }

    @GetMapping("/{userId}/getUserQuestion")
    public ResponseVO getUserQuestion(@PathVariable Integer userId) {
        return ResponseVO.buildSuccess(questionService.getUserQuestion(userId));
    }

    @PostMapping("/addAnswer")
    public ResponseVO addAnswer(@RequestBody AnswerVO answerVO) {
        return answerService.addAnswer(answerVO);
    }

    @PostMapping("/{answerId}/annulAnswer")
    public ResponseVO annulAnswer(@PathVariable Integer answerId) {
        return answerService.annulAnswer(answerId);
    }

    @GetMapping("/getAllAnswers")
    public ResponseVO getAllAnswers() {
        return ResponseVO.buildSuccess(answerService.getAllAnswers());
    }

    @GetMapping("/{questionId}/getQuestionAnswers")
    public ResponseVO getQuestionAnswer(@PathVariable Integer questionId) {
        return ResponseVO.buildSuccess(answerService.getQuestionAnswers(questionId));
    }
}
