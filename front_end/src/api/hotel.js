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
    return axios({
        url: `${api.hotelPre}/searchHotel`,
        method: 'GET'
    })
}
