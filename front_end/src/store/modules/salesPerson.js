// created by glh 2020-05-23
// 网站营销人员
import {
    getAllOrdersAPI
} from "../../api/order";
import {
    getUserInfoByEmailAPI,
} from "../../api/user";
import {
    chargeCreditAPI
} from "../../api/salesPerson";
import {
    CorporateCouponAPI
} from "../../api/coupon";
import {message} from "ant-design-vue";

const salesPerson = {
    state: {
        allOrderList: [],
        siteCouponList: [],
        handleAbnormalOrderVisible: false,
        currentUserEmail: '',
        currentUserInfo: {},
        addSiteCouponVisible: false,
        searchSuccess: false,
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
        }
    },
    actions: {
        getAllOrders: async ({state, commit}) => {
            const res = await getAllOrdersAPI()
            if (res) {
                commit('set_allOrderList', res)
            }
        },
        getCurrentUserInfoByEmail: async ({state, commit}) => {
            const params = {
                email: state.currentUserEmail
            }
            let res = await getUserInfoByEmailAPI(params)
            if (res) {
                commit('set_currentUserInfo', res)
                commit('set_searchSuccess', true)
            } else {
                commit('set_searchSuccess', false)
            }
        },
        chargeCredit: async ({state, commit, dispatch}, money) => {
            const params = {
                id: state.currentUserInfo.id,
                change: 100 * money,
            }
            const res = await chargeCreditAPI(params)
            if (res) {
                message.success("充值成功")
                dispatch('getCurrentUserInfoByEmail')
            } else {
                message.error("充值失败")
            }
        },
        getSiteCoupon: async ({commit}) => {
            // todo
        },
        addSiteCoupon: async ({commit, dispatch}, data) => {
            let res = null
            switch (data.type) {
                case 1:
                    break
                case 2:
                    break
                case 3:
                    res = await CorporateCouponAPI(data)
                    break
            }
            if (res) {
                dispatch('getSiteCoupon')
                commit('set_addSiteCouponVisible', false)
                message.success('添加策略成功')
            } else {
                message.error('添加失败')
            }
        },
    }
}
export default salesPerson