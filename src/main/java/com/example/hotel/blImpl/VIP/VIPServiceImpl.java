package com.example.hotel.blImpl.VIP;

import com.example.hotel.bl.VIP.VIPService;
import com.example.hotel.data.VIP.VIPMapper;
import com.example.hotel.po.ClientVIP;
import com.example.hotel.vo.ResponseVO;
import org.springframework.stereotype.Service;

@Service
public class VIPServiceImpl implements VIPService {

    private final static String VIP_REGISTERED = "当前用户已经是VIP";

    private VIPMapper vipMapper;

    public ResponseVO registerAsClientVIP(Integer userId, String birthday){
        try{
//            vipMapper.registerAsClientVIP(userId, birthday);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(VIP_REGISTERED);
        }
        return ResponseVO.buildSuccess();
    }

    public ClientVIP getVIPbyUserId(Integer userId){
        return null;
    }

    public ResponseVO registerAsCorporationVIP(String corporationName){
        return null;
    }

    public ResponseVO VIPCorporationCheck(String corporationName){
        return null;
    }

}
