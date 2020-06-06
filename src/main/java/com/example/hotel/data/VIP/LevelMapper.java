package com.example.hotel.data.VIP;

import org.apache.ibatis.annotations.Param;

public interface LevelMapper {

    void formulateVIPLevel(@Param("level")Integer level, @Param("requestConsumption")Integer requestConsumption);

    void changeLevelRequest(@Param("level")Integer level, @Param("requestConsumption")Integer requestConsumption);

    int getTheRequestOfLevel(@Param("level")Integer level);

}
