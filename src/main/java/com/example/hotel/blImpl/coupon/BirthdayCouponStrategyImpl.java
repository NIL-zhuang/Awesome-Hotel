package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/4/29 9:13
 */
@Service
public class BirthdayCouponStrategyImpl implements CouponMatchStrategy {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        try {
            User user = accountMapper.getAccountById(orderVO.getUserId());
            Date in = TimeFormatHelper.string2Date(orderVO.getCheckInDate());
            Date out = TimeFormatHelper.string2Date(orderVO.getCheckOutDate());
            Date birth = TimeFormatHelper.string2Date(user.getBirthday());
            assert birth != null;
            return !birth.before(in) &&
                    !birth.after(out) &&
                    (coupon.getHotelId() == -1 || coupon.getHotelId().equals(orderVO.getHotelId())) &&
                    coupon.getStatus() == 1;
        } catch (Exception e) {
            return false;
        }
    }
}
