<template>
    <div>
        <div v-if="userInfo.userType==='Client'">
            <div class="hotelList">
                <a-layout>
                    <a-layout-content style="min-width: 800px">
                        <a-spin :spinning="hotelListLoading">
                            <div class="card-wrapper">
                                <HotelCard :hotel="item" v-for="item in hotelList" :key="item.index"
                                           @click.native="jumpToDetails(item.id)"></HotelCard>
                                <div v-for="item in emptyBox" :key="item.name"
                                     class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3"></div>
                                <a-config-provider :locale="locale">
                                    <a-pagination
                                            show-less-items
                                            show-size-changer
                                            show-quick-jumper
                                            :page-size.sync="pageSize"
                                            :total="hotelList.length"
                                            :defaultCurrent="1"
                                            @change="pageChange">
                                    </a-pagination>
                                </a-config-provider>
                            </div>
                        </a-spin>
                    </a-layout-content>
                </a-layout>
            </div>
            <div class="Browselist">
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
                                    v-ant-ref="c => (searchInput = c)"
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
                                查询
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
                        <span slot="onceOrdered">
                            <a-tag color="blue">未预定</a-tag>
                        </span>
                        <span slot="action" slot-scope="record">
                            <a-button type="primary" size="small" @click="jumpToDetails(record.id)">进入酒店</a-button>
                        </span>
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
                            item-layout="horizontal"
                            :locale="{emptyText: '暂时没有您的预定记录'}"
                            :data-source="onceOrderedList">
                        <a-list-item slot="renderItem" slot-scope="item">
                            <a-button size="small" slot="actions" @click="jumpToDetails(item.id)">
                                再次光顾
                            </a-button>
                            <a-list-item-meta
                                    :description="item.description"
                            >
                                <a slot="title" href="https://www.antdv.com/">{{ item.name }}</a>
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
        <div v-if="userInfo.userType==='HotelManager'">
            <div id="line_div" ref="chart" style="width: 1200px; height:600px; margin-top: 30px">

            </div>
            <p>展示酒店收到的订单</p>
        </div>
        <div v-if="userInfo.userType==='Admin'">
            <h1>网站人员看到的页面</h1>
        </div>
        <div v-if="userInfo.userType==='SalesPerson'">
            <h1>网站营销人员看到的页面</h1>

            <salesPerformance></salesPerformance>
        </div>
    </div>
</template>
<script>
    import HotelCard from './components/hotelCard'
    import salesPerformance from "../salesPerson/salesPerformance"
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'
    import * as echarts from 'echarts/lib/echarts'
    // 引入折线图。
    import 'echarts/lib/chart/line'
    // 引入提示框组件、标题组件、工具箱组件。
    import 'echarts/lib/component/tooltip'
    import 'echarts/lib/component/title'
    import 'echarts/lib/component/toolbox'
    import 'echarts/lib/component/legend'
    import 'echarts/lib/chart/effectScatter'

    const columns = [
        {
            title: '名称',
            dataIndex: 'name',
            align: 'center',
            scopedSlots: {
                filterDropdown: 'filterDropdown',
                filterIcon: 'filterIcon',
                customRender: 'customRender',
            },
        },
        {
            title: '星级',
            dataIndex: 'hotelStar',
            filters: [{text: '三星级', value: 'Three'}, {text: '四星级', value: 'Four'}, {text: '五星级', value: 'Five'}],
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
        },
        {
            title: '地址',
            dataIndex: 'address',
            scopedSlots: {
                filterDropdown: 'filterDropdown',
                filterIcon: 'filterIcon',
                customRender: 'customRender',
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
    ];

    export default {
        name: 'home',
        props: ['label', 'itemColor', 'backgroundColor', 'itemDataType', 'xAxisName', 'yAxisName', 'eventType'],
        components: {
            HotelCard,
            salesPerformance,
        },
        data() {
            return {
                emptyBox: [{name: 'box1'}, {name: 'box2'}, {name: 'box3'}],
                columns,
                locale: zhCN,
                pageSize: 20,
                searchText: '',
                searchInput: null,
                searchedColumn: '',
            }
        },
        async mounted() {
            await this.getHotelList()
            await this.getOnceOrderedList()
            this.drawLine()
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
            jumpToDetails(id) {
                this.$router.push({name: 'hotelDetail', params: {hotelId: id}})
            },
            drawLine() {
                if (this.userInfo.userType === 'HotelManager' || this.userInfo.userType === 'SalesPerson') {
                    const line_div = this.$refs.chart
                    echarts.init(line_div).setOption({
                        title: {text: '酒店近七日营收'},
                        tooltip: {},
                        legend: {
                            data: ['订单', '收入']
                        },
                        toolbox: {
                            feature: {
                                dataView: {},
                                saveAsImage: {
                                    pixelRatio: 2
                                },
                            }
                        },
                        xAxis: {
                            name: '时间',
                            data: ['第一天', '第二天', '第三天', '第四天', '第五天', '第六天', '第七天'],
                            nameTextStyle: {
                                fontSize: 14,
                                fontWeight: 'bolder'
                            }
                        },
                        yAxis: [
                            {
                                name: '订单数', nameTextStyle: {
                                    fontSize: 14,
                                    fontWeight: 'bolder'
                                }
                            },
                            {
                                name: '收入（单位：元）', nameTextStyle: {
                                    fontSize: 14,
                                    fontWeight: 'bolder'
                                }
                            }
                        ],
                        series: [{
                            name: '订单',
                            type: 'line',
                            smooth: true,
                            data: [5, 20, 36, 10, 20, 25, 30],
                            yAxisIndex: 0,
                            emphasis: {
                                itemStyle: {
                                    color: 'red'
                                },
                                label: {
                                    show: true,
                                    formatter: '当日订单'
                                }
                            },
                        }, {
                            name: '收入',
                            type: 'line',
                            smooth: true,
                            data: [550, 300, 400, 700, 600, 500, 700],
                            yAxisIndex: 1,
                            emphasis: {
                                itemStyle: {
                                    color: 'white'
                                },
                                label: {
                                    show: true,
                                    formatter: '当日收入'
                                }
                            },
                        }]
                    })
                }
            },
        }
    }
</script>

<style scoped lang="less">
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
            min-height: 800px
        }

        .card-wrapper .card-item {
            margin: 30px;
            position: relative;
            height: 188px;
        }

    }

    .Browselist {

        .info {
            width: 400px;
            margin: 10px;
        }
    }

    .footPrints {

    }
</style>