<!-- created by glh 2020-05-23 -->
<!-- 网站营销人员用来管理订单情况，包括浏览所有订单和处理异常订单 -->
<template>
    <div>
        <div class="info-wrapper">
            <a-tabs>
                <a-tab-pane tab="所有订单" key="1">
                    <a-table
                        :columns="columns_of_orders"
                        :dataSource="allOrderList"
                        :rowKey="record => record.id"
                        bordered
                    >
                        <div
                            slot="filterDropdown"
                            slot-scope="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
                            style="padding: 8px"
                        >
                            <a-input
                                v-ant-ref="c => searchInput = c"
                                :placeholder="`查询 ${column.title}`"
                                :value="selectedKeys[0]"
                                style="width: 188px; margin-bottom: 8px; display: block;"
                                @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                                @pressEnter="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                            />
                            <a-button
                                type="primary"
                                icon="search"
                                size="small"
                                style="width: 90px; margin-right: 8px"
                                @click="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                            >
                                搜索
                            </a-button>
                            <a-button size="small" style="width: 90px" @click="() => handleReset(clearFilters)">
                                重置
                            </a-button>
                        </div>
                        <a-icon
                                slot="filterIcon"
                                slot-scope="filtered"
                                type="search"
                                :style="{ color: filtered ? '#108ee9' : undefined }"
                        />
                        <template slot="customRender" slot-scope="text, record, index, column">
                        <span v-if="searchText && searchedColumn === column.dataIndex">
                            <template
                                v-for="(fragment, i) in text
                                .toString()
                                .split(new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i'))"
                            >
                                <mark
                                    v-if="fragment.toLowerCase() === searchText.toLowerCase()"
                                    :key="i"
                                    class="highlight"
                                >
                                {{ fragment }}
                                </mark>
                                <template v-else>
                                    {{ fragment }}
                                </template>
                            </template>
                        </span>
                            <template v-else>
                                {{ text }}
                            </template>
                        </template>

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
                        <span>￥ {{ text }}</span>
                    </span>
                        <a-tag slot="orderState" color="blue" slot-scope="text">
                            {{ text }}
                        </a-tag>
                        <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showOrderDetail(record)">查看详情</a-button>
                    </span>
                    </a-table>
                </a-tab-pane>

                <a-tab-pane key="2">
                    <span slot="tab">
                        今日未执行
                        <sup style="color: red">{{ todayUnExecutedOrders.length }}</sup>
                    </span>
                    <a-table
                        :columns="columns_of_orders_nofilter"
                        :dataSource="todayUnExecutedOrders"
                        :rowKey="record => record.id"
                        :locale="{emptyText: '暂时没有未执行订单'}"
                        bordered
                    >
                        <div
                            slot="filterDropdown"
                            slot-scope="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
                            style="padding: 8px"
                        >
                            <a-input
                                v-ant-ref="c => searchInput = c"
                                :placeholder="`查询 ${column.title}`"
                                :value="selectedKeys[0]"
                                style="width: 188px; margin-bottom: 8px; display: block;"
                                @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                                @pressEnter="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                            />
                            <a-button
                                type="primary"
                                icon="search"
                                size="small"
                                style="width: 90px; margin-right: 8px"
                                @click="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                            >
                                搜索
                            </a-button>
                            <a-button size="small" style="width: 90px" @click="() => handleReset(clearFilters)">
                                重置
                            </a-button>
                        </div>
                        <a-icon
                                slot="filterIcon"
                                slot-scope="filtered"
                                type="search"
                                :style="{ color: filtered ? '#108ee9' : undefined }"
                        />
                        <template slot="customRender" slot-scope="text, record, index, column">
                        <span v-if="searchText && searchedColumn === column.dataIndex">
                            <template
                                v-for="(fragment, i) in text
                                .toString()
                                .split(new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i'))"
                            >
                                <mark
                                    v-if="fragment.toLowerCase() === searchText.toLowerCase()"
                                    :key="i"
                                    class="highlight"
                                >
                                    {{ fragment }}
                                </mark>
                                <template v-else>
                                    {{ fragment }}
                                </template>
                            </template>
                        </span>
                            <template v-else>
                                {{ text }}
                            </template>
                        </template>

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
                        <span>￥ {{ text }}</span>
                    </span>
                        <a-tag slot="orderState" color="blue" slot-scope="text">
                            {{ text }}
                        </a-tag>
                        <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showOrderDetail(record)">查看详情</a-button>
                    </span>
                    </a-table>
                </a-tab-pane>

                <a-tab-pane key="3">
                    <span slot="tab">
                        异常订单
                        <sup style="color: red">{{ allAbnormalOrders.length }}</sup>
                    </span>
                    <a-table
                            :columns="columns_of_orders_nofilter"
                            :dataSource="allAbnormalOrders"
                            :rowKey="record => record.id"
                            :locale="{emptyText: '暂时没有异常订单'}"
                            bordered
                    >
                        <div
                            slot="filterDropdown"
                            slot-scope="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
                            style="padding: 8px"
                        >
                            <a-input
                                v-ant-ref="c => searchInput = c"
                                :placeholder="`查询 ${column.title}`"
                                :value="selectedKeys[0]"
                                style="width: 188px; margin-bottom: 8px; display: block;"
                                @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                                @pressEnter="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                            />
                            <a-button
                                type="primary"
                                icon="search"
                                size="small"
                                style="width: 90px; margin-right: 8px"
                                @click="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                            >
                                搜索
                            </a-button>
                            <a-button size="small" style="width: 90px" @click="() => handleReset(clearFilters)">
                                重置
                            </a-button>
                        </div>
                        <a-icon
                            slot="filterIcon"
                            slot-scope="filtered"
                            type="search"
                            :style="{ color: filtered ? '#108ee9' : undefined }"
                        />
                        <template slot="customRender" slot-scope="text, record, index, column">
                        <span v-if="searchText && searchedColumn === column.dataIndex">
                            <template
                                v-for="(fragment, i) in text
                                .toString()
                                .split(new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i'))"
                            >
                                <mark
                                    v-if="fragment.toLowerCase() === searchText.toLowerCase()"
                                    :key="i"
                                    class="highlight"
                                >
                                    {{ fragment }}
                                </mark>
                                <template v-else>
                                    {{ fragment }}
                                </template>
                            </template>
                        </span>
                            <template v-else>
                                {{ text }}
                            </template>
                        </template>

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
                        <span>￥ {{ text }}</span>
                    </span>
                        <a-tag slot="orderState" color="blue" slot-scope="text">
                            {{ text }}
                        </a-tag>
                        <span slot="action" slot-scope="record">
                        <a-button type="danger" size="small" @click="handleAbnormalOrder(record)">处理异常</a-button>
                    </span>
                    </a-table>
                </a-tab-pane>

            </a-tabs>

        </div>
        <orderDetail></orderDetail>
        <handleAbnormalOrder></handleAbnormalOrder>
    </div>
</template>

<script>
import orderDetail from "../order/orderDetail";
import handleAbnormalOrder from "./components/handleAbnormalOrder";
import { mapGetters, mapMutations, mapActions } from 'vuex'
import {message} from "ant-design-vue";
export default {
    name: "manageOrders",
    data() {
        return {
            searchText: '',
            searchInput: null,
            searchedColumn: '',
            columns_of_orders: [
                {
                    title: '订单号',
                    dataIndex: 'id',
                },
                {
                    title: '酒店名',
                    dataIndex: 'hotelName',
                    scopedSlots: {
                        filterDropdown: 'filterDropdown',
                        filterIcon: 'filterIcon',
                        customRender: 'customRender',
                    },
                    onFilter: (value, record) =>
                        record.hotelName
                            .toString()
                            .toLowerCase()
                            .includes(value.toLowerCase()),
                    onFilterDropdownVisibleChange: visible => {
                        if (visible) {
                            setTimeout(() => {
                                this.searchInput.focus();
                            }, 0);
                        }
                    },
                },
                {
                    title: '房型',
                    dataIndex: 'roomType',
                    scopedSlots: { customRender: 'roomType' }
                },
                {
                    title: '入住时间',
                    dataIndex: 'checkInDate',
                    scopedSlots: { customRender: 'checkInDate' },
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
                    scopedSlots: { customRender: 'checkOutDate' },
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
                    scopedSlots: { customRender: 'price' }
                },
                {
                    title: '状态',
                    filters: [{text: '未入住', value: '未入住'}, {text: '已撤销', value: '已撤销'}, {text: '已入住', value: '已入住'},
                        {text: '已完成', value: '已完成'}, {text: '异常订单', value: '异常订单'}],
                    onFilter: (value, record) => record.orderState.includes(value),
                    filterMultiple: false,
                    dataIndex: 'orderState',scopedSlots: { customRender: 'orderState' },
                },
                {
                    title: '操作',
                    key: 'action',
                    scopedSlots: { customRender: 'action' },
                },

            ],
            columns_of_orders_nofilter: [
                {
                    title: '订单号',
                    dataIndex: 'id',
                },
                {
                    title: '酒店名',
                    dataIndex: 'hotelName',
                    scopedSlots: {
                        filterDropdown: 'filterDropdown',
                        filterIcon: 'filterIcon',
                        customRender: 'customRender',
                    },
                    onFilter: (value, record) =>
                        record.hotelName
                            .toString()
                            .toLowerCase()
                            .includes(value.toLowerCase()),
                    onFilterDropdownVisibleChange: visible => {
                        if (visible) {
                            setTimeout(() => {
                                this.searchInput.focus();
                            }, 0);
                        }
                    },
                },
                {
                    title: '房型',
                    dataIndex: 'roomType',
                    scopedSlots: { customRender: 'roomType' }
                },
                {
                    title: '入住时间',
                    dataIndex: 'checkInDate',
                    scopedSlots: { customRender: 'checkInDate' },
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
                    scopedSlots: { customRender: 'checkOutDate' },
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
                    scopedSlots: { customRender: 'price' }
                },
                {
                    title: '状态',
                    dataIndex: 'orderState',
                    scopedSlots: { customRender: 'orderState' },
                },
                {
                    title: '操作',
                    key: 'action',
                    scopedSlots: { customRender: 'action' },
                },

            ],
        }
    },
    components: {
        orderDetail,
        handleAbnormalOrder,
    },
    computed: {
        ...mapGetters([
            'orderDetailVisible',
            'allOrderList',
        ]),
        allAbnormalOrders () {
            return this.allOrderList.filter(function(x) {
                return x.orderState === '异常订单'
            })
        },
        todayUnExecutedOrders () {
            return this.allOrderList.filter(function(x) {
                let checkInDate = new Date(x.checkInDate);
                let now = new Date();
                return x.orderState === '未入住' && checkInDate.toLocaleDateString()===now.toLocaleDateString()
            })
        },
    },
    async mounted() {
        await this.getAllOrders()
    },
    methods: {
        ...mapActions([
            'getAllOrders',
            'getHotelById',
            'getOrderComment',
        ]),
        ...mapMutations([
            'set_orderDetailVisible',
            'set_orderInfo',
            'set_currentHotelId',
            'set_handleAbnormalOrderVisible',
        ]),
        showOrderDetail(record) {
            this.set_orderInfo(record)
            this.set_currentHotelId(record.hotelId)
            this.getHotelById(record.hotelId)
            this.set_orderDetailVisible(true)
        },
        handleAbnormalOrder(record) {
            this.set_orderInfo(record)
            this.set_currentHotelId(record.hotelId)
            this.getOrderComment(Number(record.id))
            this.getHotelById(record.hotelId)
            this.set_handleAbnormalOrderVisible(true)
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
    }
}
</script>
<style scoped lang="less">
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
<style lang="less">
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
    .highlight {
        background-color: rgb(255, 192, 105);
        padding: 0;
    }
</style>
