import {
    addRoomAPI,
    deleteRoomAPI,
} from '../../api/hotelManager'
import {
    getHotelOrdersAPI,
    getHotelOrdersInMonthAPI,
} from "../../api/order";
import {
    hotelAllCouponsAPI,
    hotelTargetMoneyAPI,
    hotelBirthdayCouponAPI,
    TimeCouponAPI,
    hotelManyRoomCouponAPI,
    deleteCouponAPI,
} from '../../api/coupon'
import {
    updateHotelInfoAPI,
    getHotelByIdAPI
} from "../../api/hotel";
import {message} from 'ant-design-vue'
const hotelManager = {
    state: {
        orderList: [],
        orderInMonth: [],
        orderNumInMonth: [],
        orderValueInMonth: [],
        latestOrderList: [],
        addRoomParams: {
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
        },
        addRoomModalVisible: false,
        couponVisible: false,
        addCouponVisible: false,
        couponList: [],
        registerHotelMembershipModalVisible: false,
        hotelInfo: {},
    },
    mutations: {
        set_managerList: function (state, data) {
            state.manageHotelList = data
        },
        set_orderList: function (state, data) {
            state.orderList = data
        },
        set_orderInMonth: function (state, data) {
            state.orderInMonth = data
        },
        set_orderNumInMonth: function (state, data) {
            state.orderNumInMonth = data
        },
        set_orderValueInMonth: function (state, data){
            state.orderValueInMonth = data
        },
        set_addRoomModalVisible: function (state, data) {
            state.addRoomModalVisible = data
        },
        set_addRoomParams: function (state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            }
        },
        set_couponVisible: function (state, data) {
            state.couponVisible = data
        },
        set_activeHotelId: function (state, data) {
            state.activeHotelId = data
        },
        set_couponList: function (state, data) {
            state.couponList = data
        },
        set_addCouponVisible: function (state, data) {
            state.addCouponVisible = data
        },
        set_registerHotelMembershipModalVisible: function (state, data) {
            state.registerHotelMembershipModalVisible = data
        },
        set_hotelInfo: function (state, data) {
            state.hotelInfo = data
        },
    },
    actions: {
        getHotelOrders: async ({state, commit}, id) => {
            const res = await getHotelOrdersAPI(id)
            res.reverse()
            if (res) {
                commit('set_orderList', res)
            }
        },
        getHotelOrdersInMonth: async ({state, commit}, id) => {
            const res = await getHotelOrdersInMonthAPI(id)
            if (res) {
                let num = []
                let value = []
                commit('set_orderInMonth', res)
                for (let orders of res) {
                    let n = 0, v = 0.00
                    for (const order of orders){
                        n++
                        v+=order.price
                    }
                    num = [...num, n]
                    value = [...value, v]
                }
                commit('set_orderNumInMonth', num)
                commit('set_orderValueInMonth', value)
            }
        },
        addRoom: async ({state, dispatch, commit}) => {
            const res = await addRoomAPI(state.addRoomParams)
            if (res) {
                commit('set_addRoomModalVisible', false)
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                })
                dispatch('getHotelInfo', state.addRoomParams.id)
                message.success('添加成功')
            } else {
                message.error('添加失败')
            }
        },
        deleteRoom: async ({state, dispatch}, params) => {
            const res = await deleteRoomAPI(params)
            if (res) {
                dispatch('getHotelInfo', params.hotelId)
                message.success('删除成功')
            }
        },
        addHotelCoupon: async ({state, commit, dispatch}, data) => {
            let res = null
            switch (data.type) {
                case 1:
                    res = await hotelBirthdayCouponAPI(data)
                    break
                case 2:
                    res = await hotelManyRoomCouponAPI(data)
                    break
                case 3:
                    res = await hotelTargetMoneyAPI(data)
                    break
                case 4:
                    res = await TimeCouponAPI(data)
                    break
            }
            if (res) {
                dispatch('getHotelCoupon', data.srcId)
                commit('set_addCouponVisible', false)
                message.success('添加策略成功')
            } else {
                message.error('添加失败')
            }
        },
        deleteCoupon: async ({state, commit, dispatch}, data) => {
            const res = await deleteCouponAPI(data.couponId)
            if (res) {
                dispatch('getHotelCoupon', data.hotelId)
                message.success('删除成功')
            }else {
                message.error('删除失败')
            }
        },
        getHotelInfo: async ({state, commit}, hotelId) => {
            const res = await getHotelByIdAPI(hotelId)
            if (res) {
                commit('set_hotelInfo', res)
            } else {
                message.error('获取失败')
            }
        },
        getHotelCoupon: async ({state, commit}, hotelId) => {
            const res = await hotelAllCouponsAPI(hotelId)
            if (res) {
                commit('set_couponList', res)
            }
        },
        updateHotelInfo: async ({state, dispatch}, data) => {
            const res = await updateHotelInfoAPI(state.hotelInfo.id, data)
            if (res) {
                message.success('修改成功')
                dispatch('getHotelInfo', state.hotelInfo.id)
            }
        }
    }
}

export default hotelManager
