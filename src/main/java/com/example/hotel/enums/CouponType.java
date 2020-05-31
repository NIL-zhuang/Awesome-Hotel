package com.example.hotel.enums;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/4/27 11:19
 */
public enum CouponType {
    Birthday("生日优惠"),
    ManyRoom("多间优惠"),
    MoneyOff("满减优惠"),
    TimeLimit("限时优惠"),
    Corporation("企业优惠");

    private String value;

    CouponType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
