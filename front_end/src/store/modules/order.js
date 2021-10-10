// created by glh 2020.05.12
import Vue from 'vue'
import { message } from 'ant-design-vue'
import {
    checkInOrderAPI,
    finishOrderAPI,
    addCommentAPI,
    getOrderCommentAPI,
    annulOrderCommentAPI,
    markAbnormalOrderAPI,
    handleAbnormalOrderAPI,
    argueAbnormalOrderAPI,
} from '../../api/order'

const order = {
    state: {
        orderDetailVisible: false,
        orderInfo: {},
        commentOrderModalVisible: false,
        currentOrderComment: {},
        argueAbnormalOrderModalVisible: false,
        argumentModify: false,
    },
    mutations: {
        set_orderDetailVisible: function (state, data) {
            state.orderDetailVisible = data
        },
        set_orderInfo: function (state, data) {
            state.orderInfo = data
        },
        set_commentOrderModalVisible: function (state, data) {
            state.commentOrderModalVisible = data
        },
        set_currentOrderComment: function (state, data) {
            state.currentOrderComment = data
        },
        set_argueAbnormalOrderModalVisible: function (state, data) {
            state.argueAbnormalOrderModalVisible = data
        },
        set_argumentModify: function (state, data) {
            state.argumentModify = data
        }
    },
    actions: {
        checkInOrder: async({ state, dispatch }, orderId) => {
            const res = await checkInOrderAPI(orderId)
            if(res) {
                message.success('入住成功')
            }
        },
        finishOrder: async({state, dispatch}, orderId) => {
            const res = await finishOrderAPI(orderId)
            if (res) {
                message.success('执行成功')
            }
        },
        addComment: async ({state, commit, dispatch}, data) => {
            const res = await addCommentAPI(data)
            if (res) {
                commit('set_commentOrderModalVisible', false)
                message.success('评论成功')
                dispatch('getOrderComment', state.orderInfo.id)
            }
        },
        getOrderComment: async ({state, commit}, orderId) => {
            const res = await getOrderCommentAPI(orderId)
            if (res) {
                commit('set_currentOrderComment', res)
            }
        },
        annulOrderComment: async ({state, commit, dispatch}, orderId) => {
            const res = await annulOrderCommentAPI(orderId)
            if (res) {
                message.success('撤回成功')
            }
        },
        markAbnormalOrder: async ({state, commit, dispatch}, orderId) => {
            const res = await markAbnormalOrderAPI(orderId)
            if (res) {
                message.success('标记成功')
            }
        },
        handleAbnormalOrder: async ({state, commit, dispatch}, params) => {
            const res = await handleAbnormalOrderAPI(params)
            if (res) {
                message.success('处理成功')
                commit('set_handleAbnormalOrderVisible', false)
                dispatch('getAllOrders')
            }
        },
        argueAbnormalOrder: async ({state, dispatch}, params) => {
            const res = await argueAbnormalOrderAPI(params)
            if (res) {
                message.success('申诉成功')
            }
        }
    }
}
export default order
