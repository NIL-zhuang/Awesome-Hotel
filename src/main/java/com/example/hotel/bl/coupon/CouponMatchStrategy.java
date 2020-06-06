package com.example.hotel.bl.coupon;

import com.example.hotel.po.Coupon;

import com.example.hotel.vo.OrderVO;

public interface CouponMatchStrategy {

    int WEBSITE = 0;

    boolean isMatch(OrderVO orderVO, Coupon coupon);

}
