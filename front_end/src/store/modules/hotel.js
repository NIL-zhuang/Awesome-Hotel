import {message} from 'ant-design-vue'
import store from '@/store'
import {
    getHotelsAPI,
    getHotelByIdAPI,
    searchHotelAPI,
    addQuestionAPI,
    getHotelQuestionAPI,
    addAnswerAPI,
    getHotelCommentsAPI, deleteQuestionAPI, deleteAnswerAPI,
} from '../../api/hotel'
import {
    reserveHotelAPI,
    getOrderableRoom,
} from '../../api/order'
import {
    orderMatchCouponsAPI,
} from '../../api/coupon'
import {
    getHotelCollectionNumAPI,
    userCollectHotelAPI,
}from '../../api/user'

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
        orderSuccess: false,
        currentOrderRoom: {},
        orderMatchCouponList: [],
        hotelQuestion: [],
        answersVisible: new Map(),
        currCollections: 0,
        currHotelCollectedByUser: false,
        hotelComments: [],
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
        set_orderSuccess: function(state, data){
            state.orderSuccess = data
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
        },
        set_currCollections: function (state, data) {
            state.currCollections = data
        },
        set_currHotelCollectedByUser: function (state, data) {
            state.currHotelCollectedByUser = data
        },
        update_currCollections: function (state, data) {
            state.currCollections += data
        },
        set_hotelComments: function (state, data) {
            state.hotelComments = data
        },
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
        getHotelByIdWithTime: async ({state, commit}, data) => {
            const res = await getOrderableRoom(data)
            if (res) {
                commit('set_currentHotelInfo', res)
            }
        },
        addOrder: async ({state, commit}, data) => {
            const res = await reserveHotelAPI(data)
            if (res) {
                commit('set_orderSuccess', true)
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
                message.success('搜索成功')
            } else {
                message.error('搜索失败')
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
                message.success('提问成功')
            }
        },
        deleteQuestion: async ({state, dispatch}, data) => {
            const res = await deleteQuestionAPI(data)
            if (res) {
                dispatch('getHotelQuestion')
            }
        },
        addAnswer: async ({state, dispatch}, data) => {
            const res = await addAnswerAPI(data)
            if (res) {
                dispatch('getHotelQuestion')
                message.success('回答成功')
            }
        },
        deleteAnswer: async ({state, dispatch}, data) => {
            const res = await deleteAnswerAPI(data)
            if (res) {
                dispatch('getHotelQuestion')
            }
        },
        getCurrCollections: async ({state}) => {
            state.currCollections = await getHotelCollectionNumAPI(state.currentHotelId)
        },
        getUserCollectHotel: async ({state}, uid) => {
            const params = {
                userId: uid,
                hotelId: state.currentHotelId
            }
            state.currHotelCollectedByUser = await userCollectHotelAPI(params)
        },
        getHotelComments: async ({state, commit}) => {
            const res = await getHotelCommentsAPI(Number(state.currentHotelId))
            if (res) {
                commit('set_hotelComments', res)
            }
        }
    }
}

export default hotel
