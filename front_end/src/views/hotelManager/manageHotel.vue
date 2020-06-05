<template>
    <div class="manageHotel-wrapper">
        <a-tabs>
            <a-tab-pane tab="我的酒店" key="1">
                <modifyHotelInfo></modifyHotelInfo>
            </a-tab-pane>

            <a-tab-pane tab="订单管理" key="2">
                <a-table
                    :columns="columns_of_orders"
                    :dataSource="orderList"
                    bordered
                >
                    <a-tag slot="hotelName" color="orange" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <span slot="roomType" slot-scope="text">
                        <a-tag color="green" v-if="text === 'BigBed'">大床房</a-tag>
                        <a-tag color="green" v-if="text === 'DoubleBed'">双床房</a-tag>
                        <a-tag color="green" v-if="text === 'Family'">家庭房</a-tag>
                    </span>
                    <a-tag slot="checkInDate" color="red" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <a-tag slot="checkOutDate" color="red" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <a-tag slot="price" color="pink" slot-scope="text">
                        ￥ {{ text }}
                    </a-tag>
                    <a-tag slot="orderState" color="blue" slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showOrderDatail(record)">详情</a-button>

                        <a-divider type="vertical" v-if="record.orderState === '已预订'"></a-divider>

                        <a-popconfirm
                                title="确定执行该订单吗？"
                                @confirm="executeOrder(record)"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState === '已预订'"
                        >
                            <a-button type="default" size="small">执行</a-button>
                        </a-popconfirm>

                        <a-divider type="vertical"></a-divider>

                        <a-popconfirm
                            title="确定想删除该订单吗？"
                            @confirm="deleteOrder(record)"
                            okText="确定"
                            cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除</a-button>
                        </a-popconfirm>

                        <a-divider type="vertical" v-if="record.orderState !== '异常订单'"></a-divider>

                        <a-popconfirm
                                title="确定标记为异常订单吗？"
                                @confirm="markAsabnormalOrder(record)"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState !== '异常订单'"
                        >
                            <a-button type="dashed" size="small">异常</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="优惠管理" key="3">
                <hotel-coupon></hotel-coupon>
            </a-tab-pane>

            <a-tab-pane tab="客房管理" key="4">
                <hotel-room></hotel-room>
            </a-tab-pane>
        </a-tabs>
        <AddHotelModal></AddHotelModal>
        <AddRoomModal></AddRoomModal>
        <Coupon></Coupon>
        <OrderDetail></OrderDetail>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddHotelModal from '../admin/components/addHotelModal'
import AddRoomModal from './components/addRoomModal'
import Coupon from './components/coupon'
import OrderDetail from "../order/orderDetail";
import ModifyHotelInfo from "./components/hotelInfo";
import HotelCoupon from "./components/hotelCoupon";
import HotelRoom from "./components/hotelRoom";
const moment = require('moment');
import { message } from 'ant-design-vue';

const columns_of_hotels = [
    {
        title: '酒店名',
        dataIndex: 'name',
    },
    {
        title: '商圈',
        dataIndex: 'bizRegion',
    },
    {
        title: '地址',
        dataIndex: 'address',
    },
    {
        title: '酒店星级',
        dataIndex: 'hotelStar'
    },
    {
        title: '评分',
        dataIndex: 'rate',
    },
    {
        title: '简介',
        dataIndex: 'description',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
const columns_of_orders = [
    {
        title: '订单号',
        dataIndex: 'id',
    },
    {
        title: '酒店名',
        dataIndex: 'hotelName',
        scopedSlots: { customRender: 'hotelName' },
    },
    {
        title: '房型',
        dataIndex: 'roomType',
        scopedSlots: { customRender: 'roomType' }
    },
    {
        title: '入住时间',
        dataIndex: 'checkInDate',
        scopedSlots: { customRender: 'checkInDate' }
    },
    {
        title: '离店时间',
        dataIndex: 'checkOutDate',
        scopedSlots: { customRender: 'checkOutDate' }
    },
    {
        title: '入住人数',
        dataIndex: 'peopleNum',
    },
    {
        title: '房价',
        dataIndex: 'price',
        scopedSlots: { customRender: 'price' }
    },
    {
        title: '状态',
        filters: [{ text: '已预订', value: '已预订' }, { text: '已撤销', value: '已撤销' }, { text: '已入住', value: '已入住' },
             {text: '已完成', value: '已完成'}, { text: '异常订单', value: '异常订单' },],
        onFilter: (value, record) => record.orderState.includes(value),
        filterMultiple: false,
        dataIndex: 'orderState',
        scopedSlots: { customRender: 'orderState' }
    },
    {
      title: '操作',
      scopedSlots: { customRender: 'action' },
    },
  ];
export default {
    name: 'manageHotel',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns_of_hotels,
            columns_of_orders,
            form: this.$form.createForm(this, { name: 'manageHotel' }),
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
            },
        }
    },
    components: {
        HotelCoupon,
        AddHotelModal,
        AddRoomModal,
        Coupon,
        OrderDetail,
        ModifyHotelInfo,
        HotelRoom,
    },
    computed: {
        ...mapGetters([
            'userId',
            'orderList',
            'addHotelModalVisible',
            'addRoomModalVisible',
            'activeHotelId',
            'couponVisible',
            'orderDetailVisible',
            'userInfo',
            'hotelInfo',
        ]),
    },
    async mounted() {
        await this.getUserInfo()
        await this.getHotelInfo(Number(this.userInfo.hotelID))
    },
    methods: {
        ...mapMutations([
            'set_addHotelModalVisible',
            'set_addRoomModalVisible',
            'set_couponVisible',
            'set_activeHotelId',
            'set_orderDetailVisible',
            'set_orderInfo',
        ]),
        ...mapActions([
            'getHotelOrders',
            'getHotelCoupon',
            'execOrder',
            'getUserInfo',
            'getHotelInfo',
        ]),
        addHotel() {
            this.set_addHotelModalVisible(true)
        },
        addRoom(record) {
            this.set_activeHotelId(record.id)
            this.set_addRoomModalVisible(true)
        },
        showCoupon(record) {
            this.set_activeHotelId(record.id)
            this.set_couponVisible(true)
            this.getHotelCoupon()
        },
        deleteHotel(){

        },
        deleteOrder(record){
            console.log(record)
        },
        showOrderDatail(record) {
            this.set_orderInfo(record)
            this.set_orderDetailVisible(true)
        },
        executeOrder(record) {
            let checkInDate = new Date(record.checkInDate);
            let now = new Date();
            if(checkInDate.toLocaleDateString()===now.toLocaleDateString()) {
                this.execOrder(record.id); // 执行订单
                message.success('执行订单')
            }
            else {
                message.warning('还未到入住时间')
            }
        },
    }
}
</script>
<style scoped lang="less">
    .manageHotel-wrapper {
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
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>

