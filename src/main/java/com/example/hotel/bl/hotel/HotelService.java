package com.example.hotel.bl.hotel;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.po.Order;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;

import java.util.List;

public interface HotelService {

    /**
     * 添加酒店
     */
    ResponseVO addHotel(HotelForm hotelForm) throws ServiceException;

    ResponseVO updateHotelInfo(Integer hotelId, HotelForm hotelForm);

    /**
     * 删除酒店
     */
    void deleteHotel(Integer hotelId);

    /**
     * 预订酒店修改剩余客房信息
     */
    void updateRoomInfo(Integer hotelId, String roomType, Integer rooms);

    /**
     * 列表获取酒店信息
     */
    List<HotelVO> retrieveHotels();

    List<HotelVO> retrieveHotels(int start, int end);

    /**
     * 获取某家酒店详细信息
     */
    HotelVO retrieveHotelDetails(Integer hotelId);

    /**
     * 查看酒店剩余某种房间数量
     */
    int getRoomCurNum(Integer hotelId, String roomType);

    void updateHotelPicture(Integer hotelId, String url);

    void addComment(CommentVO commentVO, Integer hotelId);

    void annulComment(CommentVO commentVO, Integer hotelId);

}
