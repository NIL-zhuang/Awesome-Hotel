package com.example.hotel.vo.coupon;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.VIPType;
import com.example.hotel.po.Coupon;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/13 22:18
 */
public class BizRegionCouponVO extends Coupon {
    private BizRegion region;
    private VIPType type;
    private Integer vipLevel;
    private double discount;

    public BizRegion getRegion() {
        return region;
    }

    public void setRegion(BizRegion region) {
        this.region = region;
    }

    public VIPType getType() {
        return type;
    }

    public void setType(VIPType type) {
        this.type = type;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    @Override
    public double getDiscount() {
        return discount;
    }

    @Override
    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
