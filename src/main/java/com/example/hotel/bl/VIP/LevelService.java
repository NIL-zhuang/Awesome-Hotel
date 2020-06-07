package com.example.hotel.bl.VIP;

import com.example.hotel.vo.ResponseVO;

public interface LevelService {

    ResponseVO formulateVIPLevel(Integer level, Integer requestConsumption);

    ResponseVO getTheRequestOfLevel(Integer level);

}
