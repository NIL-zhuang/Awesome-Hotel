package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    private static final String ADDRESS_OCCUPIED = "填写的地址已被占用！";

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private AccountService accountService;


    @Override
    public ResponseVO addHotel(HotelForm hotelForm) {
        Hotel hotel = new Hotel();
        hotel.setDescription(hotelForm.getDescription());
        hotel.setAddress(hotelForm.getAddress());
        hotel.setHotelName(hotelForm.getName());
        hotel.setPhoneNum(hotelForm.getPhoneNum());
        hotel.setBizRegion(BizRegion.valueOf(hotelForm.getBizRegion()));
        hotel.setHotelStar(HotelStar.valueOf(hotelForm.getHotelStar()));
        try {
            hotelMapper.insertHotel(hotel);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            return ResponseVO.buildFailure(ADDRESS_OCCUPIED);
        }
    }

    @Override
    public ResponseVO updateHotelInfo(Integer hotelId, HotelForm hotelForm){
        try {
            if (hotelForm.getBizRegion()!=null) hotelMapper.updateHotelBizRegion(hotelId, BizRegion.valueOf(hotelForm.getBizRegion()));
            if (!hotelMapper.getHotelAddress(hotelId).equals(hotelForm.getAddress())) {
                hotelMapper.updateHotelAddress(hotelId, hotelForm.getAddress()); // 为防止重复添加相同地址导致错误（地址是unique），这里先做检查再添加
            }
            hotelMapper.updateHotelDescription(hotelId, hotelForm.getDescription());
            hotelMapper.updateHotelPhoneNum(hotelId, hotelForm.getPhoneNum());
            if (hotelForm.getHotelStar()!=null) hotelMapper.updateHotelStar(hotelId, HotelStar.valueOf(hotelForm.getHotelStar()));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(ADDRESS_OCCUPIED);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public void deleteHotel(Integer hotelId) {
        hotelMapper.deleteHotel(hotelId);
    }

    /**
     * 用于办理入住，在curNum中减去房间数
     *
     * @param hotelId
     * @param roomType
     * @param rooms
     */
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
        for (Hotel hotel : hotels) {
            hotelVOs.add(new HotelVO(hotel));
        }

        return hotelVOs;
    }

    @Override
    public List<HotelVO> retrieveHotels(int start, int end) {   //左闭右开,返回区间内的hotel组成的list
        //返回某个区间内的酒店
        List<HotelVO> targetHotels = new ArrayList<>();
        List<HotelVO> hotelVOS = retrieveHotels();
        int l = hotelVOS.size();
        if (start >= 0 && start < end && end < l) {
            for (int i = start; i < end; i++) {
                targetHotels.add(hotelVOS.get(i));
            }
        } else if (end >= l) {
            for (int i = start; i < l; i++) {
                targetHotels.add(hotelVOS.get(i));
            }
        }

        return targetHotels;
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
    public void updateHotelPicture(Integer hotelId, String url) {
        hotelMapper.updatePicture(hotelId, url);
    }

    @Override
    public void addComment(CommentVO commentVO, Integer hotelId) {
        Hotel hotel = hotelMapper.selectById(hotelId);
        int time = hotel.getCommentTime();
        int new_time = time + 1;
        double n_point = calComment(hotel.getPoints(), time, new_time, commentVO.getPoints());
        double n_sanitation = calComment(hotel.getSanitation(), time, new_time, commentVO.getSanitation());
        double n_environment = calComment(hotel.getEnvironment(), time, new_time, commentVO.getEnvironment());
        double n_service = calComment(hotel.getService(), time, new_time, commentVO.getService());
        double n_equipment = calComment(hotel.getEquipment(), time, new_time, commentVO.getEquipment());
        hotelMapper.updateHotelPoints(hotelId, new_time, n_point, n_sanitation, n_environment, n_service, n_equipment);

    }

    @Override
    public void annulComment(CommentVO commentVO, Integer hotelId) {
        Hotel hotel = hotelMapper.selectById(hotelId);
        int time = hotel.getCommentTime();
        if (time == 1) {
            hotelMapper.updateHotelPoints(hotelId, 0, 5, 5, 5, 5, 5);
            return;
        }
        int new_time = time - 1;
        double n_point = calComment(hotel.getPoints(), time, new_time, -commentVO.getPoints());
        double n_sanitation = calComment(hotel.getSanitation(), time, new_time, -commentVO.getSanitation());
        double n_environment = calComment(hotel.getEnvironment(), time, new_time, -commentVO.getEnvironment());
        double n_service = calComment(hotel.getService(), time, new_time, -commentVO.getService());
        double n_equipment = calComment(hotel.getEquipment(), time, new_time, -commentVO.getEquipment());
        hotelMapper.updateHotelPoints(hotelId, new_time, n_point, n_sanitation, n_environment, n_service, n_equipment);
    }

    private double calComment(double origin, double time, double new_time, double change) {
        return (origin * time + change) / new_time;
    }
}
