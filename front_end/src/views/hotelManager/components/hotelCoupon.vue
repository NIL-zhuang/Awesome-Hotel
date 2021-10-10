<!-- created by glh  2020-05-21  -->
<template>
    <div>
        <div style="width: 100%; text-align: right; margin: 20px 0">
            <a-button type="primary" @click="addCoupon">
                <a-icon type="plus"/>添加优惠策略
            </a-button>
        </div>
        <HotelCouponTable :hotel-id="this.userInfo.hotelID" :user-type="this.userInfo.userType"></HotelCouponTable>
        <CouponDetail></CouponDetail>
        <AddCoupon></AddCoupon>
    </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddCoupon from './addCoupon'
import CouponDetail from '../../coupon/couponDetail'
import HotelCouponTable from '../../coupon/hotelCouponTable'

export default {
    name: "HotelCoupon",
    data() {
        return {

        }
    },
    components: {
        AddCoupon,
        CouponDetail,
        HotelCouponTable,
    },
    computed: {
        ...mapGetters([
            'userInfo',
        ])
    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible',
        ]),
        ...mapActions([

        ]),
        addCoupon() {
            this.set_addCouponVisible(true)
        },
    }
}
</script>

<style scoped>

</style>
