package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.VIP.VIPMapper;
import com.example.hotel.enums.VIPType;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/16 9:49
 */
@Service
public class BizRegionCouponStrategyImpl implements CouponMatchStrategy {
    @Autowired
    private AccountService accountService;
    @Autowired
    private VIPMapper vipMapper;

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        UserVO userVO = accountService.getUserInfo(orderVO.getUserId());
        int level = vipMapper.getVIPbyUserId(orderVO.getUserId()).getLevel();
        return coupon.getStatus() == 1 &&
                level == coupon.getVipLevel() &&
                userVO.getVipType() != VIPType.Normal;
    }
}
