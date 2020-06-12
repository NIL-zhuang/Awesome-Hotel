package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelSearchService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.HotelForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.SearchBodyVO;
import org.apache.coyote.Response;
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
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        roomService.insertRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess(true);
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
}
