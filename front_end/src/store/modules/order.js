// created by glh 2020.05.12
import Vue from 'vue'
import { message } from 'ant-design-vue'
import {
    execOrderAPI
} from '@/api/order'

const order = {
    state: {
        orderDetailVisible: false,
        orderInfo: {},
    },
    mutations: {
        set_orderDetailVisible: function (state, data) {
            state.orderDetailVisible = data
        },
        set_orderInfo: function (state, data) {
            state.orderInfo = data
        }
    },
    actions: {
        execOrder: async({ state, dispatch }, orderId) => {
            const res = await execOrderAPI(orderId)
            if(res) {
                dispatch('geAllOrders')
                message.success('撤销成功')
            }else{
                message.error('撤销失败')
            }
        },
    }
}
export default order
