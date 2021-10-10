<template>
    <div>
        <div class="searchBar">
            <a-button @click="searchOpen" class="searchButton" icon="search" style="float: right" type="primary">
                搜索酒店
            </a-button>
        </div>

        <div>
            <a-drawer
                    :visible="searchVisible"
                    @close="searchClose"
                    title="搜索信息"
                    width="45%"
            >
                <a-form :form="form" style="border-radius: 20px; background-color: antiquewhite; padding: 10px;">
                    <a-form-item label="地址" v-bind="formItemLayout">
                        <a-input
                                class="searchInput" placeholder="请输入地址" type="text"
                                v-decorator="['address']"
                        />
                    </a-form-item>
                    <a-form-item label="商圈" v-bind="formItemLayout">
                        <a-select
                                v-decorator="['bizRegion', { rules: [{ required: true, message: '请选择商圈' }], initialValue: '西单' }]"
                        >
                            <a-select-option value="西单">西单</a-select-option>
                            <a-select-option value="仙林">仙林</a-select-option>
                            <a-select-option value="鼓楼">鼓楼</a-select-option>
                            <a-select-option value="新街口">新街口</a-select-option>
                            <a-select-option value="观前街">观前街</a-select-option>
                            <a-select-option value="金鸡湖">金鸡湖</a-select-option>
                            <a-select-option value="王府井">王府井</a-select-option>
                            <a-select-option value="南锣鼓巷">南锣鼓巷</a-select-option>
                            <a-select-option value="天安门">天安门</a-select-option>
                            <a-select-option value="三里屯">三里屯</a-select-option>
                            <a-select-option value="静安寺">静安寺</a-select-option>
                            <a-select-option value="外滩">外滩</a-select-option>
                            <a-select-option value="陆家嘴">陆家嘴</a-select-option>
                            <a-select-option value="华强北">华强北</a-select-option>
                            <a-select-option value="三国城">三国城</a-select-option>
                            <a-select-option value="灵山大佛">灵山大佛</a-select-option>
                            <a-select-option value="岳麓书院">岳麓书院</a-select-option>
                            <a-select-option value="天心阁">天心阁</a-select-option>
                            <a-select-option value="广州塔">广州塔</a-select-option>
                        </a-select>
                    </a-form-item>
                    <a-form-item label="关键词" v-bind="formItemLayout">
                        <a-select
                                mode="multiple"
                                placeholder="请选择酒店关键词"
                                v-decorator="['tags']"
                        >
                            <a-select-option :key="tag" v-for="tag in hotelTags">
                                {{ tag }}
                            </a-select-option>
                        </a-select>
                    </a-form-item>
                    <a-form-item label="价位上限 ¥" v-bind="formItemLayout">
                        <a-row>
                            <a-col :span="16">
                                <a-slider
                                        :max="2000" :min="1" @change="handleValueChange" v-model="value"
                                />
                            </a-col>
                            <a-col :span="4">
                                <a-input-number
                                        :max="2000" :min="1" style="margin-left: 16px" v-model="value"
                                />
                            </a-col>
                        </a-row>
                    </a-form-item>
                    <a-form-item label="评分下限" v-bind="formItemLayout">
                        <a-row>
                            <a-col :span="16">
                                <a-slider
                                        :max="5" :min="0" :step="0.1" @change="handleRateChange" v-model="rate"
                                />
                            </a-col>
                            <a-col :span="4">
                                <a-input-number
                                        :max="5" :min="0" :step="0.1" style="margin-left: 16px" v-model="rate"
                                />
                            </a-col>
                        </a-row>
                    </a-form-item>
                    <a-form-item label="酒店星级" v-bind="formItemLayout">
                        <a-checkbox-group :options="hotelStarOptions" @change="onChange"
                                          v-decorator="['stars',{initialValue: defaultCheckedList}]"
                        />
                    </a-form-item>
                    <a-form-item v-bind="formItemLayout">
                        <a-button @click="search" class="searchButton" icon="search" style="background-color: orange"
                                  type="primary">
                            搜索酒店
                        </a-button>
                    </a-form-item>
                </a-form>
            </a-drawer>
        </div>

        <div class="hotelList">
            <a-layout>
                <a-layout-content style="min-width: 800px">
                    <a-spin :spinning="hotelListLoading">
                        <div class="card-wrapper">
                            <HotelCard :hotel="item" :key="item.index" @click.native="jumpToDetails(item.id)"
                                       v-for="item in limitedHotelList"></HotelCard>
                        </div>
                        <a-config-provider :locale="locale">
                            <a-pagination
                                    :defaultCurrent="1"
                                    :page-size.sync="pageSize"
                                    :total="hotelList.length"
                                    @change="pageChange"
                                    show-less-items
                                    show-quick-jumper
                                    show-size-changer
                                    @showSizeChange="onShowSizeChange"
                            >
                            </a-pagination>
                        </a-config-provider>
                    </a-spin>
                </a-layout-content>
            </a-layout>
        </div>
        <div class="browseList">
            <a-layout>
                <a-layout-header
                        style="font-size: x-large;background-color: cornflowerblue;color: white; margin: 5px;border-radius: 15px">
                    <a-icon type="eye"/>
                    浏览全部酒店
                </a-layout-header>
                <a-table
                        :columns="hotelColumns"
                        :dataSource="hotelList"
                        bordered
                        style="padding: 10px; border-radius: 20px"
                        :rowKey="record => record.id"
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
                                v-ant-ref="c => searchInput = c"
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
                <a-layout-header
                        style="font-size: x-large;background-color: mediumpurple;color: white; margin: 5px;border-radius: 15px">
                    <a-icon type="carry-out"/>
                    历史预定
                </a-layout-header>
                <a-table
                        :columns="hotelColumns"
                        :dataSource="onceOrderedList"
                        :locale="{emptyText: '暂时没有您的预定记录'}"
                        :rowKey="record => record.id"
                        bordered
                        style="padding: 10px; border-radius: 20px"
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
                                @pressEnter="() => handleAnotherSearch(selectedKeys, confirm, column.dataIndex)"
                                style="width: 188px; margin-bottom: 8px; display: block;"
                                v-ant-ref="c => anotherSearchInput = c"
                        />
                        <a-button
                                @click="() => handleAnotherSearch(selectedKeys, confirm, column.dataIndex)"
                                icon="search"
                                size="small"
                                style="width: 90px; margin-right: 8px"
                                type="primary"
                        >
                            查询
                        </a-button>
                        <a-button @click="() => handleAnotherReset(clearFilters)" size="small" style="width: 90px">
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
                        <a-button @click="jumpToDetails(record.id)" size="small">再次光临</a-button>
                    </span>

                    <template slot="customRender" slot-scope="text, record, index, column">
                        <span v-if="anotherSearchText && searchedColumn === column.dataIndex">
                            <template
                                    v-for="(fragment, i) in text.split(new RegExp(`(?<=${anotherSearchText})|(?=${anotherSearchText})`, 'g'))">
                                <mark
                                        :key="i"
                                        class="highlight"
                                        v-if="fragment === anotherSearchText"
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
    </div>
