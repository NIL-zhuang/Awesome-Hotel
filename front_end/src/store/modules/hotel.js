import {message} from 'ant-design-vue'
import store from '@/store'
import {
    getHotelsAPI,
    getHotelByIdAPI,
    searchHotelAPI,
    addQuestionAPI,
    getHotelQuestionAPI,
    addAnswerAPI,
} from '@/api/hotel'
import {
    reserveHotelAPI
} from '@/api/order'
import {
    orderMatchCouponsAPI,
} from '@/api/coupon'

const hotel = {
    state: {
        hotelList: [],
        limitedHotelList: [],
        searchList: [],
        hotelListParams: {
            pageNo: 0,
            pageSize: 20,
        },
        hotelListLoading: true,
        currentHotelId: '',
        currentHotelInfo: {},
        orderModalVisible: false,
        currentOrderRoom: {},
        orderMatchCouponList: [],
        hotelQuestion: [],
        answersVisible: new Map()
    },
    mutations: {
        set_hotelList: function (state, data) {
            state.hotelList = data
        },
        set_limitedHotelList: function (state, data) {
            state.limitedHotelList = data
        },
        set_searchList: function (state, data) {
            state.searchList = data
        },
        set_hotelListParams: function (state, data) {
            state.hotelListParams = {
                ...state.hotelListParams,
                ...data,
            }
        },
        set_hotelListLoading: function (state, data) {
            state.hotelListLoading = data
        },
        set_currentHotelId: function (state, data) {
            state.currentHotelId = data
        },
        set_currentHotelInfo: function (state, data) {
            state.currentHotelInfo = {
                ...state.currentHotelInfo,
                ...data,
            }
        },
        set_orderModalVisible: function (state, data) {
            state.orderModalVisible = data
        },
        set_currentOrderRoom: function (state, data) {
            state.currentOrderRoom = {
                ...state.currentOrderRoom,
                ...data,
            }
        },
        set_orderMatchCouponList: function (state, data) {
            state.orderMatchCouponList = data
        },
        set_hotelQuestion: function (state, data) {
            state.hotelQuestion = data
        },
        set_answersVisible: function (state, data) {
            state.answersVisible = data
        }
    },
    actions: {
        getHotelList: async ({commit, state}) => {
            const res = await getHotelsAPI()
            if (res) {
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getLimitedHotelList: async ({state, commit}) => {
            let res = []
            const start = state.hotelListParams.pageNo * state.hotelListParams.pageSize
            const end = Math.min(start + state.hotelListParams.pageSize, state.hotelList.length)
            for (let i = start; i < end; i++) {
                res = [...res, state.hotelList[i]]
            }
            if (res) {
                commit('set_limitedHotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getHotelById: async ({commit, state}) => {
            const res = await getHotelByIdAPI(state.currentHotelId)
            if (res) {
                commit('set_currentHotelInfo', res)
            }
        },
        addOrder: async ({state, commit}, data) => {
            const res = await reserveHotelAPI(data)
            if (res) {
                commit('set_orderModalVisible', false)
                message.success('预定成功')
            }
        },
        getOrderMatchCoupons: async ({state, commit}, data) => {
            const res = await orderMatchCouponsAPI(data)
            if (res) {
                commit('set_orderMatchCouponList', res)
            }
        },
        searchHotel: async ({state, commit}, data) => {
            const res = await searchHotelAPI(data)
            if (res) {
                commit('set_searchList', res)
            }
        },
        getHotelQuestion: async ({state, commit}) => {
            const res = await getHotelQuestionAPI(state.currentHotelId)
            let data = new Map()
            for (const item of res) {
                data.set(item.id, false)
            }
            if (res) {
                commit('set_hotelQuestion', res)
                commit('set_answersVisible', data)
            }
        },
        addQuestion: async ({state, dispatch}, data) => {
            const res = await addQuestionAPI(data)
            if (res) {
                dispatch('getHotelQuestion')
            }
        },
        addAnswer: async ({state, dispatch}, data) => {
            const res = await addAnswerAPI(data)
            if (res) {
                dispatch('getHotelQuestion')
            }
        }
    }
}

export default hotel