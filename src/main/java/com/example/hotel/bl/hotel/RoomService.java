package com.example.hotel.bl.hotel;

import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;

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
    void insertRoomInfo(HotelRoom hotelRoom);

    /**
     * 预订酒店后更新客房房间数量
     */
    void updateRoomInfo(Integer hotelId, String roomType, Integer rooms);

    /**
     * 获取酒店指定房间剩余数量
     */
    int getRoomCurNum(Integer hotelId, String roomType);

    /**
     * @return 在入住-退房时间里的可用房间数量
     */
    Integer getRoomCurNumByTime(Integer hotelId, String beginTime, String endTime, String type);
}
