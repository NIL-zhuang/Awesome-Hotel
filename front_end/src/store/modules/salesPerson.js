// created by glh 2020-05-23
// 网站营销人员
import {
    getAllOrdersAPI
} from "../../api/order";
import {
    getUserInfoAPI,
    getUserInfoByEmailAPI,
} from "../../api/user";
import {
    chargeCreditAPI
} from "../../api/salesPerson";
import {message} from "ant-design-vue";

const salesPerson = {
    state: {
        allOrderList: [],
        handleAbnormalOrderVisible: false,
        currentUserEmail: '',
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
            console.log(res)
            if (res) {
                commit('set_currentUserInfo', res)
                message.success("查询成功")
            } else {

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
        }
    }
}
export default salesPerson