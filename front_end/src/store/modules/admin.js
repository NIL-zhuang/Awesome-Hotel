import {
    getManagerListAPI,
    addManagerAPI,
    getSalesPersonListAPI,
} from '@/api/admin'
import { message } from 'ant-design-vue'
import {addSalesPersonAPI} from "../../api/admin";

const admin = {
    state: {
        managerList: [

        ],
        salesPersonList: [

        ],
        addManagerModalVisible: false,
        addSalesPersonModalVisible: false,
        addManagerParams: {
            email: '',
            password: '',
            hotelId: '',
        },
        addSalesPersonParams: {
            email: '',
            password: '',
        }
    },
    mutations: {
        set_managerList: function(state, data) {
            state.managerList = data
        },
        set_salesPersonList: function (state, data) {
            state.salesPersonList = data
        },
        set_addManagerModalVisible: function(state, data) {
            state.addManagerModalVisible = data
        },
        set_addManagerParams: function(state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
                ...data,
            }
            console.log(state.addManagerParams)
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
    },
    actions: {
        getManagerList: async({ commit }) => {
            const res = await getManagerListAPI()
            if(res){
                commit('set_managerList', res)
            }
        },
        getSalesPersonList: async({ commit }) => {
            const res = await getSalesPersonListAPI()
            if(res){
                commit('set_salesPersonList', res)
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
    }
}
export default admin