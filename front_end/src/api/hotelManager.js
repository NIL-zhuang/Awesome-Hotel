import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function addRoomAPI(data) {
    return axios({
        url: `${api.hotelPre}/roomInfo`,
        method: 'POST',
        data,
    })
}
export function registerHotelMembershipAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data.id}/registerHotelMembership`,
        method: 'POST'
    })
}

