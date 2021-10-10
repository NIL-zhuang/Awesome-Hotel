package com.example.hotel.vo.coupon;


/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/13 22:18
 */
public class BizRegionCouponVO extends CouponVO {
    private String region;
    private Integer vipLevel;
    private double discount;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
