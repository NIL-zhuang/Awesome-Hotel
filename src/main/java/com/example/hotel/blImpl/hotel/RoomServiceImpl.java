package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId) {
        return roomMapper.selectRoomsByHotelId(hotelId);
    }

    @Override
    public ResponseVO insertRoomInfo(RoomVO room) {
        HotelRoom hotelRoom = new HotelRoom(room);
        List<HotelRoom> hotelRooms = roomMapper.selectRoomsByHotelId(hotelRoom.getHotelId());
        for(HotelRoom hotelRoom1 : hotelRooms){
            if(hotelRoom1.getRoomType().toString().equals(hotelRoom.getRoomType().toString())){
                int curNum = hotelRoom1.getCurNum() + hotelRoom.getTotal() - hotelRoom1.getTotal();
                if(curNum<0){
                    return ResponseVO.buildFailure("更新的房间数量太少");
                }
                hotelRoom.setCurNum(curNum);
                roomMapper.deleteRoom(room.getId(), room.getRoomType());
                break;
            }
        }
        roomMapper.insertRoom(hotelRoom);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public void deleteRoom(Integer hotelId, String roomType) {
        roomMapper.deleteRoom(hotelId, roomType);
    }

    //需要用英文版roomType
    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomMapper.updateRoomInfo(hotelId,roomType,rooms);
    }

    //需要用英文版roomType
    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        return roomMapper.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public List<HotelRoom> retrieveHotelRoomInfoByType(Integer hotelId, RoomType type) {
        List<HotelRoom> rooms = roomMapper.selectRoomsByHotelId(hotelId);
        List<HotelRoom> targetRoom = new ArrayList<>();
        for(HotelRoom room : rooms){
            if(room.getRoomType().equals(type)){
                targetRoom.add(room);
                break;
            }
        }
        return targetRoom;
    }

}
