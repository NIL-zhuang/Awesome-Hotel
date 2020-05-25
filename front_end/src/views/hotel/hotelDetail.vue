<template>
    <a-layout>
        <a-layout-content>
            <div class="hotelDetailCard">
                <h1>
                    {{ currentHotelInfo.title }}
                </h1>
                <div class="hotel-info">
                    <a-card style="width: 240px">
                        <img
                                alt="example"
                                src="@/assets/cover.jpeg"
                                slot="cover"
                                referrerPolicy="no-referrer"
                        />
                    </a-card>
                    <div class="info">
                        <div class="items" v-if="currentHotelInfo.name">
                            <span class="label">酒店名称: </span>
                            <span class="value">{{ currentHotelInfo.name }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.address">
                            <span class="label">地址: </span>
                            <span class="value">{{ currentHotelInfo.address }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.rate">
                            <span class="label">评分: </span>
                            <span class="value">{{ currentHotelInfo.rate }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">星级: </span>
                            <a-rate style="font-size: 15px" :value="currentHotelInfo.rate" :disabled="true"/>
                        </div>
                        <div class="items" v-if="currentHotelInfo.descriptions">
                            <span class="label">酒店简介:</span>
                            <span class="value">{{ currentHotelInfo.description }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.phoneNum">
                            <span class="label">联系电话:</span>
                            <span class="value">{{ currentHotelInfo.phoneNum }}</span>
                        </div>
                    </div>
                </div>
                <a-divider></a-divider>
                <a-tabs>
                    <a-tab-pane tab="房间信息" key="1">
                        <a-row>
                            <h1>入住-退房时间</h1>
                        </a-row>
                        <a-range-picker
                                format="YYYY-MM-DD"
                                @change="changeDate"
                                v-decorator="[
                                    'date',
                                    { rules: [{ required: true, message: '请选择入住时间' }]},
                                ]"
                                :default-value="dateRange"
                        />
                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
                    </a-tab-pane>
                    <a-tab-pane tab="酒店详情" key="2">
                        <HotelOutline></HotelOutline>
                    </a-tab-pane>
                    <a-tab-pane tab="历史订单" key="3">
                        <a-page-header
                                style="border: 1px solid rgb(235, 237, 240)"
                                title="您在这家酒店有如下的订单"
                                sub-title="若要对订单进行撤销或者申诉，请前往个人中心-我的订单进行相关操作"
                        />
                        <a-table
                                :columns="columns_of_orders"
                                :dataSource="userOrderList.filter(order=>order.hotelId===currentHotelInfo.id)"
                                bordered
                        >
                            <a-tag slot="createDate" color="red" slot-scope="text">
                                {{text}}
                            </a-tag>
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
                            <span slot="price" slot-scope="text">
                                <a-tag color="pink">￥ {{ text }}</a-tag>
                            </span>
                            <a-tag slot="orderState" color="blue" slot-scope="text">
                                {{ text }}
                            </a-tag>
                        </a-table>
                    </a-tab-pane>
                </a-tabs>
            </div>
        </a-layout-content>
    </a-layout>
</template>
<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import RoomList from './components/roomList'
    import HotelOutline from './components/hotelOutline'

    const columns_of_orders = [
        {
            title: '订单号',
            dataIndex: 'id',
        },
        {
            title: '下单时间',
            dataIndex: 'createDate',
            scopedSlots: {customRender: 'createDate'},
        },
        {
            title: '酒店名',
            dataIndex: 'hotelName',
            scopedSlots: {customRender: 'hotelName'}
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            scopedSlots: {customRender: 'roomType'}
        },
        {
            title: '入住时间',
            dataIndex: 'checkInDate',
            scopedSlots: {customRender: 'checkInDate'}
        },
        {
            title: '离店时间',
            dataIndex: 'checkOutDate',
            scopedSlots: {customRender: 'checkOutDate'}
        },
        {
            title: '入住人数',
            dataIndex: 'peopleNum',
        },
        {
            title: '订单价格',
            dataIndex: 'price',
            scopedSlots: {customRender: 'price'}
        },
        {
            title: '状态',
            filters: [{text: '已预订', value: '已预订'}, {text: '已撤销', value: '已撤销'}, {text: '已入住', value: '已入住'},
                {text: '已完成', value: '已完成'}, {text: '异常订单', value: '异常订单'}],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState', scopedSlots: {customRender: 'orderState'},
            filterMultiple: false,
        },
    ];
    export default {
        name: 'hotelDetail',
        components: {
            RoomList,
            HotelOutline,
        },
        data() {
            return {
                columns_of_orders,
            }
        },
        computed: {
            ...mapGetters([
                'currentHotelInfo',
                'userOrderList',
                'dateRange',
            ])
        },
        async mounted() {
            this.set_currentHotelId(Number(this.$route.params.hotelId))
            this.getHotelById()
            await this.getUserInfo()
            await this.getUserOrders()
        },
        beforeRouteUpdate(to, from, next) {
            this.set_currentHotelId(Number(to.params.hotelId))
            this.getHotelById()
            next()
        },
        methods: {
            ...mapMutations([
                'set_currentHotelId',
                'set_dateRange',
            ]),
            ...mapActions([
                'getHotelById',
                'getUserInfo',
                'getUserOrders',
            ]),
            changeDate(v) {
                this.set_dateRange(v)
            },
        }
    }
</script>
<style scoped lang="less">
    .hotelDetailCard {
        padding: 50px 50px;
    }

    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;

        .info {
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;

            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;

                .label {
                    margin-right: 10px;
                    font-size: 18px;
                }

                .value {
                    margin-right: 15px
                }
            }
        }
    }
</style>
