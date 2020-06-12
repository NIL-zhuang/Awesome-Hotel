import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function getHotelsAPI() {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'GET',
    })
}
export function getHotelByIdAPI(hotelId) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/detail`,
        method: 'GET',
    })
}
export function searchHotelAPI(data) {
    console.log(data)
    return axios({
        url: `${api.hotelPre}/searchHotel`,
        method: 'POST',
        data
    })
}
export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data,
    })
}
export function deleteHotelAPI(hotelId) {
    console.log('in api: ' + hotelId)
    return axios({
        url: `${api.hotelPre}/${hotelId}/deleteHotel`,
        method: 'POST'
    })
}
