// created by glh 2020-05-23
// 网站营销人员
import {
    getAllOrdersAPI
} from "../../api/order";
import {
    getUserInfoAPI
} from "../../api/user";
import {message} from "ant-design-vue";


const salesPerson = {
    state: {
        allOrderList: [],
        handleAbnormalOrderVisible: false,
        currentUserId: '',
        currentUserInfo: {},
        addSiteCouponVisible: false,
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
        set_currentUserInfo: function (state, data) {
            state.currentUserInfo = {
                ...state.currentUserInfo,
                ...data
            }
        },
        set_addSiteCouponVisible: function (state, data) {
            state.addSiteCouponVisible = data
        },
    },
    actions: {
        getAllOrders: async ({state, commit}) => {
            let res = await getAllOrdersAPI()
            if (res) {
                commit('set_allOrderList', res)
            }
        },
        getCurrentUserInfo: async ({state, commit}) => {
            let res = await getUserInfoAPI(state.currentUserId)
            if (res) {
                commit('set_currentUserInfo', res)
            }
        },
    }
}
export default salesPerson