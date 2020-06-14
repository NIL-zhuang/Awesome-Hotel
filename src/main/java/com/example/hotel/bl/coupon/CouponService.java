package com.example.hotel.bl.coupon;

import com.example.hotel.po.Coupon;
import com.example.hotel.vo.coupon.*;
import com.example.hotel.vo.OrderVO;

import java.util.List;

public interface CouponService {
    /**
     * 返回某一订单可用的优惠策略列表
     */
    List<Coupon> getMatchOrderCoupon(OrderVO orderVO);

    /**
     * 查看某个酒店提供的所有优惠策略（不包括失效的）
     */
    List<Coupon> getHotelAllCoupon(Integer hotelId);

    /**
     * 添加酒店满减优惠策略
     */
    CouponVO addHotelTargetMoneyCoupon(HotelTargetMoneyCouponVO couponVO);

    CouponVO addBirthdayCouponVO(BirthdayCouponVO couponVO);

    CouponVO addManyRoomCoupon(ManyRoomCouponVO couponVO);

    CouponVO addTimeCouponVO(TimeCouponVO couponVO);

    CouponVO addCorporateCouponVO(CorporateCouponVO couponVO);

    CouponVO addBizRegionCouponVO(BizRegionCouponVO couponVO);

    List<Coupon> getWebsiteCoupon();

    void annulCoupon(Integer couponId);
}
