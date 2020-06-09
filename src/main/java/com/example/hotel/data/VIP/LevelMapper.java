package com.example.hotel.data.VIP;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LevelMapper {

    int formulateVIPLevel(@Param("level")Integer level, @Param("requestConsumption")Integer requestConsumption);

    int changeLevelRequest(@Param("level")Integer level, @Param("requestConsumption")Integer requestConsumption);

    Integer getTheRequestOfLevel(@Param("level")Integer level);

}
