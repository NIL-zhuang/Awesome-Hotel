package com.example.hotel.bl.user;

import com.example.hotel.vo.CollectionVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/15 21:48
 */
public interface CollectionService {
    public ResponseVO addCollection(CollectionVO collectionVO);

    public ResponseVO annulCollection(Integer collectionId);

    public List<CollectionVO> getUserCollection(Integer userId);

    public Integer getHotelCollection(Integer hotelId);

    public Boolean userCollection(Integer userId, Integer hotelId);


}
