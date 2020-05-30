package com.example.hotel.vo.coupon;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/30 16:54
 */
public class CorporateCouponVO extends CouponVO {
    private String corporateName;
    private double discount;

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
