package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.coupon.*;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Service
public class CouponServiceImpl implements CouponService {


    @Autowired
    private final CouponMapper couponMapper;

    @Autowired
    private HotelService hotelService;

    private static final List<CouponMatchStrategy> strategyList = new ArrayList<>();

    @Autowired
    public CouponServiceImpl(TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy,
                             TimeCouponStrategyImpl timeCouponStrategy,
                             BirthdayCouponStrategyImpl birthdayCouponStrategy,
                             ManyRoomCouponStrategyImpl manyRoomCouponStrategy,
                             CorporateCouponStrategyImpl corporateCouponStrategy,
                             BizRegionCouponStrategyImpl bizRegionCouponStrategy,
                             CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
        strategyList.add(targetMoneyCouponStrategy);
        strategyList.add(timeCouponStrategy);
        strategyList.add(birthdayCouponStrategy);
        strategyList.add(manyRoomCouponStrategy);
        strategyList.add(bizRegionCouponStrategy);
        strategyList.add(corporateCouponStrategy);
    }


    @Override
    public List<Coupon> getMatchOrderCoupon(OrderVO orderVO) {
        List<Coupon> hotelCoupons = getHotelAllCoupon(orderVO.getHotelId());
        List<Coupon> webCoupons = getWebsiteCoupon();
        hotelCoupons.addAll(webCoupons);
        List<Coupon> availAbleCoupons = new ArrayList<>();
        for (CouponMatchStrategy strategy : strategyList) {
            for (Coupon hotelCoupon : hotelCoupons) {
                if (strategy.isMatch(orderVO, hotelCoupon)) {
                    availAbleCoupons.add(hotelCoupon);
                }
            }
        }
        Set<Coupon> couponSet = new LinkedHashSet<>(availAbleCoupons);
        availAbleCoupons.clear();
        availAbleCoupons.addAll(couponSet);
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
        Coupon coupon = iniCoupon(couponVO);
        coupon.setDiscount(couponVO.getDiscount());
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
        coupon.setStartTime(LocalDate.parse(couponVO.getStartTime()));
        coupon.setEndTime(LocalDate.parse(couponVO.getEndTime()));
        coupon.setDiscount(couponVO.getDiscount());
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
    public CouponVO addBizRegionCouponVO(BizRegionCouponVO couponVO) {
        Coupon coupon = iniCoupon(couponVO);
        coupon.setVipLevel(couponVO.getVipLevel());
        coupon.setBizRegion(couponVO.getRegion());
        coupon.setDiscount(couponVO.getDiscount());
        couponVO.setId(addCoupon(coupon));
        return couponVO;
    }

    @Override
    public List<Coupon> getWebsiteCoupon() {
        return couponMapper.getWebCoupon();
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
        coupon.setSrcId(couponVO.getSrcId());
        coupon.setStatus(1);
        return coupon;
    }

    private int addCoupon(Coupon coupon) {
        return couponMapper.insertCoupon(coupon);
    }

//    private List<Coupon> getHotelBizRegionCoupon(Integer hotelId) {
//        String bizRegion = hotelService.retrieveHotelDetails(hotelId).getBizRegion();
//        return couponMapper.getBizRegionCoupon(bizRegion);
//    }
}
