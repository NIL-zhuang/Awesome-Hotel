<template>
    <div class="manageManager-wrapper">
        <div style="width: 100%; text-align: right; margin:20px 0">
            <a-button type="primary" @click="addSalesPerson"><a-icon type="plus" />添加网站营销人员</a-button>
        </div>
        <a-table
            :columns="columns_of_salesPerson"
            :dataSource="salesPersonList"
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
                    placeholder="搜索用户邮箱"
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

            <span slot="action" slot-scope="record">
                <a-popconfirm
                        title="删除该网站营销人员?"
                        ok-text="确定"
                        cancel-text="取消"
                        @confirm="confirmDeleteSalesPerson(record.id)"
                >
                    <a-button type="danger" size="small">删除</a-button>
                </a-popconfirm>
                <a-divider type="vertical"></a-divider>
                <a-button type="primary" size="small" @click="modifyInfo(record)">修改</a-button>
            </span>
        </a-table>
        <add-manager-modal></add-manager-modal>
        <modify-info-modal></modify-info-modal>
    </div>

</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import addManagerModal from "./addManagerModal";
    import modifyInfoModal from "./modifyInfoModal";
    export default {
        name: "manageSalesPerson",
        data() {
            return {
                searchText: '',
                searchInput: null,
                searchedColumn: '',
                columns_of_salesPerson: [
                    {
                        title: '用户邮箱',
                        dataIndex: 'email',
                        scopedSlots: {
                            filterDropdown: 'filterDropdown',
                            filterIcon: 'filterIcon',
                            customRender: 'customRender',
                        },
                        onFilter: (value, record) =>
                            record.email
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
                        title: '用户名',
                        dataIndex: 'userName',
                    },
                    {
                        title: '用户手机号',
                        dataIndex: 'phoneNumber',
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
            addManagerModal,
            modifyInfoModal,
        },
        computed: {
            ...mapGetters([
                'addSalesPersonModalVisible',
                'salesPersonList',
            ]),
        },
        async mounted() {
            await this.getSalesPersonList()
        },
        methods: {
            ...mapActions([
                'getSalesPersonList',
                'deleteSalesPerson',
            ]),
            ...mapMutations([
                'set_addSalesPersonModalVisible',
                'set_modifyUserInfo',
                'set_modifyInfoModalVisible',
            ]),
            addSalesPerson() {
                this.set_addSalesPersonModalVisible(true)
            },
            confirmDeleteSalesPerson(id) {
                this.deleteSalesPerson(Number(id))
            },
            modifyInfo(record) {
                this.set_modifyUserInfo(record)
                this.set_modifyInfoModalVisible(true)
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
    .manageManager-wrapper {
        padding: 50px;
    }
    .highlight {
        background-color: rgb(255, 192, 105);
        padding: 0px;
    }
</style>
