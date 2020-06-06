package com.example.hotel.blImpl.VIP;

import com.example.hotel.bl.VIP.VIPService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.VIP.VIPMapper;
import com.example.hotel.po.ClientVIP;
import com.example.hotel.po.CorpVIP;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VIPServiceImpl implements VIPService {

    private final static String CLI_REGISTERED = "当前用户已经是VIP";
    private final static String CORP_REGISTERED = "该公司已经注册为VIP";

    @Autowired
    private VIPMapper vipMapper;

    @Autowired
    private AccountService accountService;

    @Override
    public ResponseVO registerAsClientVIP(Integer userId, String birthday){
        try{

            vipMapper.registerAsClientVIP(new ClientVIP(userId));

            accountService.registerAsVIP(userId);

            accountService.updateBirthday(userId, birthday);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(CLI_REGISTERED);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ClientVIP getVIPbyUserId(Integer userId){
        return vipMapper.getVIPbyUserId(userId);
    }

    @Override
    public ResponseVO registerAsCorpVIP(String corporationName){
        try {
            vipMapper.registerAsCorpVIP(new CorpVIP(corporationName));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(CORP_REGISTERED);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public CorpVIP getVIPbyCorpName(String corporationName){
        return vipMapper.getVIPbyCorpName(corporationName);
    }

    @Override
    public boolean VIPCorpCheck(String corporationName){
        return vipMapper.getVIPbyCorpName(corporationName) != null;
    }

}
