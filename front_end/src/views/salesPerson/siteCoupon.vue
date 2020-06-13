<!-- created by glh 2020-05-22 -->
<!-- 网站营销人员的优惠券界面 -->
<template>
    <div class="info-wrapper">
        <div style="width: 100%; text-align: right; margin: 20px 0">
            <a-button type="primary" @click="addSiteCoupon">
                <a-icon type="plus"/>添加优惠策略
            </a-button>
        </div>

        <a-table
                :columns="columns"

                bordered
        >
            <a-tag color="red" slot="couponName" slot-scope="text">
                {{text}}
            </a-tag>
        </a-table>
        <addSiteCoupon></addSiteCoupon>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import addSiteCoupon from "./components/addSiteCoupon";
    const columns = [
        {
            title: '优惠类型',
            dataIndex: 'couponName',
            scopedSlots: {customRender: 'couponName'},
        },
        {
            title: '折扣',
            dataIndex: 'discount',
        },
        {
            title: '优惠简介',
            dataIndex: 'description',
        },
        {
            title: '优惠金额',
            dataIndex: 'discountMoney',
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