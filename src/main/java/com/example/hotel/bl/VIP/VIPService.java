package com.example.hotel.bl.VIP;

import com.example.hotel.vo.ResponseVO;

public interface VIPService {

    ResponseVO registerAsClientVIP(Integer userId, String Birthday);

    ResponseVO freezeClientVIP(Integer userId);

    ResponseVO restoreClientVIP(Integer userId);

    ResponseVO getVIPbyUserId(Integer userId);

    ResponseVO addVIPClientConsumption(Integer userId, Integer amount);

    ResponseVO clientLevelUp(Integer userId);

    ResponseVO registerAsCorpVIP(String corporationName);

    ResponseVO freezeCorpVIP(String corporationName);

    ResponseVO restoreCorpVIP(String corporationName);

    ResponseVO getVIPbyCorpName(String corporationName);

    ResponseVO addVIPCorpConsumption(String corporationName, Integer amount);

    ResponseVO corpLevelUp(String corporationName);
    
    boolean VIPCorpCheck(String corporationName);

    ResponseVO getAllVIPClient();

    ResponseVO getAllVIPCorp();



}
