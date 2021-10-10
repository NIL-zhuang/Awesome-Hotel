// created by glh 2020-05-23
// 网站营销人员
import {
    getAllOrdersAPI,
    getOrdersInMonthAPI,
} from "../../api/order";
import {
    getUserInfoByEmailAPI,
    getAllUsersAPI,
} from "../../api/user";
import {
    chargeCreditAPI,
    getAllSalesmanTelAPI,
} from "../../api/salesPerson";
import {
    BizRegionCouponAPI,
    CorporateCouponAPI,
    TimeCouponAPI,
    getSiteCouponsAPI,
    deleteCouponAPI,
} from "../../api/coupon";
import {
    getAllClientVIPAPI,
    freezeClientVIPAPI,
    restoreClientVIPAPI,
    getAllCorpVIPAPI,
    freezeCorpVIPAPI,
    restoreCorpVIPAPI,
    getTheRequestOfLevelAPI,
    formulateALevelAPI,
    VIPCorpCheckAPI,
} from "../../api/membership";
import {message} from "ant-design-vue";

const salesPerson = {
    state: {
        allOrderList: [],
        allOrderInMonth: [],
        allOrderNumInMonth: [],
        allOrderValueInMonth: [],
        siteCouponList: [],
        handleAbnormalOrderVisible: false,
        currentUserEmail: '',
        currentUserInfo: {},
        addSiteCouponVisible: false,
        searchSuccess: false,
        //管理会员相关
        allUserList: [],
        allClientVIPList: [],
        allCorpVIPList: [],
        //会员等级相关
        levels: [],
        corpLevels: [],
        levelModifyModalVisible: false,
        currentLevel: {},
        //
        salesPersonTel: []
    },
    mutations: {
        set_allOrderList: function (state, data) {
            state.allOrderList = data
        },
        set_allOrderInMonth: function (state, data){
            state.allOrderInMonth = data
        },
        set_allOrderNumInMonth: function (state, data){
            state.allOrderNumInMonth = data
        },
        set_allOrderValueInMonth: function (state, data){
            state.allOrderValueInMonth = data
        },
        set_siteCouponList: function(state, data){
            state.siteCouponList = data
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
        },
        set_levelModifyModalVisible: function (state, data) {
            state.levelModifyModalVisible = data
        },
        set_currentLevel: function (state, data) {
            state.currentLevel = data
        },
        set_salesPersonTel: function (state, data) {
            state.salesPersonTel = data
        }
    },
    actions: {
        getAllOrders: async ({state, commit}) => {
            const res = await getAllOrdersAPI()
            if (res) {
                commit('set_allOrderList', res)
            }
        },
        getAllOrdersInMonth: async ({state, commit}) => {
            const res = await getOrdersInMonthAPI()
            if (res) {
                let num = []
                let value = []
                commit('set_allOrderInMonth', res)
                for (let orders of res) {
                    let n = 0, v = 0.00
                    for (const order of orders){
                        n++
                        v+=order.price
                    }
                    num = [...num, n]
                    value = [...value, v]
                }
                commit('set_allOrderNumInMonth', num)
                commit('set_allOrderValueInMonth', value)
            }
        },
        getCurrentUserInfoByEmail: async ({state, commit}) => {
            const params = {
                email: state.currentUserEmail
            }
            let res = await getUserInfoByEmailAPI(params)
            if (res) {
                if (res.userType === 'Client') {
                    if (!state.searchSuccess) {
                        message.success('搜索成功')
                    }
                    commit('set_currentUserInfo', res)
                    commit('set_searchSuccess', true)
                } else {
                    message.error('非客户账号')
                }
            } else {
                commit('set_searchSuccess', false)
            }
        },
        chargeCredit: async ({state, commit, dispatch}, money) => {
            const params = {
                id: state.currentUserInfo.id,
                change: 100 * money,
                reason: '充值',
            }
            const res = await chargeCreditAPI(params)
            if (res) {
                message.success("充值成功")
                dispatch('getCurrentUserInfoByEmail')
            } else {
                message.error("充值失败")
            }
        },
        getSiteCoupon: async ({ state, commit}) => {
            const res = await getSiteCouponsAPI()
            if (res) {
                commit('set_siteCouponList', res)
            }
        },
        addSiteCoupon: async ({commit, dispatch}, data) => {
            let res = null
            let check = false
            switch (data.type) {
                case 1:
                    res = await TimeCouponAPI(data)
                    break
                case 2:
                    res = await BizRegionCouponAPI(data)
                    break
                case 3:
                    check =await VIPCorpCheckAPI(data.corporateName)
                    if (check)
                        res = await CorporateCouponAPI(data)
                    else
                        message.error('该企业未注册为VIP！')
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
        deleteSiteCoupon: async ({state, commit, dispatch}, data) => {
            const res = await deleteCouponAPI(data)
            if (res) {
                dispatch('getSiteCoupon')
                message.success('删除成功')
            }else {
                message.error('删除失败')
            }
        },
        getAllUsers: async ({state, commit}) => {
            const res = await getAllUsersAPI()
            if (res) {
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
        getClientLevel: async ({state}) => {
            state.levels = []
            for (let i = 1; i <= 5; i++) {
                const res = await getTheRequestOfLevelAPI(i, "Client")
                state.levels.push(res)
            }
        },
        getCorpLevel: async ({state}) => {
            state.corpLevels = []
            for (let i = 1; i <= 3; i++) {
                const res = await getTheRequestOfLevelAPI(i, "Corporation")
                state.corpLevels.push(res)
            }
        },
        formulateALevel: async ({state, dispatch, commit}, params) => {
            const res = await formulateALevelAPI(params)
            if (res) {
                message.success('修改成功')
                if (params.type === 'Client'){
                    dispatch('getClientLevel')
                }else{
                    dispatch('getCorpLevel')
                }
                commit('set_levelModifyModalVisible', false)
            } else {
                message.error('修改失败')
            }
        },
        getAllSalesmanTel: async ({state, commit}) => {
            const res = await getAllSalesmanTelAPI()
            if (res) {
                commit('set_salesPersonTel', res)
            }
        },
    },
}
export default salesPerson
