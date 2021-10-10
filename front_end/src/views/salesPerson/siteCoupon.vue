<!-- created by glh 2020-05-22 -->
<!-- 网站营销人员的优惠券界面 -->
<template>
    <div class="info-wrapper">
        <div style="width: 100%; text-align: right; margin: 20px 0">
            <a-button type="primary" @click="addSiteCoupon">
                <a-icon type="plus"/>添加优惠策略
            </a-button>
        </div>
        <site-coupon-table user-type="SalesPerson"></site-coupon-table>
        <addSiteCoupon></addSiteCoupon>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import addSiteCoupon from "./components/addSiteCoupon";
    import siteCouponTable from "../coupon/siteCouponTable";
    const columns = [
        {
            title: '优惠名称',
            dataIndex: 'couponName',
            scopedSlots: {customRender: 'couponName'},
        },
        {
            title: '折扣',
            dataIndex: 'discount',
            scopedSlots: {customRender: 'discount'},
        },
        {
            title: '优惠简介',
            dataIndex: 'description',
        },
        {
            title: '受用VIP等级（VIP指定商圈优惠）',
            dataIndex: 'vipLevel',
            scopedSlots: {customRender: 'vipLevel'},
        },
    ];
    export default {
        name: "siteCoupon",
        data() {
            return {
                columns,
            }
        },
        components: {
            siteCouponTable,
            addSiteCoupon,
        },
        computed: {
            ...mapGetters([
                'addSiteCouponVisible',
            ])
        },
        methods: {
            ...mapMutations([
                'set_addSiteCouponVisible',
            ]),
            ...mapActions([

            ]),
            addSiteCoupon() {
                this.set_addSiteCouponVisible(true)
            },
        }
    }
</script>

<style scoped>

</style>
<style scoped>
    .info-wrapper {
        padding: 50px;
    .chart {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-top: 20px
    }
    }
</style>