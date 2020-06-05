package com.example.hotel.data.VIP;

import com.example.hotel.po.ClientVIP;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VIPMapper {

    void registerAsClientVIP(ClientVIP vip);

}
