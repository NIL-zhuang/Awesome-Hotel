package com.example.hotel.bl.user;

import com.example.hotel.vo.CollectionVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/15 21:48
 */
public interface CollectionService {
    ResponseVO addCollection(CollectionVO collectionVO);

    ResponseVO annulCollection(Integer collectionId);

    List<CollectionVO> getUserCollection(Integer userId);

    Integer getHotelCollection(Integer hotelId);

    Boolean userCollection(Integer userId, Integer hotelId);

    ResponseVO addCollectionByUserId(Integer hotelId, Integer userId);

    ResponseVO annulCollectionByUserId(Integer hotelId, Integer userId);


}
