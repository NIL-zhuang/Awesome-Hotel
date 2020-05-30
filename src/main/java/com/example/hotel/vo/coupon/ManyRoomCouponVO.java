package com.example.hotel.vo.coupon;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/30 14:10
 */
public class ManyRoomCouponVO extends CouponVO {
    private Integer targetRoomNum;
    private double discount;

    public Integer getTargetRoomNum() {
        return targetRoomNum;
    }

    public void setTargetRoomNum(Integer targetRoomNum) {
        this.targetRoomNum = targetRoomNum;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
