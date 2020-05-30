package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

@Service
public class TargetMoneyCouponStrategyImpl implements CouponMatchStrategy {


    /**
     * 判断某个订单是否满足某种满减金额优惠策略
     *
     * @param orderVO 订单
     * @param coupon  优惠券
     * @return 是否满足满减策略
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        try {
            return coupon.getCouponType() == 3 &&
                    coupon.getStatus() == 1 &&
                    orderVO.getPrice() >= coupon.getTargetMoney() &&
                    (coupon.getHotelId() == -1 || coupon.getHotelId().equals(orderVO.getHotelId()));
        } catch (Exception e) {
            return false;
        }
    }
}
