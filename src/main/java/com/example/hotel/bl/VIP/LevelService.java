package com.example.hotel.bl.VIP;

import com.example.hotel.vo.ResponseVO;

public interface LevelService {

    ResponseVO formulateVIPLevel(Integer level, String type, Integer requestConsumption, double reduction);

    ResponseVO getTheRequestOfLevel(Integer level, String type);

}
