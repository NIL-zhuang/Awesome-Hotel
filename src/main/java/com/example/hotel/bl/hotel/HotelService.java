package com.example.hotel.bl.hotel;

import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;

import java.util.List;

public interface HotelService {

    /**
     * 添加酒店
     *
     * @param hotelForm
     * @throws
     */
    void addHotel(HotelForm hotelForm) throws ServiceException;

    /**
     * 删除酒店
     *
     * @param hotelId
     * @throws
     */
    void deleteHotel(Integer hotelId);

    /**
     * 预订酒店修改剩余客房信息
     *
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    void updateRoomInfo(Integer hotelId, String roomType, Integer rooms);

    /**
     * 列表获取酒店信息
     *
     * @return
     */
    List<HotelVO> retrieveHotels();

    /**
     * 获取某家酒店详细信息
     *
     * @param hotelId
     * @return
     */
    HotelVO retrieveHotelDetails(Integer hotelId);

    /**
     * 查看酒店剩余某种房间数量
     *
     * @param hotelId
     * @param roomType
     * @return
     */
    int getRoomCurNum(Integer hotelId, String roomType);

    HotelVO retrieveAvailableHotelDetails(Integer hotelId, String beginTime, String endTime);

    List<RoomVO> checkRoom(List<RoomVO> rooms, String beginTime,String endTime);

    ResponseVO addLike(LikeVO likeVO);

    ResponseVO removeLike(Integer userId, Integer hotelId);

    boolean getLike(Integer userId, Integer hotelId);


    ResponseVO addCollection(CollectionVO collectionVO);

    // 根据用户id获取收藏酒店的id
    List<Integer> getCollections(int userId);

    ResponseVO addQuestion(QuestionVO questionVO);

    ResponseVO annualQuestion(Integer questionId);

    void updateHotelPicture(Integer hotelId, String url);

}
