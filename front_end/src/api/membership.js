import { axios } from '@/utils/request'

const api = {
    memberShipPre: '/api/membership'
}

export function registerClientMembershipAPI(params) {
    return axios({
        url: `${api.memberShipPre}/registerClientMembership`,
        method: 'POST',
        params
    })
}

export function freezeClientVIPAPI(id) {
    return axios({
        url: `/${api.memberShipPre}/${id}/freezeClientVIP`,
        method: 'POST'
    })
}

export function restoreClientVIPAPI(id) {
    return axios({
        url: `/${api.memberShipPre}/${id}/restoreClientVIP`,
        method: 'POST'
    })
}

export function getUserVIPAPI(id) {
    return axios({
        url: `${api.memberShipPre}/${id}/getUserVIP`,
        method: 'GET',
    })
}

export function addVIPUserConsumptionAPI(params) {
    return axios({
        url: `${api.memberShipPre}/addVIPUserConsumption`,
        method: 'POST',
        params
    })
} // 执行订单时遇到VIP顾客调用

export function registerCorpMembershipAPI(corpName) {
    return axios({
        url: `${api.memberShipPre}/${corpName}/registerCorpMembership`,
        method: 'POST',
    })
}

export function freezeCorpVIPAPI(corpName) {
    return axios({
        url: `${api.memberShipPre}/${corpName}/freezeCorpVIP`,
        method: 'POST',
    })
}

export function restoreCorpVIPAPI(corpName) {
    return axios({
        url: `${api.memberShipPre}/${corpName}/restoreCorpVIP`,
        method: 'POST',
    })
}

export function getCorpVIPAPI(corpName) {
    return axios({
        url: `${api.memberShipPre}/${corpName}/getCorpVIP`,
        method: 'GET',
    })
}

export function addVIPCorpConsumptionAPI(params) {
    return axios({
        url: `${api.memberShipPre}/addVIPCorpConsumption`,
        method: 'POST',
        params
    })
}

export function VIPCorpCheckAPI(corpName) {
    return axios({
        url: `${api.memberShipPre}/${corpName}/CorpMembershipCheck`,
        method: 'GET',
    })
} // 网站制定企业优惠券时调用

export function getAllClientVIPAPI() {
    return axios({
        url: `/${api.memberShipPre}/allClientVIP`,
        method: 'GET'
    })
}

export function getAllCorpVIPAPI() {
    return axios({
        url: `/${api.memberShipPre}/allCorpVIP`,
        method: 'GET'
    })
}

export function formulateALevel(params) {
    return axios({
        url: `${api.memberShipPre/formulateALevel}`,
        method: 'POST',
        params
    })
} // 一次只能制定一条

export function getTheRequestOfLevel(level) {
    return axios({
        url: `${api.memberShipPre}/${level}/getTheRequestOfLevel`,
        method: 'GET'
    })
}
