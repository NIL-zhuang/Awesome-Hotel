<template>
    <div>
        <div v-if="userInfo.userType==='Client'">
            <ClientHomePage></ClientHomePage>
        </div>
        <div v-if="userInfo.userType==='HotelManager'">
            <ManagerHomePage></ManagerHomePage>
            <p>展示酒店收到的订单</p>
        </div>
        <div v-if="userInfo.userType==='Admin'">
            <h1>网站人员看到的页面</h1>
        </div>
        <div v-if="userInfo.userType==='SalesPerson'">
            <h1>网站营销人员看到的页面</h1>
            <SalesHomePage></SalesHomePage>
        </div>
    </div>
</template>

<script>
    import {mapGetters} from 'vuex'
    import ClientHomePage from './components/clientHomePage'
    import ManagerHomePage from "./components/managerHomePage"
    import SalesHomePage from "./components/salesHomePage"

    export default {
        name: "homePage",
        components:  {
            ManagerHomePage,
            SalesHomePage,
            ClientHomePage,

        },
        computed: {
            ...mapGetters([
                'userInfo',
                'hotelList',
                'hotelListLoading',
                'onceOrderedList',
            ])
        },
    }
</script>

<style scoped>

</style>