package com.example.hotel.data.VIP;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LevelMapper {

    void formulateVIPLevel(@Param("level")Integer level, @Param("type")String type,
                          @Param("requestConsumption")Integer requestConsumption, @Param("reduction")double reduction);

    void changeLevel(@Param("level")Integer level, @Param("type")String type,
                    @Param("requestConsumption")Integer requestConsumption, @Param("reduction")double reduction);

    Integer getTheRequestOfLevel(@Param("level")Integer level, @Param("type")String type);

    double getTheReduOfLevel(@Param("level")Integer level, @Param("type")String type);

}
