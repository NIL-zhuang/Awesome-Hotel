<template>
    <div class="homePage">
        <div v-if="userInfo.userType==='Client'">
            <ClientHomePage></ClientHomePage>
        </div>
        <div v-if="userInfo.userType==='HotelManager'">
            <ManagerHomePage></ManagerHomePage>
        </div>
        <div v-if="userInfo.userType==='Admin'">
            <AdminHomePage></AdminHomePage>
        </div>
        <div v-if="userInfo.userType==='SalesPerson'">
            <SalesHomePage></SalesHomePage>
        </div>
    </div>
</template>

<script>

    import {mapGetters} from 'vuex'
    import ClientHomePage from './components/clientHomePage'
    import ManagerHomePage from "./components/managerHomePage"
    import AdminHomePage from "./components/adminHomePage"
    import SalesHomePage from "./components/salesHomePage"

    export default {
        name: "HomePage",
        components:  {
            AdminHomePage,
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
    .homePage {

    }

</style>