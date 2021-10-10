<template>
    <div class="box">
        <div class="backBar">
            <a-button @click="backToHome" class="backButton" icon="left-circle" type="primary">
                返回首页
            </a-button>
        </div>
        <div class="searchresult" v-if="searchList.length>0">
            <div class="card-wrapper">
                <HotelCard :hotel="item" :key="item.index" @click.native="jumpToDetails(item.id)"
                           v-for="item in searchList"></HotelCard>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import HotelCard from "./components/hotelCard"


    export default {
        name: "searchHotel",
        components: {
            HotelCard,
        },
        computed: {
            ...mapGetters([
                'searchList'
            ])
        },
        data() {
            return {}
        },
        // destroyed() {
        //     this.set_searchList([]) //离开页面时清除搜索的结果
        // },
        methods: {
            ...mapMutations([
                'set_searchList'
            ]),
            backToHome() {
                this.$router.push('/homePage')
            },
            jumpToDetails(id) {
                this.$router.push({name: 'hotelDetail', params: {hotelId: id}})
            },
        }
    }
</script>

<style lang="less" scoped>

    .box {
        padding: 20px;
        align-content: center;
    }

    .backBar {
        padding: 20px;
        margin-top: 10px;
        margin-bottom: 10px;
    }

    .backButton {
        width: 200px;
        height: auto;
        padding: 5px;
        text-align: center;
        float: left;
        font-size: large;
        border-radius: 20px;
    }

    .searchbox {
        width: 100%;
        align-self: center;
    }

    .searchresult {
        width: 100%;
        padding: 50px 0;
        text-align: center;

        .card-wrapper {
            display: flex;
            justify-content: flex-start;
            flex-wrap: wrap;
            min-height: 500px;
        }
    }
</style>