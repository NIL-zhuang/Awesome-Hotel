package com.example.hotel.vo.coupon;

import com.example.hotel.vo.coupon.CouponVO;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/4/29 9:55
 */
public class BirthdayCouponVO extends CouponVO {

    private double discount;

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(double discount) {
        this.discount= discount;
    }
}
