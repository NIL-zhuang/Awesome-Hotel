<template>
    <a-layout>
        <a-layout-content>
            <div class="hotelDetailCard">
                <h1>
                    {{ currentHotelInfo.title }}
                </h1>
                <div class="hotel-info">
                    <a-card style="width: 300px; border-radius: 20px">
                        <img
                                alt="example"
                                referrerPolicy="no-referrer"
                                slot="cover"
                                style="height: 400px;  border-radius: 20px"
                                src="@/assets/cover.jpeg"
                        />
                    </a-card>
                    <div class="info">
                        <div class="items" v-if="currentHotelInfo.name">
                            <span class="label">酒店名称: </span>
                            <span class="value">{{ currentHotelInfo.name }}</span>
                            <a-tag color="orange"
                                   v-if="userOrderList.filter(order=>order.hotelId===currentHotelInfo.id).length>0">
                                <a-icon type="star"/>
                                您曾经预定过这家酒店
                            </a-tag>
                        </div>
                        <div class="items" v-if="currentHotelInfo.address">
                            <span class="label">地址: </span>
                            <span class="value">{{ currentHotelInfo.address }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.bizRegion">
                            <span class="label">商圈: </span>
                            <span class="value">{{ currentHotelInfo.bizRegion }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.rate">
                            <span class="label">评分: </span>
                            <span class="value">{{ currentHotelInfo.rate.toFixed(1) }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">星级: </span>
                            <a-rate :disabled="true"
                                    :value="currentHotelInfo.hotelStar === '三星级' ?
                                    3 : currentHotelInfo.hotelStar === '四星级' ? 4 : 5"
                                    style="font-size: 15px"/>
                        </div>
                        <div class="items" v-if="currentHotelInfo.description">
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
                    <a-tab-pane key="1" tab="房间信息">
                        <a-row>
                            <h1>入住-退房时间</h1>
                        </a-row>
                        <a-range-picker
                                style="border-radius: 15px"
                                :default-value="dateRange"
                                @change="changeDate"
                                format="YYYY-MM-DD"
                                v-decorator="[
                                    'date',
                                    { rules: [{ required: true, message: '请选择入住时间' }]},
                                ]"
                        />
                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
                    </a-tab-pane>
                    <a-tab-pane key="2" tab="了解详情">
                        <HotelOutline></HotelOutline>
                    </a-tab-pane>
                    <a-tab-pane key="3" tab="历史订单">
                        <a-page-header
                                style="border: 1px solid rgb(235, 237, 240)"
                                sub-title="若要对订单进行撤销，请前往个人中心-我的订单页面进行相关操作"
                                title="您在这家酒店有如下的订单"
                        />
                        <a-table
                                :columns="columns_of_orders"
                                :dataSource="userOrderList.filter(order=>order.hotelId===currentHotelInfo.id)"
                                :rowKey="record => record.id"
                                :locale="{emptyText: '暂时没有订单'}"
                                style="border-radius: 25px; background-color: white; padding: 10px"
                                bordered
                        >
                            <a-tag color="purple" slot="createDate" slot-scope="text">
                                {{text}}
                            </a-tag>
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
                            <span slot="price" slot-scope="text">
                                <a-tag color="pink">￥ {{ text }}</a-tag>
                            </span>
                            <a-tag color="blue" slot="orderState" slot-scope="text">
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
            filters: [{text: '未入住', value: '未入住'}, {text: '已撤销', value: '已撤销'}, {text: '已入住', value: '已入住'},
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
                'currentHotelId',
                'currentHotelInfo',
                'userOrderList',
                'dateRange',
            ])
        },
        async mounted() {
            await this.set_currentHotelId(Number(this.$route.params.hotelId))
            this.getHotelByIdWithTime({
                hotelId: this.currentHotelId,
                startTime: this.dateRange[0].format("YYYY-MM-DD"),
                endTime: this.dateRange[1].format("YYYY-MM-DD"),
            })
            await this.getUserInfo()
            this.getUserOrders()
        },
        beforeRouteUpdate(to, from, next) {
            this.set_currentHotelId(Number(to.params.hotelId))
            this.getHotelByIdWithTime({
                hotelId: this.currentHotelId,
                startTime: this.dateRange[0].format("YYYY-MM-DD"),
                endTime: this.dateRange[1].format("YYYY-MM-DD"),
            })
            next()
        },
        methods: {
            ...mapMutations([
                'set_currentHotelId',
                'set_dateRange',
            ]),
            ...mapActions([
                'getHotelByIdWithTime',
                'getUserInfo',
                'getUserOrders',
            ]),
            async changeDate(v) {
                this.set_dateRange(v)
                this.getHotelByIdWithTime({
                    hotelId: this.currentHotelId,
                    startTime: this.dateRange[0].format("YYYY-MM-DD"),
                    endTime: this.dateRange[1].format("YYYY-MM-DD"),
                })
            },
        }
    }
</script>
<style lang="less" scoped>
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
            margin-left: 40px;

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
