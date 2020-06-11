package com.example.hotel.blImpl.VIP;

import com.example.hotel.bl.VIP.VIPService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.VIP.LevelMapper;
import com.example.hotel.data.VIP.VIPMapper;
import com.example.hotel.po.ClientVIP;
import com.example.hotel.po.CorpVIP;
import com.example.hotel.vo.ClientVIPVO;
import com.example.hotel.vo.CorpVIPVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VIPServiceImpl implements VIPService {

    private final static String CLI_REGISTERED = "当前用户已经是VIP";
    private final static String CLI_FAILURE = "当前用户不是VIP";
    private final static String CORP_REGISTERED = "该企业名已被占用";
    private final static String CORP_FAILURE = "该企业不是VIP";

    @Autowired
    private VIPMapper vipMapper;

    @Autowired
    private LevelMapper levelMapper;

    @Autowired
    private AccountService accountService;

    private void clientLevelUp(Integer userId) {
        try{
            vipMapper.clientLevelUp(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void corpLevelUp(String corporationName) {
        try{
            vipMapper.corpLevelUp(corporationName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

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
    public ResponseVO freezeClientVIP(Integer userId) {
        try {
            vipMapper.freezeClientVIP(userId);
            accountService.freezeVIP(userId);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(CLI_FAILURE);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO restoreClientVIP(Integer userId) {
        try {
            vipMapper.restoreClientVIP(userId);
            accountService.registerAsVIP(userId);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(CLI_FAILURE);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO getVIPbyUserId(Integer userId){
        try {
            ClientVIP clientVIP = vipMapper.getVIPbyUserId(userId);
            ClientVIPVO clientVIPVO = new ClientVIPVO();
            BeanUtils.copyProperties(clientVIP, clientVIPVO);
            return ResponseVO.buildSuccess(clientVIPVO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(CLI_FAILURE);
        }
    }

    @Override
    public ResponseVO addVIPClientConsumption(Integer userId, Integer amount) {
        try{
            vipMapper.addVIPClientConsumption(userId,amount);
            ClientVIP clientVIP = vipMapper.getVIPbyUserId(userId);
            int nextLevelConsum = levelMapper.getTheRequestOfLevel(clientVIP.getLevel()+1);
            if (clientVIP.getConsumption()>=nextLevelConsum){
                clientLevelUp(userId);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(CLI_FAILURE);
        }
        return ResponseVO.buildSuccess();
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
    public ResponseVO freezeCorpVIP(String corporationName) {
        try {
            vipMapper.freezeCorpVIP(corporationName);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(CORP_FAILURE);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO restoreCorpVIP(String corporationName) {
        try {
            vipMapper.restoreCorpVIP(corporationName);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(CORP_FAILURE);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO getVIPbyCorpName(String corporationName){
        try {
            CorpVIP corpVIP = vipMapper.getVIPbyCorpName(corporationName);
            CorpVIPVO corpVIPVO = new CorpVIPVO();
            BeanUtils.copyProperties(corpVIP,corpVIPVO);
            return ResponseVO.buildSuccess(corpVIPVO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(CORP_FAILURE);
        }
    }

    @Override
    public ResponseVO addVIPCorpConsumption(String corporationName, Integer amount) {
        try{
            vipMapper.addVIPCorpConsumption(corporationName, amount);
            CorpVIP corpVIP = vipMapper.getVIPbyCorpName(corporationName);
            int nextLevelConsum = levelMapper.getTheRequestOfLevel(corpVIP.getLevel()+1);
            if (corpVIP.getConsumption()>=nextLevelConsum){
                corpLevelUp(corporationName);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(CORP_FAILURE);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public boolean VIPCorpCheck(String corporationName){
        return vipMapper.getVIPbyCorpName(corporationName) != null;
    }

    @Override
    public ResponseVO getAllVIPClient() {
        return ResponseVO.buildSuccess(vipMapper.getAllVIPClient());
    }

    @Override
    public ResponseVO getAllVIPCorp() {
        return ResponseVO.buildSuccess(vipMapper.getAllVIPCorp());
    }
}
