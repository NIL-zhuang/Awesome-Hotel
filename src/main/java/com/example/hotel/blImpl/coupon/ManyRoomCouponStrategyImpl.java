package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/4/29 9:19
 */
@Service
public class ManyRoomCouponStrategyImpl implements CouponMatchStrategy {

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        try {
            return coupon.getCouponType() == MULTIPLE_ROOM &&
                    (coupon.getSrcId() == WEBSITE || coupon.getSrcId().equals(orderVO.getHotelId())) &&
                    orderVO.getRoomNum() >= coupon.getTargetRoomNum() &&
                    coupon.getStatus() == 1;
        } catch (Exception e) {
            return false;
        }
    }
}
