<template>
    <div style="margin-top: 50px;">
        <div style="float: left; margin-left: 5%">
            <membership-coupon ></membership-coupon>
        </div>
        <div style="float: left; margin-left: 5%; text-align: center; width: 270px">
            <a-card style="width: 270px;">
                <p>VIP网站会员特权</p>
                <img
                    alt="example"
                    src="@/assets/membershipLogo.svg"
                    style="width: 20px; height: 20px"
                />
                <span> 尊贵标识</span>
                <a-divider type="vertical"></a-divider>
                <img
                    alt="example"
                    src="@/assets/couponLogo.svg"
                    style="width: 20px; height: 20px"
                />
                <span> 丰富优惠</span>
            </a-card>
            <a-button type="primary" @click="registerSiteMembership" style="margin-top: 20px"><a-icon type="user" />注册网站会员</a-button>

            <a-card style="width: 270px; margin-top: 20px">
                <p>VIP企业会员特权</p>
                <img
                        alt="example"
                        src="@/assets/couponLogo.svg"
                        style="width: 20px; height: 20px"
                />
                <span>企业专享优惠</span>
            </a-card>
            <a-button type="primary" @click="registerCorporationMembership" style="margin-top: 20px"><a-icon type="user" />注册企业会员</a-button>


        </div>
        <RegisterSiteMembership></RegisterSiteMembership>
        <RegisterCorporationMembership></RegisterCorporationMembership>
    </div>
</template>

<script>
import membershipCoupon from "./components/membershipCoupon";
import RegisterSiteMembership from "./components/RegisterSiteMembership";
import RegisterCorporationMembership from "./components/RegisterCorporationMembership";
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: "usermembership",
    components: {
        membershipCoupon,
        RegisterSiteMembership,
        RegisterCorporationMembership
    },
    computed: {
    },
    methods: {
        ...mapMutations([
            'set_registerSiteMembershipModalVisible',
            'set_registerCorporationMembershipModalVisible',
        ]),
        registerSiteMembership() {
            this.set_registerSiteMembershipModalVisible(true);
        },
        registerCorporationMembership() {
            this.set_registerCorporationMembershipModalVisible(true)
        },
    }

}
</script>

<style scoped>

</style>