</template>

<script>
    import HotelCard from '../../hotel/components/hotelCard'
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'
    import moment from "moment";

    const hotelStarOptions = ['三星级', '四星级', '五星级']
    const defaultCheckedList = ['三星级', '四星级', '五星级']

    export default {
        name: "clientHomePage",
        components: {
            HotelCard,
        },
        data() {
            return {
                searchText: '',
                anotherSearchText: '',
                searchInput: '',
                anotherSearchInput: '',
                searchedColumn: '',
                hotelColumns: [
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
                        align: 'center',
                    },
                    {
                        title: '简介',
                        dataIndex: 'description',
                        ellipsis: true,
                        align: 'center',
                    },
                    {
                        title: '操作',
                        scopedSlots: {customRender: 'action'},
                        align: 'center',
                    },
                ],
                locale: zhCN,
                pageSize: 20,
                searchVisible: false,
                formItemLayout: {
                    labelCol: {
                        xs: {span: 10},
                        sm: {span: 5},
                    },
                    wrapperCol: {
                        xs: {span: 20},
                        sm: {span: 10},
                    },
                },
                keyword: '',
                value: 1000,
                rate: 3.0,
                indeterminate: false,
                hotelStarOptions,
                defaultCheckedList,
                dateRange: [moment(), moment().add(1, 'd')],
                hotelTags: [
                    '温泉',
                    '干净整洁',
                    '便宜',
                    '早餐',
                    '体验很棒',
                    '晚安服务',
                    '酒',
                    '可乐',
                    '安静',
                    '情侣酒店',
                    '浪漫',
                    '安全',
                ],
            }
        },
        async mounted() {
            await this.getHotelList()
            this.getLimitedHotelList()
            this.getOnceOrderedList()
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'searchTable'});
        },

        computed: {
            ...mapGetters([
                'userInfo',
                'hotelList',
                'limitedHotelList',
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
                'getLimitedHotelList',
                'getOnceOrderedList',
                'searchHotel',
            ]),
            pageChange(page, pageNum) {
                const data = {
                    pageNo: page - 1,
                    pageSize: pageNum,
                }
                this.set_hotelListParams(data)
                this.set_hotelListLoading(true)
                this.getLimitedHotelList()
            },
            onShowSizeChange(current, pageSize) {
                this.pageChange(current, pageSize)
            },
            handleSearch(selectedKeys, confirm, dataIndex) {
                confirm();
                this.searchText = selectedKeys[0];
                this.searchedColumn = dataIndex;
            },
            handleAnotherSearch(selectedKeys, confirm, dataIndex){
                confirm();
                this.anotherSearchText = selectedKeys[0];
                this.searchedColumn = dataIndex;
            },
            handleReset(clearFilters) {
                clearFilters();
                this.searchText = '';
            },
            handleAnotherReset(clearFilters) {
                clearFilters();
                this.anotherSearchText = '';
            },
            jumpToDetails(id) {
                this.$router.push({name: 'hotelDetail', params: {hotelId: id}})
            },
            handleValueChange(value) {
                this.value = value
            },
            handleRateChange(rate) {
                this.rate = rate
            },
            onChange(checkedList) {
                this.indeterminate = !!checkedList.length && checkedList.length < hotelStarOptions.length;
            },
            searchOpen() {
                this.searchVisible = true
            },
            searchClose() {
                this.searchVisible = false
            },
            search(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll(async (err, values) => {
                    if (!err) {
                        const data = {
                            address: this.form.getFieldValue('address') ? this.form.getFieldValue('address') : '',
                            bizRegion: this.form.getFieldValue('bizRegion') ? this.form.getFieldValue('bizRegion') : '',
                            keyWords: this.form.getFieldValue('tags') ? this.form.getFieldValue('tags') : [],
                            maxPrice: this.value,
                            minScore: this.rate,
                            hotelStar: this.form.getFieldValue('stars'),
                        }
                        await this.searchHotel(data)
                        this.form.resetFields()
                        this.searchVisible = false
                        this.$router.push('/hotel/searchHotel')
                    }
                });
            },
        }
    }
</script>

<style lang="less" scoped>
    .searchBar {
        padding: 20px;
        margin-top: 10px;
        margin-bottom: 10px;
    }

    .searchButton {
        width: 200px;
        height: auto;
        padding: 5px;
        text-align: center;
        float: right;
        font-size: large;
        border-radius: 20px;
    }

    .hotelList {
        text-align: center;
        padding: 50px 0;
        width: 100%;

        .card-wrapper {
            display: flex;
            justify-content: flex-start;
            flex-wrap: wrap;
            min-height: 500px;
        }
    }

    .browseList {

        .info {
            width: 400px;
            margin: 10px;
        }

    }

    .footPrints {

    }

    .highlight {
        background-color: rgb(255, 192, 105);
        padding: 0;
    }
</style>