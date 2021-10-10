<template>
    <div>
        <a-table
                :columns="columns"
                :dataSource="allCorpVIPList"
                :locale="{emptyText: '暂时没有企业会员'}"
                :rowKey="record => record.corporationName"
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
                                v-if="fragment=== searchText"
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

            <span slot="level" slot-scope="text">
                {{ text }}
            </span>

            <span slot="consumption" slot-scope="text">
                {{ text }}
            </span>

            <span slot="status" slot-scope="text">
                <a-tag color="blue" v-if="text===1">正常</a-tag>
                <a-tag color="red" v-else>被冻结</a-tag>
            </span>

            <span slot="action" slot-scope="record">
                <a-popconfirm
                    title="确定冻结该企业会员吗?"
                    ok-text="确定"
                    cancel-text="取消"
                    @confirm="freezeCorp(record.corporationName)"
                    @cancel="cancel"
                    v-if="record.status===1"
            >
                    <a-button type="danger" size="small" >冻结</a-button>
                </a-popconfirm>

                <a-popconfirm
                    title="确定恢复该企业会员吗?"
                    ok-text="确定"
                    cancel-text="取消"
                    @confirm="restoreCorp(record.corporationName)"
                    @cancel="cancel"
                    v-else
                >
                <a-button type="primary" size="small" >恢复</a-button>
                </a-popconfirm>
            </span>
        </a-table>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "ManageCorpMembership",
        data() {
            return {
                searchText: '',
                searchInput: null,
                searchedColumn: '',
                columns: [
                    {
                        title: '企业名',
                        dataIndex: 'corporationName',
                        scopedSlots: {
                            filterDropdown: 'filterDropdown',
                            filterIcon: 'filterIcon',
                            customRender: 'customRender',
                        },
                        onFilter: (value, record) =>
                            record.corporationName
                                .toString()
                                .includes(value),
                        onFilterDropdownVisibleChange: visible => {
                            if (visible) {
                                setTimeout(() => {
                                    this.searchInput.focus();
                                }, 0);
                            }
                        },
                    },
                    {
                        title: 'VIP等级',
                        dataIndex: 'level',
                        scopedSlots: { customRender: 'level' }
                    },
                    {
                        title: '累计消费￥',
                        dataIndex: 'consumption',
                        scopedSlots: { customRender: 'consumption' }
                    },
                    {
                        title: '状态',
                        dataIndex: 'status',
                        scopedSlots: { customRender: 'status' },
                        filters: [{text: '正常', value: 1}, {text: '冻结', value: 0}],
                        onFilter: (value, record) => record.status === value,
                        filterMultiple: false,
                    },
                    {
                        title: '操作',
                        key: 'action',
                        scopedSlots: { customRender: 'action' },
                    },

                ],
            }
        },
        computed: {
            ...mapGetters([
                'allCorpVIPList',
            ]),
        },
        async mounted() {
            await this.getAllCorpVIP()
        },
        methods: {
            ...mapActions([
                'getAllCorpVIP',
                'freezeCorpVIP',
                'restoreCorpVIP',
            ]),
            freezeCorp(corporationName) {
                this.freezeCorpVIP(corporationName)
            },
            restoreCorp(corporationName) {
                this.restoreCorpVIP(corporationName)
            },
            cancel() {},
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

<style scoped>
    .highlight {
        background-color: rgb(255, 192, 105);
        padding: 0px;
    }
</style>
