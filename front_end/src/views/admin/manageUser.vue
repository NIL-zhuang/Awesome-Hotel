<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店工作人员" key="1">
                <manage-hotel-manager></manage-hotel-manager>
            </a-tab-pane>

            <a-tab-pane tab="网站营销人员" key="2">
                <manage-sales-person></manage-sales-person>
            </a-tab-pane>

            <a-tab-pane tab="普通顾客" key="3">
                <manage-client></manage-client>
            </a-tab-pane>
        </a-tabs>
    </div>
</template>
<script>
    import manageHotelManager from "./components/manageHotelManager";
    import manageSalesPerson from "./components/manageSalesPerson";
    import manageClient from "./components/manageClient";
    export default {
        name: 'manageUser',
        data(){
            return {
            }
        },
        components: {
            manageHotelManager,
            manageSalesPerson,
            manageClient,
        },
    }
</script>
<style scoped lang="less">
    .manageUser-wrapper {
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>
