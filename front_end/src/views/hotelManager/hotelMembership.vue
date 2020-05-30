<!-- created by glh 2020-05-20 -->
<template>
    <div style="margin-top: 50px;">
        <div style="float: left; margin-left: 5%">
            <hotelMembershipCoupon></hotelMembershipCoupon>
        </div>
        <div style="float: left; margin-left: 5%; text-align: center; width: 270px">
            <a-card style="width: 270px;">
                <p>VIP会员特权</p>
                <img
                        alt="example"
                        src="@/assets/couponLogo.svg"
                        style="width: 20px; height: 20px"
                />
                <span>合作优惠</span>
            </a-card>
            <a-button type="primary" @click="registerMembership" style="margin-top: 20px"><a-icon type="user" />注册会员</a-button>
            <register-hotel-membership></register-hotel-membership>
        </div>

    </div>
</template>

<script>
    import hotelMembershipCoupon from "./components/hotelMembershipCoupon";
    import registerHotelMembership from "./components/registerHotelMembership";
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "hotelMembership",
        components: {
            hotelMembershipCoupon,
            registerHotelMembership,
        },
        computed: {
            ...mapGetters([
                'registerHotelMembershipModalVisible'
            ])
        },
        methods: {
            ...mapMutations([
                'set_registerHotelMembershipModalVisible'
            ]),
            registerMembership() {
                this.set_registerHotelMembershipModalVisible(true);
            }
        },
    }
</script>

<style scoped>

</style>