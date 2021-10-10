import {
    getManagerListAPI,
    addManagerAPI,
    getSalesPersonListAPI,
    addSalesPersonAPI,
    deleteHotelManagerAPI,
    deleteSalesPersonAPI,
} from '../../api/admin'
import {
    updateUserInfoAPI,
    updateUserPasswordAPI,
} from "../../api/user";
import {
    addHotelAPI,
    deleteHotelAPI,
    updateHotelInfoAPI,
} from "../../api/hotel";

import { message } from 'ant-design-vue'

const admin = {
    state: {
        // 管理酒店工作人员
        managerList: [

        ],
        addManagerModalVisible: false,
        addManagerParams: {
            email: '',
            userName: '',
            phoneNumber: '',
            hotelId: '',
            password: '',
        },
        // 管理网站营销人员
        salesPersonList: [

        ],
        addSalesPersonModalVisible: false,
        addSalesPersonParams: {
            email: '',
            userName: '',
            phoneNumber: '',
            password: '',
        },
        // 修改人员信息
        modifyInfoModalVisible: false,
        modifyUserInfo: {},
        // 添加酒店
        addHotelParams: {
            name: '',
            address: '',
            bizRegion: '',
            hotelStar: '',
            rate: 0,
            description: '',
            phoneNum: '',
        },
        addHotelModalVisible: false,
        // 修改酒店
        modifyHotelModalVisible: false,
        modifyHotelInfo: {},
    },
    mutations: {
        // 管理酒店工作人员相关
        set_managerList: function(state, data) {
            state.managerList = data
        },
        set_addManagerModalVisible: function(state, data) {
            state.addManagerModalVisible = data
        },
        set_addManagerParams: function(state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
                ...data,
            }
        },
        // 管理网站营销人员相关
        set_salesPersonList: function (state, data) {
            state.salesPersonList = data
        },
        set_addSalesPersonModalVisible: function(state, data) {
            state.addSalesPersonModalVisible = data
        },
        set_addSalesPersonParams: function(state, data) {
            state.addSalesPersonParams = {
                ...state.addSalesPersonParams,
                ...data,
            }
        },
        // 修改酒店工作人员或者网站营销人员的基本信息(用户名、手机号、密码)
        set_modifyUserInfo: function (state, data) {
            state.modifyUserInfo = data
        },
        set_modifyInfoModalVisible: function (state, data) {
            state.modifyInfoModalVisible = data
        },
        // 酒店管理相关
        set_addHotelParams: function (state, data) {
            state.addHotelParams = {
                ...state.addHotelParams,
                ...data,
            }
        },
        set_addHotelModalVisible: function (state, data) {
            state.addHotelModalVisible = data
        },
        // 修改酒店信息
        set_modifyHotelModalVisible: function (state, data) {
            state.modifyHotelModalVisible = data
        },
        set_modifyHotelInfo: function (state, data) {
            state.modifyHotelInfo = data
        },
    },
    actions: {
        // 管理酒店工作人员
        getManagerList: async({ commit }) => {
            const res = await getManagerListAPI()
            if(res){
                commit('set_managerList', res)
            }
        },
        addManager: async({ state, commit, dispatch }) => {
            const res = await addManagerAPI(state.addManagerParams)
            if(res) {
                commit('set_addManagerParams',{
                    email: '',
                    password: '',
                    hotelId: '',
                })
                commit('set_addManagerModalVisible', false)
                message.success('添加成功')
                dispatch('getManagerList')
            }else{
                message.error('添加失败')
            }
        },
        deleteHotelManager: async({ state, commit, dispatch }, id) => {
            const res = await deleteHotelManagerAPI(id)
            if(res) {
                dispatch('getManagerList')
                message.success('删除成功')
            } else {
                message.error("删除失败")
            }
        },
        // 管理网站营销人员
        getSalesPersonList: async({ commit }) => {
            const res = await getSalesPersonListAPI()
            if(res){
                commit('set_salesPersonList', res)
            }
        },
        addSalesPerson: async({ state, commit, dispatch }) => {
            const res = await addSalesPersonAPI(state.addSalesPersonParams)
            if(res) {
                commit('set_addSalesPersonParams',{
                    email:'',
                    password:''
                })
                commit('set_addSalesPersonModalVisible', false)
                message.success('添加成功')
                dispatch('getSalesPersonList')
            }else{
                message.error('添加失败')
            }
        },
        deleteSalesPerson: async({ state, commit, dispatch }, id) => {
            const res = await deleteSalesPersonAPI(id)
            if(res) {
                message.success('删除成功')
                dispatch('getSalesPersonList')
            } else {
                message.error("删除失败")
            }
        },
        // 修改人员信息
        adminUpdateUserInfo: async({ state, commit, dispatch }, data) => {
            await updateUserInfoAPI(data)
            const res = await updateUserPasswordAPI(data)
            if (res) {
                dispatch('getSalesPersonList')
                dispatch('getManagerList')
                message.success('修改成功')
            } else {
                message.error('修改失败')
            }
            commit('set_modifyInfoModalVisible', false)
        },
        // 添加酒店
        addHotel: async ({state, dispatch, commit}) => {
            const res = await addHotelAPI(state.addHotelParams)
            if (res) {
                dispatch('getHotelList')
                commit('set_addHotelParams', {
                    name: '',
                    address: '',
                    bizRegion: 'XiDan',
                    hotelStar: '',
                    rate: 0,
                    description: '',
                    phoneNum: '',
                    managerId: '',
                })
                commit('set_addHotelModalVisible', false)
                message.success('添加成功')
            } else {
                message.error('添加失败')
            }
        },
        // 删除酒店
        deleteHotel: async ({state, dispatch, commit}, id) => {
            const res = await deleteHotelAPI(id)
            if(res) {
                dispatch('getHotelList')
                message.success('删除成功')
            } else {
                message.error('删除失败')
            }
        },
        // 修改酒店信息
        updateHotel: async ({state, dispatch, commit}, data) => {
            const res = await updateHotelInfoAPI(state.modifyHotelInfo.id, data)
            if (res) {
                dispatch('getHotelList')
                commit('set_modifyHotelModalVisible', false)
                message.success('修改成功')
            }
        }
    }
}
export default admin
