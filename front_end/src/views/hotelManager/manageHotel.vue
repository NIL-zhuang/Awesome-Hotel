<template>
    <div class="manageHotel-wrapper">
        <a-tabs>
            <a-tab-pane key="1" style="margin-top: 30px" tab="我的酒店">
                <modifyHotelInfo></modifyHotelInfo>
            </a-tab-pane>

            <a-tab-pane key="2" tab="订单管理">
                <a-table
                        :columns="columns_of_orders"
                        :dataSource="orderList"
                        :rowKey="record => record.id"
                        :locale="{emptyText: '暂时没有订单'}"
                        bordered
                        style="background-color: white; padding: 10px; border-radius: 20px"
                >

                    <span slot="roomType" slot-scope="text">
                        <a-tag color="pink" v-if="text === 'BigBed'">大床房</a-tag>
                        <a-tag color="pink" v-if="text === 'DoubleBed'">双床房</a-tag>
                        <a-tag color="pink" v-if="text === 'Family'">家庭房</a-tag>
                    </span>
                    <a-tag color="red" slot="checkInDate" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <a-tag color="red" slot="checkOutDate" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <a-tag color="pink" slot="price" slot-scope="text">
                        ￥ {{ text }}
                    </a-tag>
                    <a-tag color="blue" slot="orderState" slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button @click="showOrderDatail(record)" size="small" type="primary">订单详情</a-button>

                        <a-divider type="vertical" v-if="record.orderState === '未入住'"></a-divider>

                        <a-popconfirm
                                @confirm="checkIn(record)"
                                cancelText="取消"
                                okText="确定"
                                title="确定执行该入住吗？"
                                v-if="record.orderState === '未入住'"
                        >
                            <a-button size="small" type="default">入住</a-button>
                        </a-popconfirm>

                        <a-divider type="vertical" v-if="record.orderState === '已入住'"></a-divider>

                        <a-popconfirm
                                @confirm="finish(record)"
                                cancelText="取消"
                                okText="确定"
                                title="确定完成该订单吗？"
                                v-if="record.orderState === '已入住'"
                        >
                            <a-button size="small" type="default">完成</a-button>
                        </a-popconfirm>

                        <a-divider type="vertical" v-if="record.orderState === '异常订单'"></a-divider>

                        <a-popconfirm
                                @confirm="abnormal(record)"
                                cancelText="取消"
                                okText="确定"
                                title="确定为用户补登入住？"
                                v-if="record.orderState === '异常订单'"
                        >
                            <a-button size="small" type="danger">处理异常</a-button>
                        </a-popconfirm>

                        <a-divider type="vertical" v-if="record.orderState === '未入住'"></a-divider>

                        <a-popconfirm
                                @confirm="markAbnormal(record)"
                                cancelText="取消"
                                okText="确定"
                                title="确定将该订单标记为异常订单？"
                                v-if="record.orderState === '未入住'"
                        >
                            <a-button size="small" type="danger">标记异常</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane key="3" tab="优惠管理">
                <hotel-coupon></hotel-coupon>
            </a-tab-pane>

            <a-tab-pane key="4" tab="客房管理">
                <hotel-room></hotel-room>
            </a-tab-pane>
        </a-tabs>
        <!--        <AddHotelModal></AddHotelModal>-->
        <!--        <AddRoomModal></AddRoomModal>-->
        <!--        <Coupon></Coupon>-->
        <OrderDetail></OrderDetail>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import OrderDetail from "../order/orderDetail";
    import ModifyHotelInfo from "./components/hotelInfo";
    import HotelCoupon from "./components/hotelCoupon";
    import HotelRoom from "./components/hotelRoom";
    import {message} from 'ant-design-vue';

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
            scopedSlots: {customRender: 'action'},
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
            scopedSlots: {customRender: 'hotelName'},
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            scopedSlots: {customRender: 'roomType'}
        },
        {
            title: '入住时间',
            dataIndex: 'checkInDate',
            scopedSlots: {customRender: 'checkInDate'},
            sorter: function (x, y) {
                let checkInDateA = new Date(x.checkInDate)
                let checkInDateB = new Date(y.checkInDate)
                if (checkInDateA < checkInDateB) {
                    return 1
                } else if (checkInDateA > checkInDateB) {
                    return -1
                } else {
                    return 0
                }
            }
        },
        {
            title: '离店时间',
            dataIndex: 'checkOutDate',
            scopedSlots: {customRender: 'checkOutDate'},
            sorter: function (x, y) {
                let checkInDateA = new Date(x.checkInDate)
                let checkInDateB = new Date(y.checkInDate)
                if (checkInDateA < checkInDateB) {
                    return 1
                } else if (checkInDateA > checkInDateB) {
                    return -1
                } else {
                    return 0
                }
            }
        },
        {
            title: '入住人数',
            dataIndex: 'peopleNum',
        },
        {
            title: '房价',
            dataIndex: 'price',
            scopedSlots: {customRender: 'price'}
        },
        {
            title: '状态',
            filters: [{text: '未入住', value: '未入住'}, {text: '已撤销', value: '已撤销'}, {text: '已入住', value: '已入住'},
                {text: '已完成', value: '已完成'}, {text: '异常订单', value: '异常订单'},],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState',
            scopedSlots: {customRender: 'orderState'}
        },
        {
            title: '操作',
            scopedSlots: {customRender: 'action'},
        },
    ];
    export default {
        name: 'manageHotel',
        data() {
            return {
                formLayout: 'horizontal',
                pagination: {},
                columns_of_hotels,
                columns_of_orders,
                form: this.$form.createForm(this, {name: 'manageHotel'}),
                formItemLayout: {
                    labelCol: {
                        xs: {span: 12},
                        sm: {span: 6},
                    },
                    wrapperCol: {
                        xs: {span: 24},
                        sm: {span: 16},
                    },
                },
            }
        },
        components: {
            HotelCoupon,
            OrderDetail,
            ModifyHotelInfo,
            HotelRoom,
        },
        computed: {
            ...mapGetters([
                'orderList',
                'addHotelModalVisible',
                'addRoomModalVisible',
                'activeHotelId',
                'couponVisible',
                'orderDetailVisible',
                'userInfo',
                'hotelInfo',
                'handleAbnormalOrderVisible',
            ]),
        },
        async mounted() {
            await this.getUserInfo()
            this.getHotelInfo(Number(this.userInfo.hotelID))
            this.getHotelOrders(Number(this.userInfo.hotelID))
        },
        methods: {
            ...mapMutations([
                'set_addHotelModalVisible',
                'set_addRoomModalVisible',
                'set_couponVisible',
                'set_activeHotelId',
                'set_orderDetailVisible',
                'set_orderInfo',
                'set_handleAbnormalOrderVisible',
                'set_currentHotelId',
            ]),
            ...mapActions([
                'getHotelOrders',
                'getHotelCoupon',
                'checkInOrder',
                'finishOrder',
                'handleAbnormalOrder',
                'getUserInfo',
                'getHotelInfo',
                'getHotelOrders',
                'markAbnormalOrder',
                'getHotelById',
            ]),
            addHotel() {
                this.set_addHotelModalVisible(true)
            },
            addRoom(record) {
                this.set_activeHotelId(record.id)
                this.set_addRoomModalVisible(true)
            },
            // showCoupon(record) {
            //     this.set_activeHotelId(record.id)
            //     this.set_couponVisible(true)
            //     this.getHotelCoupon()
            // },
            deleteHotel() {

        },
        showOrderDatail(record) {
            this.set_orderInfo(record)
            this.set_currentHotelId(record.hotelId)
            this.getHotelById(record.hotelId)
            this.set_orderDetailVisible(true)
        },
       checkIn(record) {
            let checkInDate = new Date(record.checkInDate);
            let now = new Date();
            if(checkInDate.toLocaleDateString()===now.toLocaleDateString()) {
                this.checkInOrder(record.id).then(() => {
                    this.getHotelOrders(Number(this.userInfo.hotelID))
                })
            }
            else {
                message.warning('不在入住时间段')
            }
        },
       finish(record) {
           this.finishOrder(record.id).then(() => {
               this.getHotelOrders(Number(this.userInfo.hotelID))
           })
       },
       //酒店管理员处理异常订单，可以为用户补登入住，但是有时间限制
       abnormal(record) {
           let checkInDate = new Date(record.checkInDate);
           let now = new Date();
           if(checkInDate.toLocaleDateString() !== now.toLocaleDateString()) {
               message.error('已过了入住时间，无法处理异常订单')
               return
           }
            const params = {
                orderId: record.id,
                ratio: 1
            }
            this.handleAbnormalOrder(params).then(() => {
                this.checkInOrder(record.id).then(() => {
                    this.getHotelOrders(Number(this.userInfo.hotelID))
                })
            })
       },
        markAbnormal(record) {
            this.markAbnormalOrder(record.id).then(() => {
                this.getHotelOrders(Number(this.userInfo.hotelID))
            })
        }

        }
    }
</script>
<style lang="less" scoped>
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

