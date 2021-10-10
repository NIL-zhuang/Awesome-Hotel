package com.example.hotel.blImpl.VIP;

import com.example.hotel.bl.VIP.LevelService;
import com.example.hotel.data.VIP.LevelMapper;
import com.example.hotel.vo.LevelVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelServiceImpl implements LevelService {

    private static final String NO_LEVEL = "该等级不存在";

    @Autowired
    private LevelMapper levelMapper;

    @Override
    public ResponseVO formulateVIPLevel(Integer level, String type, Integer requestConsumption, double reduction) {
        Integer request = levelMapper.getTheRequestOfLevel(level, type);
        if (request==null) { // 这里需要验证一下，我不十分确定 --crx //GLH验证成功
            levelMapper.formulateVIPLevel(level, type,requestConsumption, reduction);
        }else{
            levelMapper.changeLevel(level, type, requestConsumption, reduction);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO getTheRequestOfLevel(Integer level, String type) {
        int request;
        double reduction;
        try{
            request = levelMapper.getTheRequestOfLevel(level, type);
            reduction = levelMapper.getTheReduOfLevel(level, type);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(NO_LEVEL);
        }
        return ResponseVO.buildSuccess(new LevelVO(level, request, reduction));
    }
}
