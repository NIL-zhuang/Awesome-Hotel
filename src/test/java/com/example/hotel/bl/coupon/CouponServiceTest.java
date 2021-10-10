package com.example.hotel.bl.coupon;

import com.example.hotel.po.Coupon;
import com.example.hotel.vo.coupon.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    private final HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO = new HotelTargetMoneyCouponVO() {{
        setDescription("hotelTargetMoneyCoupon");
        setDiscountMoney(100);
        setName("TargetMoney");
        setSrcId(5);
        setStatus(1);
        setTargetMoney(200);
        setType(3);
    }};

    private final BirthdayCouponVO birthdayCouponVO = new BirthdayCouponVO() {{
        setDescription("Birthday Coupon");
        setDiscount(0.8);
        setName("Birthday");
        setSrcId(2);
        setStatus(1);
        setType(1);
    }};

    private final ManyRoomCouponVO manyRoomCouponVO = new ManyRoomCouponVO() {{
        setDescription("Many Room");
        setDiscount(0.9);
        setName("Many Room");
        setSrcId(2);
        setStatus(1);
        setTargetRoomNum(3);
        setType(2);
    }};

    private final TimeCouponVO timeCouponVO = new TimeCouponVO() {{
        setDescription("Time");
        setName("Time");
        setDiscount(0.9);
        setStartTime("2020-06-19");
        setEndTime("2020-06-21");
        setSrcId(2);
        setStatus(1);
        setType(4);
    }};

    private final BizRegionCouponVO bizRegionCouponVO = new BizRegionCouponVO() {{
        setDescription("Biz Region");
        setDiscount(0.8);
        setName("Biz Region");
        setSrcId(0);
        setStatus(1);
        setRegion("XiDan");
        setVipLevel(1);
        setType(0);
    }};

    private final CorporateCouponVO corporateCouponVO = new CorporateCouponVO() {{
        setCorporateName("Nanjing University");
        setName("Corporate");
        setDescription("Corporate");
        setDiscount(0.8);
        setSrcId(0);
        setStatus(1);
        setType(0);
    }};

    @Test
    @Transactional
    public void getMatchOrderCoupon() {
    }

    @Test
    @Transactional
    public void getHotelAllCoupon() {
        addCoupon();
        List<Coupon> couponList = couponService.getHotelAllCoupon(2);
        Assert.assertEquals(couponList.size(), 4);
    }

    @Test
    @Transactional
    public void addHotelTargetMoneyCoupon() {
        couponService.addHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);
        List<Coupon> couponList = couponService.getHotelAllCoupon(5);
        Assert.assertEquals(couponList.size(), 1);
    }

    @Test
    @Transactional
    public void addBirthdayCouponVO() {
        couponService.addBirthdayCouponVO(birthdayCouponVO);
        List<Coupon> couponList = couponService.getHotelAllCoupon(2);
        Assert.assertEquals(couponList.size(), 2);
    }

    @Test
    @Transactional
    public void addManyRoomCoupon() {
        couponService.addManyRoomCoupon(manyRoomCouponVO);
        List<Coupon> couponList = couponService.getHotelAllCoupon(2);
        Assert.assertEquals(couponList.size(), 2);
    }

    @Test
    @Transactional
    public void addTimeCouponVO() {
        couponService.addTimeCouponVO(timeCouponVO);
        List<Coupon> couponList = couponService.getHotelAllCoupon(2);
        Assert.assertEquals(couponList.size(), 2);
    }

    @Test
    @Transactional
    public void addCorporateCouponVO() {
        couponService.addCorporateCouponVO(corporateCouponVO);
    }

    @Test
    @Transactional
    public void annulCoupon() {
        couponService.addHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);
        Coupon couponVO = couponService.getHotelAllCoupon(5).get(0);
        couponService.annulCoupon(couponVO.getId());
        List<Coupon> coupons = couponService.getHotelAllCoupon(5);
        Assert.assertEquals(coupons.size(), 0);
    }

    @Test
    @Transactional
    public void addBizRegionCouponVO() {
        couponService.addBizRegionCouponVO(bizRegionCouponVO);
    }

    @Test
    @Transactional
    public void getWebsiteCoupon() {
        addCoupon();
        List<Coupon> couponList = couponService.getWebsiteCoupon();
        Assert.assertEquals(couponList.size(), 2);
    }

    private void addCoupon() {
        couponService.addBizRegionCouponVO(bizRegionCouponVO);
        couponService.addCorporateCouponVO(corporateCouponVO);
        couponService.addTimeCouponVO(timeCouponVO);
        couponService.addManyRoomCoupon(manyRoomCouponVO);
        couponService.addBirthdayCouponVO(birthdayCouponVO);
        couponService.addHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);
    }
}