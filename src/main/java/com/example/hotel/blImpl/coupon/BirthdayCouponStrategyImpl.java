package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/4/29 9:13
 */
@Service
public class BirthdayCouponStrategyImpl implements CouponMatchStrategy {

    private AccountMapper accountMapper;

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        try {
            User user = accountMapper.getAccountById(orderVO.getUserId());
            // user doesn't have birthday entity
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
