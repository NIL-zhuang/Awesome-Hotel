package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.VIPType;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/4/29 9:13
 */
@Service
public class BirthdayCouponStrategyImpl implements CouponMatchStrategy {

    @Autowired
    private AccountMapper accountMapper;

    private final static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        // 下单当天生日
        try {
            User user = accountMapper.getAccountById(orderVO.getUserId());
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            return coupon.getCouponType() == BIRTHDAY &&
                    curdate.equals(user.getBirthday()) &&
                    user.getVipType() == VIPType.Client &&
                    coupon.getSrcId().equals(orderVO.getHotelId());
        } catch (Exception e) {
            return false;
        }
    }
}
