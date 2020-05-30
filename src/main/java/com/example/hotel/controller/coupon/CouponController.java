package com.example.hotel.controller.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.coupon.*;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/hotelTargetMoney")
    public ResponseVO addHotelTargetMoneyCoupon(@RequestBody HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO) {
        CouponVO couponVO = couponService.addHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/birthday")
    public ResponseVO addBirthdayCoupon(@RequestBody BirthdayCouponVO birthdayCouponVO) {
        CouponVO couponVO = couponService.addBirthdayCouponVO(birthdayCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/manyRoom")
    public ResponseVO addManyRoomCoupon(@RequestBody ManyRoomCouponVO manyRoomCouponVO) {
        CouponVO couponVO = couponService.addManyRoomCoupon(manyRoomCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/time")
    public ResponseVO addTimeCoupon(@RequestBody TimeCouponVO timeCouponVO) {
        CouponVO couponVO = couponService.addTimeCouponVO(timeCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/corporate")
    public ResponseVO addCorporateCoupon(@RequestBody CorporateCouponVO corporateCouponVO) {
        CouponVO couponVO = couponService.addCorporateCouponVO(corporateCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/hotelAllCoupons")
    public ResponseVO getHotelAllCoupons(@RequestParam Integer hotelId) {
        return ResponseVO.buildSuccess(couponService.getHotelAllCoupon(hotelId));
    }

    @PostMapping("/annalCoupons")
    public ResponseVO annulCoupon(@RequestParam("id") Integer id) {
        couponService.annulCoupon(id);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/orderMatchCoupons")
    public ResponseVO getOrderMatchCoupons(@RequestParam Integer userId,
                                           @RequestParam Integer hotelId,
                                           @RequestParam Double orderPrice,
                                           @RequestParam Integer roomNum,
                                           @RequestParam String checkIn,
                                           @RequestParam String checkOut) {
        OrderVO requestOrderVO = new OrderVO();
        requestOrderVO.setUserId(userId);
        requestOrderVO.setHotelId(hotelId);
        requestOrderVO.setPrice(orderPrice);
        requestOrderVO.setRoomNum(roomNum);
        requestOrderVO.setCheckInDate(checkIn);
        requestOrderVO.setCheckOutDate(checkOut);
        return ResponseVO.buildSuccess(couponService.getMatchOrderCoupon(requestOrderVO));
    }


}
