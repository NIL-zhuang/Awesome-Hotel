package com.example.hotel.bl.coupon;

import com.example.hotel.po.Coupon;

import com.example.hotel.vo.OrderVO;

public interface CouponMatchStrategy {

    int WEBSITE = 0;

    int BIRTHDAY = 1;
    int MULTIPLE_ROOM = 2;
    int TARGET_MONEY = 3;
    int TIME_LIMITED = 4;

    boolean isMatch(OrderVO orderVO, Coupon coupon);

}
