<template>
    <div>
        <a-table
                :columns="columns"
                :data-source="couponList"
                :rowKey="record=>record.id"
                :locale="{ emptyText: '暂时没有优惠'}"
                bordered
                style="background-color: white; padding: 10px; border-radius: 20px"
        >
            <template slot="title">
                <h3>只有满减优惠有具体的优惠金额，其他类型的优惠券的优惠方式都是折扣。</h3>
            </template>
            <a-tag color="purple" slot="couponName" slot-scope="text">
                {{text}}
            </a-tag>
            <a-tag color="blue" slot="discount" slot-scope="disc">
                {{disc===0.00?'满减':(disc*100) + '%'}}
            </a-tag>
            <a-tag color="pink" slot="discountMoney" slot-scope="money">
                {{money===0?'非满减':money}}
            </a-tag>
            <span slot="action" slot-scope="record">
                <a-button @click="showCouponDetail(record)" icon="eye" size="small" type="primary">查看详情</a-button>
                <a-divider type="vertical" v-if="userType==='HotelManager'"/>
                <a-button @click="deleteHotelCoupon(record.id)" icon="minus-circle" size="small" type="danger"
                          v-if="userType==='HotelManager'">删除优惠</a-button>
            </span>
        </a-table>
        <CouponDetail></CouponDetail>
    </div>
</template>

<script>
    import CouponDetail from "./couponDetail";
    import {mapActions, mapGetters, mapMutations} from "vuex";

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
        name: "hotelCouponTable",
        props: ['hotelId', 'userType'],
        data() {
            return {
                columns
            }
        },
        components: {
            CouponDetail,
        },
        computed: {
            ...mapGetters([
                'couponList',
            ])
        },
        mounted() {
            this.getHotelCoupon(this.hotelId)
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
            showCouponDetail(record) {
                this.set_currentCouponInfo(record)
                this.set_couponDetailVisible(true)
            },
            deleteHotelCoupon(couponId) {
                this.deleteCoupon({
                    couponId: couponId,
                    hotelId: this.hotelId
                })
            }
        }
    }
</script>

<style scoped>

</style>