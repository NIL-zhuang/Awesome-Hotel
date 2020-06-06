package com.example.hotel.bl.VIP;

import com.example.hotel.po.ClientVIP;
import com.example.hotel.po.CorpVIP;
import com.example.hotel.vo.ResponseVO;

public interface VIPService {

    ResponseVO registerAsClientVIP(Integer userId, String Birthday);

    ClientVIP getVIPbyUserId(Integer userId);

    ResponseVO registerAsCorpVIP(String corporationName);

    CorpVIP getVIPbyCorpName(String corporationName);
    
    boolean VIPCorpCheck(String corporationName);

}
