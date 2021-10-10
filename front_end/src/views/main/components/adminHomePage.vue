<template>
    <div class="manageHotel-wrapper">
        <div style="text-align: center; width: 100%">
            <h1>全部酒店</h1>
        </div>
        <div style="width: 100%; text-align: right; margin:20px 0">
            <a-button type="primary" @click="addHotel"><a-icon type="plus" />添加酒店</a-button>
        </div>
        <a-table
            :columns="columns_of_hotels"
            :dataSource="hotelList"
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
                    :placeholder="`查询${column.title}`"
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
                            v-if="fragment === searchText"
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

            <span slot="action" slot-scope="record">
                <a-popconfirm
                    title="确定想删除该酒店吗？"
                    @confirm="deleteHotelById(record.id)"
                    okText="确定"
                    cancelText="取消"
            >
                    <a-button type="danger" size="small">删除</a-button>
                </a-popconfirm>

                <a-divider type="vertical"></a-divider>
                <a-button type="primary" size="small" @click="modifyHotel(record)">修改</a-button>
            </span>
        </a-table>

        <addHotelModal></addHotelModal>
        <ModifyHotelInfo></ModifyHotelInfo>
    </div>
</template>

<script>var searchInput;

import {mapGetters, mapMutations, mapActions} from 'vuex'
import addHotelModal from "../../admin/components/addHotelModal";
import ModifyHotelInfo from "../../admin/components/ModifyHotelInfo";
import {message} from 'ant-design-vue';

export default {
    name: "AdminHomePage",
    data() {
        return {
            columns_of_hotels: [
                {
                    title: '酒店名',
                    dataIndex: 'name',
                    scopedSlots: {
                        filterDropdown: 'filterDropdown',
                        filterIcon: 'filterIcon',
                        customRender: 'customRender',
                    },
                    onFilter: (value, record) => {
                        return record.name.toString().includes(value)
                    },
                    onFilterDropdownVisibleChange: visible => {
                        if (visible) {
                            setTimeout(() => {
                                this.searchInput.focus();
                            }, 0);
                        }
                    },
                },
                {
                    title: '商圈',
                    dataIndex: 'bizRegion',
                    scopedSlots: {
                        filterDropdown: 'filterDropdown',
                        filterIcon: 'filterIcon',
                        customRender: 'customRender',
                    },
                    onFilter: (value, record) => {
                        return record.bizRegion.toString().includes(value)
                    },
                    onFilterDropdownVisibleChange: visible => {
                        if (visible) {
                            setTimeout(() => {
                                this.searchInput.focus();
                            }, 0);
                        }
                    },
                },
                {
                    title: '地址',
                    dataIndex: 'address',
                    scopedSlots: {
                        filterDropdown: 'filterDropdown',
                        filterIcon: 'filterIcon',
                        customRender: 'customRender',
                    },
                    onFilter: (value, record) => {
                        return record.address.toString().includes(value)
                    },
                    onFilterDropdownVisibleChange: visible => {
                        if (visible) {
                            setTimeout(() => {
                                this.searchInput.focus();
                            }, 0);
                        }
                    },
                },
                {
                    title: '酒店星级',
                    dataIndex: 'hotelStar',
                    filters: [
                        { text: '三星级', value: '三星级' },
                        { text: '四星级', value: '四星级' },
                        { text: '五星级', value: '五星级' },
                    ],
                    onFilter: (value, record) => record.hotelStar === value,
                    filterMultiple: true,
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
            ],
            searchText: '',
            searchInput: '',
            searchedColumn: '',
        }
    },
    components: {
        addHotelModal,
        ModifyHotelInfo,
    },
    computed: {
        ...mapGetters([
            'hotelList',
            'addHotelModalVisible',
        ]),
    },
    async mounted() {
        await this.getHotelList()
    },
    methods: {
        ...mapMutations([
            'set_addHotelModalVisible',
            'set_modifyHotelModalVisible',
            'set_modifyHotelInfo',
        ]),
        ...mapActions([
            'getHotelList',
            'deleteHotel',
        ]),
        addHotel() {
            this.set_addHotelModalVisible(true)
        },
        deleteHotelById(id) {
            this.deleteHotel(id)
        },
        modifyHotel(record) {
            this.set_modifyHotelModalVisible(true)
            this.set_modifyHotelInfo(record)
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
    .manageHotel-wrapper {
        padding: 50px;
        .highlight {
            background-color: rgb(255, 192, 105);
            padding: 0;
        }
    }
</style>
