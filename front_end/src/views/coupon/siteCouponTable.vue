<template>
    <div>
        <a-table
                :columns="columns"
                :dataSource="siteCouponList"
                :locale="{emptyText: '暂时没有网站优惠'}"
                :rowKey="record => record.id"
                bordered
                style="padding: 10px; border-radius: 20px"
        >
            <a-tag color="pink" slot="type" slot-scope="t">{{t===1 ? '节日优惠' : t===2 ? 'VIP指定商圈优惠' : '合作企业优惠'}}</a-tag>
            <a-tag color="purple" slot="couponName" slot-scope="text">{{text}}</a-tag>
            <a-tag color="red" slot="discount" slot-scope="text">{{text*100}}%</a-tag>
            <span slot="actions" slot-scope="record">
            <a-button @click="showCouponDetail(record)" icon="eye" size="small" type="primary">
                查看详情
            </a-button>
            <a-divider type="vertical" v-if="userType === 'SalesPerson'"/>
            <a-button @click="deleteHotelCoupon(record.id)" icon="minus-circle" size="small"
                      type="danger" v-if="userType === 'SalesPerson'">
                删除优惠
            </a-button>
        </span>
        </a-table>
        <CouponDetail></CouponDetail>
    </div>
</template>

<script>
    import {mapActions, mapMutations, mapGetters} from "vuex";
    import CouponDetail from './couponDetail'

    export default {
        name: "siteCouponTable",
        props: ['userType'],
        components: {
            CouponDetail,
        },
        data() {
            return {
                columns: [
                    {
                        title: '优惠类型',
                        dataIndex: 'couponType',
                        scopedSlots: {customRender: 'type'},
                        filters: [{text: '节日优惠', value: 1}, {text: 'VIP指定商圈优惠', value: 2}, {text: '合作企业优惠', value: 3}],
                        onFilter: (value, record) => record.couponType === value,
                        filterMultiple: false,
                    },
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
                        title: '操作',
                        scopedSlots: {customRender: 'actions'}
                    }]
            }
        },
        mounted() {
            this.getSiteCoupon()
        },
        computed: {
            ...mapGetters([
                'siteCouponList',
            ])
        },
        methods: {
            ...mapMutations([
                'set_couponDetailVisible',
                'set_currentCouponInfo',
            ]),
            ...mapActions([
                'getSiteCoupon',
                'deleteSiteCoupon',
            ]),
            showCouponDetail(record) {
                this.set_currentCouponInfo(record)
                this.set_couponDetailVisible(true)
            },
            deleteHotelCoupon(couponId) {
                this.deleteSiteCoupon(couponId)
            }
        },
    }
</script>

<style scoped>

</style>
