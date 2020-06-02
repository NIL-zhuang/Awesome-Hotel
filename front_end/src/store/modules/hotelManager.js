import {
    addRoomAPI,
} from '@/api/hotelManager'
import {
    getHotelOrdersAPI,
} from '@/api/order'
import {
    hotelAllCouponsAPI,
    hotelTargetMoneyAPI,
    hotelBirthdayCouponAPI,
    hotelTimeCouponAPI,
    hotelManyRoomCouponAPI,
    CorporateCouponAPI,
    deleteCouponAPI,
} from '@/api/coupon'
import {
    registerHotelMembershipAPI
} from "@/api/hotelManager";
import {message} from 'ant-design-vue'

const hotelManager = {
    state: {
        manageHotelId: 0, // 需要一些新的方法
        orderList: [],
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
        activeHotelId: 0,
        couponList: [],
        registerHotelMembershipModalVisible: false,
    },
    mutations: {
        set_managerList: function (state, data) {
            state.manageHotelList = data
        },
        set_orderList: function (state, data) {
            state.orderList = data
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
        }
    },
    actions: {
        getHotelOrders: async ({state, commit}) => {
            let res = await getHotelOrdersAPI(state.manageHotelId)
            if (res) {
                commit('set_orderList', res)
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
                message.success('添加成功')
            } else {
                message.error('添加失败')
            }
        },
        getHotelCoupon: async ({state, commit}) => {
            const res = await hotelAllCouponsAPI(state.activeHotelId)
            if (res) {
                commit('set_couponList', res)
            } else {

            }
        },
        addHotelCoupon: async ({commit, dispatch}, data) => {
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
                    res = await hotelTimeCouponAPI(data)
                    break
                case 5:
                    res = await CorporateCouponAPI(data)
                    break
            }
            if (res) {
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible', true)
                message.success('添加策略成功')
            } else {
                message.error('添加失败')
            }
        },
        deleteCoupon: async ({commit, dispatch}, data) => {
            const res = await deleteCouponAPI(data)
            if (res) {
                dispatch('getHotelCoupon')
                message.success('删除成功')
            }else {
                message.error('删除失败')
            }
        },
        registerHotelMembership: async ({state, dispatch, commit}, data) => {
            const res = await registerHotelMembershipAPI(data)
            if(res) {
                dispatch('getHotelById')
                commit('set_registerHotelMembershipModalVisible', false)
                message.success('注册成功')
            } else {
                message.error('注册失败')
            }
        }
    }
}

export default hotelManager
