package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private OrderService orderService;

    @Override
    public void addHotel(HotelForm hotelForm) {
        Hotel hotel = new Hotel();
        hotel.setDescription(hotelForm.getDescription());
        hotel.setAddress(hotelForm.getAddress());
        hotel.setHotelName(hotelForm.getName());
        hotel.setPhoneNum(hotelForm.getPhoneNum());
        hotel.setRate(hotelForm.getRate());
        hotel.setBizRegion(BizRegion.valueOf(hotelForm.getBizRegion()));
        hotel.setHotelStar(HotelStar.valueOf(hotelForm.getHotelStar()));
        hotelMapper.insertHotel(hotel);
    }

    @Override
    public void deleteHotel(Integer hotelId) {
        hotelMapper.deleteHotel(hotelId);
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomService.updateRoomInfo(hotelId, roomType, rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        return roomService.getRoomCurNum(hotelId, roomType);
    }

    @Override
    public List<HotelVO> retrieveHotels() {
        List<Hotel> hotels = hotelMapper.selectAllHotel();
        List<HotelVO> hotelVOs = new ArrayList<>();
        for (Hotel hotel:hotels){
            hotelVOs.add(new HotelVO(hotel));
        }

        return hotelVOs;
    }

    @Override
    public List<HotelVO> retrieveHotels(String start,String end){
        //todo
        return null;
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
        Hotel hotel = hotelMapper.selectById(hotelId);
        HotelVO hotelVO = new HotelVO(hotel);
        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelId);
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setBedType(r.getBedType().toString());
            roomVO.setBreakfast(r.getBreakfast().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            roomVO.setPeopleNum(r.getPeopleNum());
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);

        return hotelVO;
    }

    @Override
    public HotelVO retrieveAvailableHotelDetails(Integer hotelId, String beginTime, String endTime) {
        HotelVO hotel = retrieveHotelDetails(hotelId);
        List<RoomVO> rooms = hotel.getRooms();
        List<RoomVO> roomVOS = new ArrayList<>();
        List<Order> orders = orderService.getHotelOrders(hotelId);
        orders = orderService.getOrdersInMonth(orders);
        orders = orderService.washOrder(orders,beginTime,endTime);

        HashMap<String,Integer> roomNum = new HashMap<>();

        for (RoomVO room : rooms) {
           roomNum.put(room.getRoomType(),room.getTotal());
        }

        for(Order order : orders){
            int curNum = roomNum.get(order.getRoomType()) - order.getRoomNum();
            roomNum.put(order.getRoomType(),curNum);
        }

        for(RoomVO room : rooms){
            if(roomNum.get(room.getRoomType())>0)
                roomVOS.add(room);
        }
        hotel.setRooms(roomVOS);
        return hotel;
    }

    @Override
    public List<RoomVO> checkRoom(List<RoomVO> rooms, String beginTime, String endTime) {
        return null;
    }

    @Override
    public ResponseVO addLike(LikeVO likeVO) {
        return null;
    }

    @Override
    public ResponseVO removeLike(Integer userId, Integer hotelId) {
        return null;
    }

    @Override
    public boolean getLike(Integer userId, Integer hotelId) {
        return false;
    }

    @Override
    public ResponseVO addCollection(CollectionVO collectionVO) {
        return null;
    }

    @Override
    public List<Integer> getCollections(int userId) {
        return null;
    }

    @Override
    public ResponseVO addQuestion(QuestionVO questionVO) {
        return null;
    }

    @Override
    public ResponseVO annualQuestion(Integer questionId) {
        return null;
    }

    @Override
    public void updateHotelPicture(Integer hotelId, String url) {
        hotelMapper.updatePicture(hotelId, url);
    }


}
