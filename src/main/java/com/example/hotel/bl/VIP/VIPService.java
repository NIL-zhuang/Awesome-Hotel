package com.example.hotel.bl.VIP;

import com.example.hotel.vo.ResponseVO;

public interface VIPService {

    ResponseVO registerAsClientVIP(Integer userId, String Birthday);

    ResponseVO freezeClientVIP(Integer userId);

    ResponseVO restoreClientVIP(Integer userId);

    ResponseVO getVIPbyUserId(Integer userId);

    ResponseVO addVIPClientConsumption(Integer userId, Integer amount);

    ResponseVO registerAsCorpVIP(String corporationName);

    ResponseVO freezeCorpVIP(String corporationName);

    ResponseVO restoreCorpVIP(String corporationName);

    ResponseVO getVIPbyCorpName(String corporationName);

    ResponseVO addVIPCorpConsumption(String corporationName, Integer amount);
    
    boolean VIPCorpCheck(String corporationName);

    ResponseVO getAllVIPClient();

    ResponseVO getAllVIPCorp();



}
