package com.example.hotel.data.hotel;

import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomMapper {

    /**
     * @param minNum 这个是减去多少房间，天坑
     */

    int updateRoomInfo(@Param("hotelId") Integer hotelId,@Param("roomType") String roomType,@Param("minNum") Integer minNum);

    int insertRoom(HotelRoom hotelRoom);

    List<HotelRoom> selectRoomsByHotelId(@Param("hotelId") Integer hotelId);

    int getRoomCurNum(@Param("hotelId") Integer hotelId,@Param("roomType") String roomType);

    void updateRoomNum(@Param("hotelId") Integer hotelId, @Param("roomType") String roomType, @Param("total") Integer total);

    void updateCurNum(@Param("hotelId") Integer hotelId, @Param("roomType") String roomType, @Param("curNum") Integer curNum);

    void updateRoomPrice(@Param("hotelId") Integer hotelId, @Param("roomType") String roomType, @Param("price") Double price);

    void deleteRoom(@Param("hotelId") Integer hotelId, @Param("roomType") String roomType);
}
