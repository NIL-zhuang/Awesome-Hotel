import router from '../../router'
import {getToken, setToken, removeToken} from '../../utils/auth'
import {resetRouter} from '../../router'
import {message} from 'ant-design-vue'
import {
    loginAPI,
    registerAPI,
    getUserInfoAPI,
    updateUserInfoAPI,
    updateUserBirthdayAPI,
    getUserCreditAPI,
    getUserCollectionsAPI,
    annulCollectionAPI,
    addCollectionAPI, updateUserPasswordAPI,
} from '../../api/user'

import {
    getUserOrdersAPI,
    cancelOrderAPI,
} from '../../api/order'

import {
    getHotelByIdAPI
} from "../../api/hotel";

import {
    registerClientMembershipAPI,
    registerCorpMembershipAPI,
    getUserVIPAPI,
    getCorpVIPAPI,
    VIPCorpCheckAPI,
} from "../../api/membership"

import moment from "moment";
import passwordForm from "../../views/user/components/passwordForm";

const getDefaultState = () => {
    return {
        userId: '',
        userInfo: {},
        userVIP: {},
        corpVIP: {},
        dateRange: [moment().add(1, 'd'), moment().add(2, 'd')],
        userOrderList: [],
        onceOrderedList: [],
        creditChangeList: [],
        userCollections: [],
        registerSiteMembershipModalVisible: false,
        registerCorporationMembershipModalVisible: false,
        isCorpVIP: false,
    }
}
const user = {
    state: getDefaultState(),
    mutations: {
        reset_state: function (state) {
            state.token = ''
            state.userId = ''
            state.userInfo = {}
            state.userVIP = {}
            state.corpVIP = {}
            state.userOrderList = []
            state.onceOrderedList = []
            state.creditChangeList = []
            state.userCollections = []
            state.registerSiteMembershipModalVisible = false
            state.registerCorporationMembershipModalVisible = false
            state.isCorpVIP = false
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
        set_creditChangeList: (state, data) => {
            state.creditChangeList = data
        },
        set_userCollections: (state, data) => {
            state.userCollections = data
        },
        set_registerSiteMembershipModalVisible: (state, data) => {
            state.registerSiteMembershipModalVisible = data
        },
        set_registerCorporationMembershipModalVisible: (state, data) => {
            state.registerCorporationMembershipModalVisible = data
        },
        set_userVIP: (state, data) => {
            state.userVIP = {
                ...state.userVIP,
                ...data
            }
        },
        set_corpVIP: (state, data) => {
            state.corpVIP = data
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
                await dispatch('getUserInfo')
                dispatch('corpVIPCheck', params.corporation)
                dispatch('getCorpVIP', params.corporation)
            }
        },
        getUserOrders: async ({state, commit}) => {
            const res = await getUserOrdersAPI(state.userId)
            res.reverse()
            if (res) {
                commit('set_userOrderList', res)
            }
        },
        getOnceOrderedList: async ({state, commit}) => {
            const data = await getUserOrdersAPI(state.userId)
            data.reverse()
            let res = new Map()
            for (const item of data) {
                const hotel = await getHotelByIdAPI(item.hotelId)
                if (!res.has(hotel.id)) res.set(hotel.id, hotel)
            }
            if (res) {
                commit('set_onceOrderedList', [...res.values()])
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
                dispatch('getUserVIP', Number(state.userId))
            }
        },
        registerCorporationMembership: async ({state, dispatch}, corpName) => {
            const res = await registerCorpMembershipAPI(corpName)
            if (res) {
                message.success('注册成功')
                dispatch('getUserInfo')
                dispatch('corpVIPCheck', corpName)
                dispatch('getCorpVIP', corpName)
            }
        },
        getUserVIP: async ({state, commit}, id) => {
            const res = await getUserVIPAPI(id)
            if (res) {
                commit('set_userVIP', res)
            }
        },
        getCorpVIP: async ({state, commit}, name) => {
            const res = await getCorpVIPAPI(name)
            if (!res) {
                commit('set_corpVIP', {})
            }
            if (res) {
                commit('set_corpVIP', res)
            }
        },
        corpVIPCheck: async ({state}, name) => {
            state.isCorpVIP = await VIPCorpCheckAPI(name)
        },
        updateUserBirthday: async ({state, dispatch}, data) => {
            const params = {
                id: state.userId,
                birthday: data
            }
            const res = await updateUserBirthdayAPI(params)
            if (res) {
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        getUserCredits: async ({state, commit}, id) => {
            let res = await getUserCreditAPI(id)
            res.reverse()
            if (res) {
                commit('set_creditChangeList', res)
            }
        },
        getUserCollections: async ({state, commit}, id) => {
            const res = await getUserCollectionsAPI(id)
            if (res) {
                commit('set_userCollections', res)
            }
        },
        addCollection: async ({state, commit}, data) => {
            const res = await addCollectionAPI(data)
            if (res) {
                message.success('收藏成功')
                commit('set_currHotelCollectedByUser', true)
                commit('update_currCollections', 1)
            }
        },
        annulCollection: async ({state, commit}, data) => {
            const res = await annulCollectionAPI(data)
            if (res){
                message.success('取消收藏成功')
                commit('set_currHotelCollectedByUser', false)
                commit('update_currCollections', -1)
            }
        },
        updateUserCorporation: async ({state, commit, dispatch}, corporation) => {
            const params = {
                id: state.userId,
                userName: state.userInfo.userName,
                phoneNumber: state.userInfo.phoneNumber,
                corporation: corporation,
            }
            const res = await updateUserInfoAPI(params)
            if (res)  {
                dispatch('getUserInfo')

            }
        },
        updateUserPassword: async ({state, commit, dispatch}, password) => {
            const params = {
                id: state.userId,
                password: password
            }
            const res = await updateUserPasswordAPI(params)
            if (res) {
                message.success('修改成功')
            }
        }
    }
}

export default user
