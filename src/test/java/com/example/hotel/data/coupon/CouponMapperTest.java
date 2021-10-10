package com.example.hotel.data.coupon;

import com.example.hotel.po.Coupon;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/6/7 10:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponMapperTest {

    @Autowired
    private CouponMapper couponMapper;

    private final Coupon coupon = new Coupon() {{
        setCorporateName("nju");
        setDiscount(0.8);
        setSrcId(0);
        setTargetRoomNum(3);
        setCouponName("TEST");
        setCouponType(0);
        setDescription("test coupon");
        setEndTime(LocalDate.now());
        setStartTime(LocalDate.parse("2020-06-01"));
        setBizRegion("nanjing");
        setCorporateName("NJU");
        setVipLevel(7);
        setStatus(1);
    }};

    @Test
    @Transactional
    public void insertCoupon() {
        int val = couponMapper.insertCoupon(coupon);
        Coupon coupon = couponMapper.getWebCoupon().get(0);
        Assert.assertThat(coupon.getCorporateName(), is("NJU"));
        Assert.assertThat(val, is(1));
    }

    @Test
    @Transactional
    public void selectByHotelId() {
        List<Coupon> couponList = couponMapper.selectByHotelId(2);
        Assert.assertThat(couponList.size(), is(1));
        Assert.assertThat(couponList.get(0).getCouponName(), is("满减优惠券"));

    }

    @Test
    @Transactional
    public void annualCoupon() {
        couponMapper.annualCoupon(2);
        Assert.assertThat(couponMapper.selectByHotelId(2).size(), is(0));
    }

    @Test
    @Transactional
    public void getWebCoupon() {
        int val = couponMapper.insertCoupon(coupon);
        Assert.assertEquals(val, 1);
        List<Coupon> couponList = couponMapper.getWebCoupon();
        Assert.assertEquals(couponList.size(), 1);
        Assert.assertThat(couponList.get(0).getSrcId(), is(0));
    }

    @Test
    @Transactional
    public void getBizRegionCoupon() {
        int val = couponMapper.insertCoupon(coupon);
        Assert.assertEquals(val, 1);
        List<Coupon> couponList = couponMapper.getBizRegionCoupon("nanjing");
        Assert.assertEquals(couponList.size(), 1);
        Assert.assertThat(couponList.get(0).getSrcId(), is(0));
    }
}