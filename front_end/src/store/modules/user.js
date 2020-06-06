import Vue from 'vue'
import router from '@/router'
import {getToken, setToken, removeToken} from '@/utils/auth'
import {resetRouter} from '@/router'
import {message} from 'ant-design-vue'
import {
    loginAPI,
    registerAPI,
    getUserInfoAPI,
    updateUserInfoAPI,
} from '@/api/user'

import {
    getUserOrdersAPI,
    cancelOrderAPI,
} from '@/api/order'

import {
    getHotelByIdAPI
} from "@/api/hotel";

import {
    registerClientMembershipAPI,
    registerCorpMembershipAPI,
} from "@/api/membership"

import moment from "moment";

const getDefaultState = () => {
    return {
        userId: '',
        userInfo: {},
        dateRange: [moment(), moment().add(1, 'd')],
        userOrderList: [],
        onceOrderedList: [],
        registerSiteMembershipModalVisible: false,
        registerCorporationMembershipModalVisible: false,
    }
}
const user = {
    state: getDefaultState(),

    mutations: {
        reset_state: function (state) {
            state.token = ''
            state.userId = ''
            state.userInfo = {}
            state.userOrderList = []
        },
        set_token: function (state, token) {
            state.token = token
        },
        set_email: (state, data) => {
            state.email = data
        },
        set_userId: (state, data) => {
            state.userId = data
        },
        set_userInfo: (state, data) => {
            state.userInfo = {
                ...state.userInfo,
                ...data
            }
        },
        set_dateRange: (state, data) => {
            state.dateRange = data
        },
        set_userOrderList: (state, data) => {
            state.userOrderList = data
        },
        set_onceOrderedList: (state, data) => {
            state.onceOrderedList = data
        },
        set_registerSiteMembershipModalVisible: (state, data) => {
            state.registerSiteMembershipModalVisible = data
        },
        set_registerCorporationMembershipModalVisible: (state, data) => {
            state.registerCorporationMembershipModalVisible = data
        },
    },

    actions: {
        login: async ({state, dispatch, commit}, userData) => {
            const res = await loginAPI(userData)
            if (res) {
                setToken(res.id)
                commit('set_userId', res.id)
                // modifed by glh
                dispatch('getUserInfo').then(() => {
                    router.push('/homePage')
                })
            }
        },
        register: async ({commit}, data) => {
            const res = await registerAPI(data)
            if (res) {
                message.success('注册成功')
            }
        },
        getUserInfo: ({state, commit}) => {
            return new Promise((resolve, reject) => {
                getUserInfoAPI(state.userId).then(response => {
                    const data = response
                    if (!data) {
                        reject('登录已过期，请重新登录')
                    }
                    commit('set_userInfo', data)
                    commit('set_userId', data.id)
                    resolve(data)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        updateUserInfo: async ({state, dispatch}, data) => {
            const params = {
                id: state.userId,
                ...data,
            }
            const res = await updateUserInfoAPI(params)
            if (res) {
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        getUserOrders: async ({state, commit}) => {
            const res = await getUserOrdersAPI(state.userId)
            if (res) {
                commit('set_userOrderList', res)
            }
        },
        getOnceOrderedList: async ({state, commit}) => {
            const data = await getUserOrdersAPI(state.userId)
            let res = []
            for (const item of data) {
                const hotel = await getHotelByIdAPI(item.hotelId)
                res=[...res, hotel]
            }
            if (res) {
                commit('set_onceOrderedList', res)
            }
        },
        cancelOrder: async ({state, dispatch}, orderId) => {
            const res = await cancelOrderAPI(orderId)
            if (res) {
                dispatch('getUserOrders')
                message.success('撤销成功')
            } else {
                message.error('撤销失败')
            }
        },
        logout: async ({commit}) => {
            removeToken()
            resetRouter()
            commit('reset_state')
        },
        // remove token
        resetToken({commit}) {
            return new Promise(resolve => {
                removeToken() // must remove  token  first
                commit('reset_state')
                resolve()
            })
        },
        registerMembership: async ({state, dispatch}, data) => {
            const params = {
                id: Number(state.userId),
                ...data,
            }
            const res = await registerClientMembershipAPI(params)
            if (res) {
                message.success('注册成功')
                dispatch('getUserInfo')
            }
        },
        registerCorporationMembership: async ({state, dispatch}, data) => {
            const params = {
                id: Number(state.userId),
                ...data,
            }
            const res = await registerCorpMembershipAPI(params)
            if (res) {
                message.success('注册成功')
                dispatch('getUserInfo')
            }
        }

    }
}

export default user
