package com.example.hotel.bl.VIP;

import com.example.hotel.po.ClientVIP;
import com.example.hotel.vo.ResponseVO;

public interface VIPService {

    ResponseVO registerAsClientVIP(Integer userId, String Birthday);

    ClientVIP getVIPbyUserId(Integer userId);

    ResponseVO registerAsCorporationVIP(String corporationName);
    
    ResponseVO VIPCorporationCheck(String corporationName);

}
