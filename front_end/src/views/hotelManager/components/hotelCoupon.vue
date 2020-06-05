<!-- created by glh  2020-05-21  -->
<template>
    <div>
        <div style="width: 100%; text-align: right; margin: 20px 0">
            <a-button type="primary" @click="addCoupon">
                <a-icon type="plus"/>添加优惠策略
            </a-button>
        </div>

        <a-table
                :columns="columns"
                :dataSource="couponList"
                bordered

        >
            <template slot="title">
                <h3>只有满减优惠有具体的优惠金额，其他类型的优惠券的优惠方式都是折扣。</h3>
            </template>
            <a-tag color="purple" slot="couponName" slot-scope="text">
                {{text}}
            </a-tag>
            <a-tag color="blue" slot="discount" slot-scope="disc">
                {{disc===0.00?'暂无':disc}}
            </a-tag>
            <a-tag color="pink" slot="discountMoney" slot-scope="money">
                {{money===0?'暂无':money}}
            </a-tag>
            <span slot="action" slot-scope="record">
                <a-button size="small" type="primary" @click="showCouponDetail(record)">查看详情</a-button>
                <a-divider type="vertical"></a-divider>
                <a-button size="small" type="danger" @click="deleteHotelCoupon(record.id)">删除优惠券</a-button>
            </span>
        </a-table>
        <AddCoupon></AddCoupon>
    </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddCoupon from './addCoupon'

const columns = [
    {
        title: '优惠券名称',
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
        title: '优惠金额（满减）',
        dataIndex: 'discountMoney',
        scopedSlots: {customRender: 'discountMoney'},
    },
    {
        title: '操作',
        scopedSlots: {customRender: 'action'}
    }
];

export default {
    name: "HotelCoupon",
    data() {
        return {
            columns
        }
    },
    components: {
        AddCoupon,
    },
    computed: {
        ...mapGetters([
            'couponList',
            'userInfo',
        ])
    },
    mounted() {
        this.getHotelCoupon(Number(this.userInfo.hotelID))
    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible',
            'set_couponDetailVisible',
            'set_currentCouponInfo',
        ]),
        ...mapActions([
            'getHotelCoupon',
            'deleteCoupon',
        ]),
        addCoupon() {
            this.set_addCouponVisible(true)
        },
        showCouponDetail(record) {
            this.set_currentCouponInfo(record)
            this.set_couponDetailVisible(true)
        },
        deleteHotelCoupon(couponId){
            this.deleteCoupon(couponId)
        }
    }
}
</script>

<style scoped>

</style>
