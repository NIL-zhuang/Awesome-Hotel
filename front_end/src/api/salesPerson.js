import { axios } from '@/utils/request'
const api = {
    userPre: '/api/user'
}
export function chargeCreditAPI(params) {
    console.log("in chargeCreditAPI")
    console.log(params)
    return axios({
        url: `${api.userPre}/${params.id}/chargeCredit`,
        method: 'POST',
        params,
    })
}

