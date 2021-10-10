<template>
    <a-result
            status="success"
            title="订单提交成功"
            style="height: 1500px"
    >
        <template>
            <a-button type="primary" @click="back">
                继续预定
            </a-button>
            <a-button @click="home" style="margin-left: 30px">
                返回首页
            </a-button>
        </template>
    </a-result>
</template>

<script>
    import {mapGetters} from "vuex";

    export default {
        name: "SuccessOrder",
        computed: {
            ...mapGetters([
                'currentHotelInfo'
            ])
        },
        methods: {
            back() {
                this.$router.push({name: 'hotelDetail', params: {hotelId: this.currentHotelInfo.id}})
            },
            home() {
                this.$router.push('/homePage')
            }
        }
    }
</script>

<style scoped>

</style>