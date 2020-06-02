package com.example.hotel.bl.VIP;

import com.example.hotel.po.VIP;
import com.example.hotel.vo.ResponseVO;

public interface VIPService {

    VIP getVIPbyUserId(Integer userId);
    
    ResponseVO VIPCorporationCheck(String corporationName);

}
