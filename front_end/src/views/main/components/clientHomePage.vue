<template>
    <div>
        <div class="hotelList">
            <a-layout>
                <a-layout-content style="min-width: 800px">
                    <a-spin :spinning="hotelListLoading">
                        <div class="card-wrapper">
                            <HotelCard :hotel="item" :key="item.index" @click.native="jumpToDetails(item.id)"
                                       v-for="item in hotelList"></HotelCard>
                            <div :key="item.name" class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3"
                                 v-for="item in emptyBox"></div>
                            <a-config-provider :locale="locale">
                                <a-pagination
                                        :defaultCurrent="1"
                                        :page-size.sync="pageSize"
                                        :total="hotelList.length"
                                        @change="pageChange"
                                        show-less-items
                                        show-quick-jumper
                                        show-size-changer>
                                </a-pagination>
                            </a-config-provider>
                        </div>
                    </a-spin>
                </a-layout-content>
            </a-layout>
        </div>
        <div class="browselist">
            <a-layout>
                <a-layout-header style="font-size: x-large;background-color: cornflowerblue;color: white;">
                    <a-icon type="eye"/>
                    浏览酒店
                </a-layout-header>
                <a-table
                        :columns="columns"
                        :dataSource="hotelList"
                        bordered
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
                                v-ant-ref="c => (this.searchInput = c)"
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
                    <span slot="onceOrdered">
                                <a-tag color="blue">未预定</a-tag>
                            </span>
                    <span slot="action" slot-scope="record">
                                <a-button @click="jumpToDetails(record.id)" size="small" type="primary">进入酒店</a-button>
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
            </a-layout>
        </div>
        <div class="footPrints">
            <a-layout>
                <a-layout-header style="font-size: x-large;background-color: mediumpurple;color: white;">
                    <a-icon type="carry-out"/>
                    历史预定
                </a-layout-header>
                <a-list
                        :data-source="onceOrderedList"
                        :locale="{emptyText: '暂时没有您的预定记录'}"
                        item-layout="horizontal">
                    <a-list-item slot="renderItem" slot-scope="item">
                        <a-button @click="jumpToDetails(item.id)" size="small" slot="actions">
                            再次光顾
                        </a-button>
                        <a-list-item-meta
                                :description="item.description"
                        >
                            <a href="https://www.antdv.com/" slot="title">{{ item.name }}</a>
                            <a-avatar
                                    slot="avatar"
                                    src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                            />
                        </a-list-item-meta>
                    </a-list-item>
                </a-list>
            </a-layout>
        </div>
    </div>
</template>

<script>
    import HotelCard from '../../hotel/components/hotelCard'
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'

    export default {
        name: "clientHomePage",
        components: {
            HotelCard,
        },
        data() {
            return {
                emptyBox: [{name: 'box1'}, {name: 'box2'}, {name: 'box3'}],
                searchText: '',
                searchInput: null,
                searchedColumn: '',
                columns: [
                    {
                        title: '名称',
                        dataIndex: 'name',
                        align: 'center',
                        scopedSlots: {
                            filterDropdown: 'filterDropdown',
                            filterIcon: 'filterIcon',
                            customRender: 'customRender',
                        },
                        onFilter: (value, record) => record.name.includes(value),
                        onFilterDropdownVisibleChange: visible => {
                            if (visible) {
                                setTimeout(() => {
                                    this.searchInput.focus();
                                });
                            }
                        },
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
                        onFilterDropdownVisibleChange: visible => {
                            if (visible) {
                                setTimeout(() => {
                                    this.searchInput.focus();
                                });
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
                        onFilter: (value, record) => record.address.includes(value),
                        onFilterDropdownVisibleChange: visible => {
                            if (visible) {
                                setTimeout(() => {
                                    this.searchInput.focus();
                                });
                            }
                        },
                    },
                    {
                        title: '简介',
                        dataIndex: 'description',
                        ellipsis: true,
                    },
                    {
                        title: '操作',
                        scopedSlots: {customRender: 'action'},
                    },
                ],
                locale: zhCN,
                pageSize: 20,
            }
        },
        async mounted() {
            await this.getHotelList()
            await this.getOnceOrderedList()
        },
        computed: {
            ...mapGetters([
                'userInfo',
                'hotelList',
                'hotelListLoading',
                'onceOrderedList',
            ])
        },
        methods: {
            ...mapMutations([
                'set_hotelListParams',
                'set_hotelListLoading'
            ]),
            ...mapActions([
                'getHotelList',
                'getOnceOrderedList',
            ]),
            pageChange(page, pageSize) {
                const data = {
                    pageNo: page - 1
                }
                this.set_hotelListParams(data)
                this.set_hotelListLoading(true)
                this.getHotelList() // 这里需要改，不然每次翻页都是重新获取所有酒店
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
            jumpToDetails(id) {
                this.$router.push({name: 'hotelDetail', params: {hotelId: id}})
            },
        }
    }
</script>

<style lang="less" scoped>

    .hotelList {
        text-align: center;
        padding: 50px 0;

        .emptyBox {
            height: 0;
            margin: 10px 10px
        }

        .card-wrapper {
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
            flex-grow: 3;
            min-height: 500px
        }

        .card-wrapper .card-item {
            margin: 30px;
            position: relative;
            height: 188px;
        }
    }

    .browselist {

        .info {
            width: 400px;
            margin: 10px;
        }

        .highlight {
            background-color: rgb(255, 192, 105);
            padding: 0;
        }

    }

    .footPrints {

    }
</style>