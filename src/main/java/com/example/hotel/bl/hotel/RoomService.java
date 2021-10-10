package com.example.hotel.bl.hotel;

import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;

import java.util.List;

public interface RoomService {

    /**
     * 获取某个酒店的全部房间信息
     */
    List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId);

    List<HotelRoom> retrieveHotelRoomInfoByType(Integer hotelId, RoomType type);

    /**
     * 添加酒店客房信息
     */
    ResponseVO insertRoomInfo(RoomVO hotelRoom);

    /**
     *
     */
    void deleteRoom(Integer hotelId, String roomType);

    /**
     * 预订酒店后更新客房房间数量
     */
    void updateRoomInfo(Integer hotelId, String roomType, Integer rooms);

    /**
     * 获取酒店指定房间剩余数量
     */
    int getRoomCurNum(Integer hotelId, String roomType);
}
