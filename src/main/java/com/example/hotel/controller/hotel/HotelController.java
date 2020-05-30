package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelSearchService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.SearchBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {
        hotelService.addHotel(hotelVO);
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
                                       @RequestBody String startTime,
                                       @RequestBody String endTime) {
        return ResponseVO.buildSuccess(hotelService.retrieveAvailableHotelDetails(hotelId, startTime, endTime));
    }


    @GetMapping
    public ResponseVO searchHotel(@RequestBody SearchBody searchBody){
        return ResponseVO.buildSuccess(hotelSearchService.searchHotel(searchBody));
    }

    // TODO
    @PostMapping("/{hotelId}/registerHotelMembership")
    public ResponseVO registerHotelMembership(@PathVariable Integer hotelId) {
        System.out.println(hotelId);
        return ResponseVO.buildSuccess(true);
    }
}
