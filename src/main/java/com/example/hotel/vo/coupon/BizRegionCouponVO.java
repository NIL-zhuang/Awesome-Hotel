package com.example.hotel.vo.coupon;

import com.example.hotel.enums.BizRegion;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/13 22:18
 */
public class BizRegionCouponVO extends CouponVO {
    private BizRegion region;
    private Integer vipLevel;
    private double discount;

    public String getRegion() {
        return region.toString();
    }

    public void setRegion(BizRegion region) {
        this.region = region;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }
}
