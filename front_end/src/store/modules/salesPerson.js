// created by glh 2020-05-23
// 网站营销人员
import {
    getAllOrdersAPI
} from "../../api/order";
import {
    getUserInfoByEmailAPI,
    getAllUsersAPI,
} from "../../api/user";
import {
    chargeCreditAPI
} from "../../api/salesPerson";
import {
    getAllClientVIPAPI,
    freezeClientVIPAPI,
    restoreClientVIPAPI,
    getAllCorpVIPAPI,
    freezeCorpVIPAPI,
    restoreCorpVIPAPI
} from "../../api/membership";
import {message} from "ant-design-vue";

const salesPerson = {
    state: {
        allOrderList: [],
        handleAbnormalOrderVisible: false,
        currentUserEmail: '',
        currentUserInfo: {},
        addSiteCouponVisible: false,
        searchSuccess: false,
        //管理会员相关
        allUserList: [],
        allClientVIPList: [],
        allCorpVIPList: [],

    },
    mutations: {
        set_allOrderList: function (state, data) {
            state.allOrderList = data
        },
        set_handleAbnormalOrderVisible: function (state, data) {
            state.handleAbnormalOrderVisible = data
        },
        set_currentUserId: function (state, data) {
            state.currentUserId = data
        },
        set_currentUserEmail: function (state, data) {
            state.currentUserEmail = data
        },
        set_currentUserInfo: function (state, data) {
            state.currentUserInfo = {
                ...state.currentUserInfo,
                ...data
            }
        },
        set_addSiteCouponVisible: function (state, data) {
            state.addSiteCouponVisible = data
        },
        set_searchSuccess: function (state, data) {
            state.searchSuccess = data
        },
        set_allUserList: function (state, data) {
            state.allUserList = data
        },
        set_allClientVIPList: function (state, data) {
            state.allClientVIPList = data
        },
        set_allCorpVIPList: function (state, data) {
            state.allCorpVIPList = data
        }
    },
    actions: {
        getAllOrders: async ({state, commit}) => {
            let res = await getAllOrdersAPI()
            if (res) {
                commit('set_allOrderList', res)
            }
        },
        getCurrentUserInfoByEmail: async ({state, commit}) => {
            const params = {
                email: state.currentUserEmail
            }
            let res = await getUserInfoByEmailAPI(params)
            console.log("in getCurrent")
            console.log(res)
            if (res) {
                commit('set_currentUserInfo', res)
                commit('set_searchSuccess', true)
                message.success("查询成功")
            } else {
                commit('set_searchSuccess', false)
            }
        },
        chargeCredit: async ({state, commit, dispatch}, money) => {
            console.log("in chargeCredit")
            console.log(money)
            const params = {
                id: state.currentUserInfo.id,
                money: money,
            }
            const res = await chargeCreditAPI(params)
            if(res) {
                message.success("充值成功")
                dispatch(this.getCurrentUserInfoByEmail())
            } else {
                message.error("充值失败")
            }
        },
        getAllUsers: async ({state, commit}) => {
            const res = await getAllUsersAPI()
            if(res) {
                commit('set_allUserList', res)
            }
        },
        getAllClientVIP: async ({state, commit}) => {
            const res = await getAllClientVIPAPI()
            if (res) {
                commit('set_allClientVIPList', res)
            }
        },
        freezeClientVIP: async ({state, dispatch, commit}, id) => {
            const res = await freezeClientVIPAPI(id)
            if (res) {
                message.success('冻结成功')
                dispatch('getAllClientVIP')
            }
        },
        restoreClientVIP: async ({state, dispatch, commit}, id) => {
            const res = await restoreClientVIPAPI(id)
            if (res) {
                message.success('恢复成功')
                dispatch('getAllClientVIP')
            }
        },
        getAllCorpVIP: async ({state, commit}) => {
            const res = await getAllCorpVIPAPI()
            if (res) {
                commit('set_allCorpVIPList', res)
            }
        },
        freezeCorpVIP: async ({state, dispatch, commit}, corporationName) => {
            const res = await freezeCorpVIPAPI(corporationName)
            if (res) {
                message.success('冻结成功')
                dispatch('getAllCorpVIP')
            }
        },
        restoreCorpVIP: async ({state, dispatch, commit}, corporationName) => {
            const res = await restoreCorpVIPAPI(corporationName)
            if (res) {
                message.success('恢复成功')
                dispatch('getAllCorpVIP')
            }
        },
    }
}
export default salesPerson
