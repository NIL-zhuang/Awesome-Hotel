package com.example.hotel.bl.hotel;

import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.SearchBodyVO;

import java.util.List;

public interface HotelSearchService {

    List<HotelVO> searchHotel(SearchBodyVO searchBody);

}
