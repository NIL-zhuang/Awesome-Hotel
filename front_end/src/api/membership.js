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
export function registerCorpMembershipAPI(params) {
    return axios({
        url: `${api.memberShipPre}/registerCorpMembership`,
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


