package com.example.hotel.data.VIP;

import com.example.hotel.po.ClientVIP;
import com.example.hotel.po.CorpVIP;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VIPMapper {

    void registerAsClientVIP(ClientVIP vip);

    ClientVIP getVIPbyUserId(@Param("userId") Integer userId);

    void addVIPClientConsumption(@Param("userId")Integer userId, @Param("amount") Integer amount);

    void clientLevelUp(@Param("userId")Integer userId);

    void registerAsCorpVIP(CorpVIP corpVIP);

    CorpVIP getVIPbyCorpName(@Param("corporationName")String corporationName);

    void addVIPCorpConsumption(@Param("corporationName")String corporationName, @Param("amount") Integer amount);

    void corpLevelUp(@Param("corporationName")String corporationName);

}
