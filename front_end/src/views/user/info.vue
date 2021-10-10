<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane key="1" tab="我的信息">
                <InfoForm></InfoForm>
            </a-tab-pane>
            <a-tab-pane key="2" tab="我的订单">
                <a-table
                        :columns="columns_of_orders"
                        :dataSource="userOrderList"
                        :locale="{emptyText: '您还没有订单'}"
                        :rowKey="record => record.id"
                        bordered
                        style="background-color: white; padding: 10px; border-radius: 20px"
                >
                    <div
                            slot="filterDropdown"
                            slot-scope="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
                            style="padding: 8px"
                    >
                        <a-input
                                :placeholder="`查询${column.title}`"
                                :value="selectedKeys[0]"
                                @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                                @pressEnter="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                                style="width: 188px; margin-bottom: 8px; display: block;"
                                v-ant-ref="c => (searchInput = c)"
                        />
                        <a-button
                                @click="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                                icon="search"
                                size="small"
                                style="width: 90px; margin-right: 8px"
                                type="primary"
                        >
                            查询
                        </a-button>
                        <a-button @click="() => handleReset(clearFilters)" size="small" style="width: 90px">
                            重置
                        </a-button>
                    </div>
                    <a-icon
                            :style="{ color: filtered ? '#108ee9' : undefined }"
                            slot="filterIcon"
                            slot-scope="filtered"
                            type="search"
                    />

                    <a-tag color="red" slot="createDate" slot-scope="text">
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
                    <span slot="action" slot-scope="record">
                        <a-button @click="showOrderDetail(record)" size="small" type="primary">查看</a-button>

                        <a-divider type="vertical" v-if="record.orderState === '未入住'"></a-divider>
                        <a-popconfirm
                                @confirm="confirmCancelOrder(record.id)"
                                cancelText="取消"
                                okText="确定"
                                title="你确定撤销该笔订单吗？"
                                v-if="record.orderState === '未入住'"
                        >
                            <a-button size="small" type="danger">撤销</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState === '已完成'"></a-divider>
                        <a-button @click="commentOrder(record)" size="small" type="default"
                                  v-if="record.orderState === '已完成'">评价</a-button>

                        <a-divider type="vertical" v-if="record.orderState === '异常订单'"></a-divider>
                        <a-button @click="argueOrder(record)" size="small" type="default"
                                  v-if="record.orderState === '异常订单'">申诉</a-button>
                    </span>
                    <template slot="customRender" slot-scope="text, record, index, column">
                        <span v-if="searchText && searchedColumn === column.dataIndex">
                            <template
                                    v-for="(fragment, i) in text.split(new RegExp(`(?<=${searchText})|(?=${searchText})`, 'g'))">
                                <mark
                                        :key="i"
                                        class="highlight"
                                        v-if="fragment === searchText"
                                >
                                    {{ fragment }}
                                </mark>
                                <template v-else>{{ fragment }}</template>
                            </template>
                        </span>
                        <template v-else>
                            {{ text }}
                        </template>
                    </template>
                </a-table>
            </a-tab-pane>
            <a-tab-pane key="3" tab="我的收藏">
                <a-table
                        :columns="columns_of_collections"
                        :dataSource="userCollections"
                        :locale="{emptyText: '您还没有收藏任何酒店'}"
                        :rowKey="record => record.id"
                        bordered
                        style="background-color: white; padding: 10px; border-radius: 20px"
                >
                    <div
                            slot="filterDropdown"
                            slot-scope="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
                            style="padding: 8px"
                    >
                        <a-input
                                :placeholder="`查询${column.title}`"
                                :value="selectedKeys[0]"
                                @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                                @pressEnter="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                                style="width: 188px; margin-bottom: 8px; display: block;"
                        />
                        <a-button
                                @click="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                                icon="search"
                                size="small"
                                style="width: 90px; margin-right: 8px"
                                type="primary"
                        >
                            查询
                        </a-button>
                        <a-button @click="() => handleReset(clearFilters)" size="small" style="width: 90px">
                            重置
                        </a-button>
                    </div>
                    <a-icon
                            :style="{ color: filtered ? '#108ee9' : undefined }"
                            slot="filterIcon"
                            slot-scope="filtered"
                            type="search"
                    />
                    <span slot="action" slot-scope="record">
                        <a-button @click="jumpToDetails(record.hotelID)" size="small" type="primary">进入酒店</a-button>
                    </span>
                    <template slot="customRender" slot-scope="text, record, index, column">
                        <span v-if="searchText && searchedColumn === column.dataIndex">
                            <template
                                    v-for="(fragment, i) in text.split(new RegExp(`(?<=${searchText})|(?=${searchText})`, 'g'))">
                                <mark
                                        :key="i"
                                        class="highlight"
                                        v-if="fragment === searchText"
                                >
                                    {{ fragment }}
                                </mark>
                                <template v-else>{{ fragment }}</template>
                            </template>
                        </span>
                        <template v-else>
                            {{ text }}
                        </template>
                    </template>
                </a-table>
            </a-tab-pane>
            <a-tab-pane key="4" tab="信用记录">
                <a-table
                        :columns="columns_of_credit"
                        :dataSource="creditChangeList"
                        :locale="{emptyText: '暂时没有信用变更记录'}"
                        :rowKey="record => record.id"
                        bordered
                        style="background-color: white; padding: 10px; border-radius: 20px"
                >
                    <span slot="type" slot-scope="record">
                        <a-icon theme="twoTone" type="plus-circle" v-if="record.change>0"/>
                        <a-icon theme="twoTone" two-tone-color="red" type="minus-circle" v-else/>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane key="5" tab="密码更改">
                <password-form></password-form>
            </a-tab-pane>
        </a-tabs>
        <orderDetail></orderDetail>
        <comment-order></comment-order>
        <argue-abnormal-order-modal></argue-abnormal-order-modal>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import orderDetail from '../order/orderDetail'
    import commentOrder from "../order/commentOrder";
    import InfoForm from './components/infoForm';
    import passwordForm from "./components/passwordForm";
    import argueAbnormalOrderModal from "../order/argueAbnormalOrderModal";
    import {message} from 'ant-design-vue';

    const columns_of_orders = [
        {
            title: '订单号',
            dataIndex: 'id',
            sorter: (a, b) => a.id - b.id,
    },
        {
            title: '下单时间',
            dataIndex: 'createDate',
            scopedSlots: {customRender: 'createDate'},
        },
        {
            title: '酒店名',
            dataIndex: 'hotelName',
            scopedSlots: {
                filterDropdown: 'filterDropdown',
                filterIcon: 'filterIcon',
                customRender: 'customRender',
            },
            onFilter: (value, record) => record.hotelName.includes(value),
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
            filterMultiple: true,
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },

    ];
    const columns_of_collections = [
        {
            title: '酒店名称',
            dataIndex: 'hotelName',
            align: 'center',
            scopedSlots: {
                filterDropdown: 'filterDropdown',
                filterIcon: 'filterIcon',
                customRender: 'customRender',
            },
            onFilter: (value, record) => record.hotelName.includes(value),
        },
        {
            title: '星级',
            dataIndex: 'hotelStar',
            filters: [{text: '三星级', value: '三星级'}, {text: '四星级', value: '四星级'},
                {text: '五星级', value: '五星级'}],
            onFilter: (value, record) => record.hotelStar.includes(value),
            align: 'center',
        },
        {
            title: '评分',
            dataIndex: 'rate',
            sorter: (a, b) => a.rate - b.rate,
            align: 'center',
        },
        {
            title: '商圈',
            dataIndex: 'bizRegion',
            align: 'center',
            scopedSlots: {
                filterDropdown: 'filterDropdown',
                filterIcon: 'filterIcon',
                customRender: 'customRender',
            },
            onFilter: (value, record) => record.bizRegion.includes(value),
        },
        {
            title: '地址',
            dataIndex: 'address',
            scopedSlots: {
                filterDropdown: 'filterDropdown',
                filterIcon: 'filterIcon',
                customRender: 'customRender',
            },
            onFilter: (value, record) => record.address.includes(value),
        },
        {
            title: '操作',
            scopedSlots: {customRender: 'action'},
        },
    ];
    const columns_of_credit = [
        {
            title: '变更类型',
            scopedSlots: {customRender: 'type'},
        },
        {
            title: '变更日期',
            dataIndex: 'changeDate',
        },
        {
            title: '变更数额',
            dataIndex: 'change',
        },
        {
            title: '变更后信用',
            dataIndex: 'now',
        },
        {
            title: '变更原因',
            dataIndex: 'reason',
        }
    ];
    export default {
        name: 'info',
        data() {
            return {
                modify: false,
                formLayout: 'horizontal',
                formItemLayout: {
                    labelCol: {
                        span: 3
                    },
                    wrapperCol: {
                        span: 8,
                        offset: 1,
                    },
                },
                pagination: {},
                columns_of_orders,
                columns_of_collections,
                columns_of_credit,
                searchText: '',
                searchInput: '',
                searchedColumn: '',
            }
        },
        components: {
            orderDetail,
            commentOrder,
            InfoForm,
            passwordForm,
            argueAbnormalOrderModal,
        },
        computed: {
            ...mapGetters([
                'userId',
                'userInfo',
                'userVIP',
                'userOrderList',
                'creditChangeList',
                'orderDetailVisible',
                'userCollections',
                'currentOrderComment',
            ]),
        },

        async mounted() {
            await this.getUserInfo()
            this.getUserOrders()
            this.getUserCredits(this.userId)
            this.getUserCollections(this.userId)
        },
        methods: {
            ...mapActions([
                'getUserInfo',
                'getUserVIP',
                'getUserOrders',
                'cancelOrder',
                'getHotelById',
                'getUserCredits',
                'getOrderComment',
                'getUserCollections',
            ]),
            ...mapMutations([
                'set_orderDetailVisible',
                'set_currentHotelId',
                'set_commentOrderModalVisible',
                'set_orderInfo',
                'set_argueAbnormalOrderModalVisible',
                'set_argumentModify',
            ]),

            confirmCancelOrder(orderId) {
                this.cancelOrder(orderId)
            },

            showOrderDetail(record) {
                this.set_orderInfo(record)
                this.set_currentHotelId(record.hotelId)
                this.getHotelById(record.hotelId)
                this.set_orderDetailVisible(true)
            },
            commentOrder(record) {
                this.getOrderComment(Number(record.id)).then(() => {
                    this.set_commentOrderModalVisible(true)
                    this.set_orderInfo(record)
                })
            },
            jumpToDetails(id) {
                this.$router.push({name: 'hotelDetail', params: {hotelId: id}})
            },
            handleSearch(selectedKeys, confirm, dataIndex) {
                confirm();
                this.searchText = selectedKeys[0];
                this.searchedColumn = dataIndex;
            },

            handleReset(clearFilters) {
                clearFilters();
                this.searchText = '';
            },

            argueOrder(record) {
                this.getOrderComment(Number(record.id)).then(() => {
                    if(this.currentOrderComment.comment === null) {
                        this.set_argumentModify(true)
                    } else {
                        this.set_argumentModify(false)
                    }
                    this.set_orderInfo(record)
                    this.set_argueAbnormalOrderModalVisible(true)
                })
            },

        }
    }
</script>

<style lang="less" scoped>
    .info-wrapper {
        height: 1500px;
        padding: 30px;

        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px

        }
        .ant-tabs-bar {
            padding-left: 30px
        }
        .highlight {
            background-color: rgb(255, 192, 105);
            padding: 0;
        }
    }
</style>
