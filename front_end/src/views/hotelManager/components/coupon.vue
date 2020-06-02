<template>
    <div>
        <a-modal
                :footer="null"
                :visible="couponVisible"
                @cancel="cancel"
                title="优惠策略"
                width="900px"
        >
            <div style="width: 100%; text-align: right; margin: 20px 0">
                <a-button @click="addCoupon" type="primary">
                    <a-icon type="plus"/>
                    添加优惠策略
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
                    {{disc===0.00?'暂无':100*disc+'%'}}
                </a-tag>
                <a-tag color="pink" slot="discountMoney" slot-scope="money">
                    {{money===0?'暂无':money}}
                </a-tag>
                <span slot="action" slot-scope="record">
                <a-button size="small" type="primary" @click="showCouponDetail(record)">查看详情</a-button>
                <a-divider type="vertical"></a-divider>
                <a-popconfirm
                        @confirm="deleteHotelCoupon(record.id)"
                        cancelText="取消"
                        okText="确定"
                        title="确定想删除该优惠吗？"
                >
                    <a-button size="small" type="danger">删除优惠</a-button>
                </a-popconfirm>
            </span>
            </a-table>
        </a-modal>
        <AddCoupon></AddCoupon>
        <CouponDetail></CouponDetail>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import AddCoupon from './addCoupon'
    import CouponDetail from "../../coupon/couponDetail";

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
        name: 'coupon',
        data() {
            return {
                columns
            }
        },
        components: {
            CouponDetail,
            AddCoupon,
        },
        computed: {
            ...mapGetters([
                'couponVisible',
                'couponList',
            ])
        },
        methods: {
            ...mapMutations([
                'set_addCouponVisible',
                'set_couponVisible',
                'set_couponDetailVisible',
                'set_currentCouponInfo',
            ]),
            ...mapActions([
                'deleteCoupon',
            ]),
            cancel() {
                this.set_couponVisible(false)
            },
            addCoupon() {
                this.set_addCouponVisible(true)
                this.set_couponVisible(false)
            },
            deleteHotelCoupon(id) {
                this.deleteCoupon(id)
            },
            showCouponDetail(record) {
                this.set_currentCouponInfo(record)
                this.set_couponDetailVisible(true)
            }
        },
    }
</script>
<style scoped>

</style>