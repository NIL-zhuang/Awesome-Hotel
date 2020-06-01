package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class TimeCouponStrategyImpl implements CouponMatchStrategy {


    /**
     * 判断某个订单是否满足某种限时优惠策略
     *
     * @param orderVO 订单
     * @return 是否匹配
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        try {
            LocalDate couponStartTime = coupon.getStartTime();
            LocalDate couponEndTime = coupon.getEndTime();
            LocalDate checkIn = LocalDate.parse(orderVO.getCheckInDate());
            return (coupon.getHotelId() == -1 || coupon.getHotelId().equals(orderVO.getHotelId())) && // 该酒店适用
                    coupon.getCouponType() == 4 &&  // 限时优惠
                    coupon.getStatus() == 1 &&  // 优惠券有效
                    checkIn.isBefore(couponEndTime) &&
                    checkIn.isAfter(couponStartTime); // 似乎没有用？
        } catch (Exception e) {
            return false;
        }
    }
}
