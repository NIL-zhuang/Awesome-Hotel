const coupon = {
    state: {
        couponDetailVisible: false,
        currentCouponInfo: {

        }
    },
    mutations: {
        set_couponDetailVisible: function (state, visible) {
            state.couponDetailVisible = visible
        },
        set_currentCouponInfo: function (state, data) {
            state.currentCouponInfo = data
        }
    }
}

export default coupon