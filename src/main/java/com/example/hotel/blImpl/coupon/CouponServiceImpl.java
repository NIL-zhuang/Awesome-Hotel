package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.coupon.*;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CouponServiceImpl implements CouponService {


    private final TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;
    private final TimeCouponStrategyImpl timeCouponStrategy;
    private final BirthdayCouponStrategyImpl birthdayCouponStrategy;
    private final ManyRoomCouponStrategyImpl manyRoomCouponStrategy;
    private final CorporateCouponStrategyImpl corporateCouponStrategy;

    private final CouponMapper couponMapper;

    private static List<CouponMatchStrategy> strategyList = new ArrayList<>();

    @Autowired
    public CouponServiceImpl(TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy,
                             TimeCouponStrategyImpl timeCouponStrategy,
                             BirthdayCouponStrategyImpl birthdayCouponStrategy,
                             ManyRoomCouponStrategyImpl manyRoomCouponStrategy, CorporateCouponStrategyImpl corporateCouponStrategy, CouponMapper couponMapper) {
        this.birthdayCouponStrategy = birthdayCouponStrategy;
        this.manyRoomCouponStrategy = manyRoomCouponStrategy;
        this.corporateCouponStrategy = corporateCouponStrategy;
        this.couponMapper = couponMapper;
        this.targetMoneyCouponStrategy = targetMoneyCouponStrategy;
        this.timeCouponStrategy = timeCouponStrategy;
        strategyList.add(targetMoneyCouponStrategy);
        strategyList.add(timeCouponStrategy);
        strategyList.add(birthdayCouponStrategy);
        strategyList.add(manyRoomCouponStrategy);
    }


    @Override
    public List<Coupon> getMatchOrderCoupon(OrderVO orderVO) {
        List<Coupon> hotelCoupons = getHotelAllCoupon(orderVO.getHotelId());
        List<Coupon> availAbleCoupons = new ArrayList<>();

        for (Coupon hotelCoupon : hotelCoupons) {
            for (CouponMatchStrategy strategy : strategyList) {
                if (strategy.isMatch(orderVO, hotelCoupon)) {
                    availAbleCoupons.add(hotelCoupon);
                }
            }
        }
        return availAbleCoupons;
    }

    @Override
    public List<Coupon> getHotelAllCoupon(Integer hotelId) {
        return couponMapper.selectByHotelId(hotelId);
    }

    @Override
    public CouponVO addHotelTargetMoneyCoupon(HotelTargetMoneyCouponVO couponVO) {
        Coupon coupon = iniCoupon(couponVO);
        coupon.setTargetMoney(couponVO.getTargetMoney());
        coupon.setDiscountMoney(couponVO.getDiscountMoney());
        couponVO.setId(addCoupon(coupon));
        return couponVO;
    }

    @Override
    public CouponVO addBirthdayCouponVO(BirthdayCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setDiscount(couponVO.getDiscountMoney());
        couponVO.setId(addCoupon(coupon));
        return couponVO;
    }

    @Override
    public CouponVO addManyRoomCoupon(ManyRoomCouponVO couponVO) {
        Coupon coupon = iniCoupon(couponVO);
        coupon.setTargetRoomNum(couponVO.getTargetRoomNum());
        coupon.setDiscount(couponVO.getDiscount());
        couponVO.setId(addCoupon(coupon));
        return couponVO;
    }

    @Override
    public CouponVO addTimeCouponVO(TimeCouponVO couponVO) {
        Coupon coupon = iniCoupon(couponVO);
        coupon.setStartTime(couponVO.getStartTime());
        coupon.setEndTime(couponVO.getEndTime());
        couponVO.setId(addCoupon(coupon));
        return couponVO;
    }

    @Override
    public CouponVO addCorporateCouponVO(CorporateCouponVO couponVO) {
        Coupon coupon = iniCoupon(couponVO);
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setCorporateName(couponVO.getCorporateName());
        couponVO.setId(addCoupon(coupon));
        return couponVO;
    }

    @Override
    public void annulCoupon(Integer couponId) {
        couponMapper.annualCoupon(couponId);
    }

    private Coupon iniCoupon(CouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setStatus(1);
        return coupon;
    }

    private int addCoupon(Coupon coupon) {
        return couponMapper.insertCoupon(coupon);
    }
}
