package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HotelMapper {

    int insertHotel(Hotel hotel);

    int deleteHotel(Integer id);

    List<Hotel> selectAllHotel();

    Hotel selectById(@Param("id") Integer id);

    int updatePicture(@Param("id") Integer id, @Param("url") String url);

    int updateHotelPoints(@Param("id") Integer id,
                          @Param("commentTime") Integer commentTime,
                          @Param("points") double points,
                          @Param("sanitation") double sanitation,
                          @Param("environment") double environment,
                          @Param("service") double service,
                          @Param("equipment") double equipment);
}
