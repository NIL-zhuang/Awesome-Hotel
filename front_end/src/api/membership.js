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

export function ClientVIPLevelUpAPI(id) {
    return axios({
        url: `${api.memberShipPre}/${id}/addVIPUserConsumption`,
        method: 'POST',
    })
} // 每次执行订单增加消费额后判断是否要升级

export function registerCorpMembershipAPI(params) {
    return axios({
        url: `${api.memberShipPre}/registerCorpMembership`,
        method: 'POST',
        params
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

export function CorpVIPLevelUpAPI(corpName) {
    return axios({
        url: `${api.memberShipPre}/${corpName}/CorpVIPLevelUp`,
        method: 'POST',
    })
}

export function VIPCorpCheckAPI(corpName) {
    return axios({
        url: `${api.memberShipPre}/${corpName}/CorpMembershipCheck`,
        method: 'GET',
    })
} // 网站制定企业优惠券时调用

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
