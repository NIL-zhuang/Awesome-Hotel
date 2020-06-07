package com.example.hotel.data.VIP;

import com.example.hotel.po.ClientVIP;
import com.example.hotel.po.CorpVIP;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface VIPMapper {

    void registerAsClientVIP(ClientVIP vip);

    void freezeClientVIP(@Param("userId") Integer userId);

    void restoreClientVIP(@Param("userId") Integer userId);

    ClientVIP getVIPbyUserId(@Param("userId") Integer userId);

    void addVIPClientConsumption(@Param("userId")Integer userId, @Param("amount") Integer amount);

    void clientLevelUp(@Param("userId")Integer userId);

    void registerAsCorpVIP(CorpVIP corpVIP);

    void freezeCorpVIP(@Param("corporationName") String corporationName);

    void restoreCorpVIP(@Param("corporationName") String corporationName);

    CorpVIP getVIPbyCorpName(@Param("corporationName")String corporationName);

    void addVIPCorpConsumption(@Param("corporationName")String corporationName, @Param("amount") Integer amount);

    void corpLevelUp(@Param("corporationName")String corporationName);

    List<ClientVIP> getAllVIPClient();

    List<CorpVIP> getAllVIPCorp();

}
