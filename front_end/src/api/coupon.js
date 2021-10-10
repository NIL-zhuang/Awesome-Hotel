import { axios } from '@/utils/request'
const api = {
    couponPre: '/api/coupon'
}
export function hotelBirthdayCouponAPI(data){
    return axios({
        url: `${api.couponPre}/birthday`,
        method: 'POST',
        data,
    })
}

export function TimeCouponAPI(data) {
    return axios({
        url: `${api.couponPre}/time`,
        method: 'POST',
        data,
    })
}

export function hotelManyRoomCouponAPI(data) {
    return axios({
        url: `${api.couponPre}/manyRoom`,
        method: 'POST',
        data,
    })
}

export function hotelTargetMoneyAPI(data) {
    return axios({
        url: `${api.couponPre}/hotelTargetMoney`,
        method: 'POST',
        data,
    })
}

export function CorporateCouponAPI(data) {
    return axios({
        url: `${api.couponPre}/corporation`,
        method: 'POST',
        data,
    })
}

export function BizRegionCouponAPI(data) {
    return axios({
        url: `${api.couponPre}/bizRegion`,
        method: 'POST',
        data,
    })
}

export function deleteCouponAPI(couponId) {
    return axios({
        url: `${api.couponPre}/annulCoupons`,
        method: 'POST',
        params: { id: couponId },
    })
}


export function hotelAllCouponsAPI(hotelId) {
    return axios({
        url: `${api.couponPre}/hotelAllCoupons`,
        method: 'GET',
        params: {
            hotelId: hotelId
        },
    })
}

export function getSiteCouponsAPI() {
    return axios({
        url: `${api.couponPre}/websiteCoupons`,
        method: 'GET',
    })
}

export function orderMatchCouponsAPI(params) {
    return axios({
        url: `${api.couponPre}/orderMatchCoupons`,
        method: 'GET',
        params,
    })
}
