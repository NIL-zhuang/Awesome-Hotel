<template>
    <a-carousel autoplay>
        <div class="privilege">
            <h3 v-for="item in this.levelConsumption" :key="item.level">
                LV{{ item.level }}: 累计消费{{ item.consumption }} 减免{{ item.level }}% <br/>
            </h3>
        </div>
        <!--  这里可以再补充优惠券的部分，暂时还没想好怎么展示   -->
    </a-carousel>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "membershipCoupon",
        data() {
            return {

            }
        },
        computed: {
            ...mapGetters([
                'levelConsumption',
            ]),
        },
        methods: {
            ...mapActions([
                'getTheRequestOfLevel'
            ]),
        },
        async mounted() {
            await this.getTheRequestOfLevel()
        },
    }
</script>

<style scoped>
    /* For demo */
    .ant-carousel {
        width: 600px;
    }

    .ant-carousel >>> .slick-slide {
        text-align: center;
        height: 340px;

        background: #83c2f8;
        overflow: hidden;
    }

    .ant-carousel >>> .slick-slide h3 {
        color: #fff;
    }

    .privilege {
        margin-top: 90px;
    }
</style>
