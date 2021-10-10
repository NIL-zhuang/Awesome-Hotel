<template>
    <a-carousel autoplay>
        <div class="privilege">
            <h2>企业会员</h2>
            <h3 v-for="item in this.corpLevels" :key="item.level">
                LV{{ item.level }}: 累计消费￥{{ item.request }} (减免{{ item.reduction * 100 }}%) <br/>
            </h3>
        </div>

        <div class="siteCoupon" v-for="coupon in corpCouponList" :key="coupon.id">
            <div>
                <h2>企业会员特惠</h2>
                <h3>{{ coupon.corporateName }}</h3>
                <h3>折扣{{ coupon.discount * 100 }}%</h3>
            </div>
        </div>
    </a-carousel>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "CorpMembershipCoupon",
        data() {
            return {

            }
        },
        computed: {
            ...mapGetters([
                'levelConsumption',
                'levels',
                'corpLevels',
                'siteCouponList'
            ]),
            corpCouponList() {
                return this.siteCouponList.filter(function(x) {
                    return x.couponType === 3
                })
            }
        },
        async mounted() {
            await this.getClientLevel()
            await this.getCorpLevel()
            await this.getSiteCoupon()
        },
        methods: {
            ...mapActions([
                'getClientLevel',
                'getCorpLevel',
                'getSiteCoupon',
            ]),
        },
    }
</script>

<style scoped>

    .ant-carousel {
        width: 800px;
    }

    .ant-carousel >>> .slick-slide {
        text-align: center;
        height: 600px;
        background-image: url('../../../assets/background.png');
        overflow: hidden;
    }

    .ant-carousel >>> .slick-slide h3 {
        color: #fff;
    }

    .privilege {
        margin-top: 180px;
        font-size: large;
    }

    .siteCoupon {
        margin-top: 230px;
        font-size: large;
    }

    h2 {
        color: #ffffff;
    }
</style>
