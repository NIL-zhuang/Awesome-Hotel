<template>
    <div class="client-wrapper">
        <a-table
            :columns="columns_of_Client"
            :dataSource="userList"
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
                <a-button type="primary" size="small" @click="modifyInfo(record)">修改信息</a-button>
            </span>
        </a-table>
        <modify-info-modal></modify-info-modal>
    </div>

</template>

<script>
    import modifyInfoModal from "./modifyInfoModal";
    import { mapGetters, mapMutations, mapActions } from 'vuex'

    export default {
        name: "manageClient",
        data() {
            return {
                searchText: '',
                searchInput: null,
                searchedColumn: '',
                columns_of_Client: [
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
            modifyInfoModal,
        },
        computed: {
            ...mapGetters([
                'allUserList',
            ]),
            userList() {
                return this.allUserList.filter(function (x) {
                    return x.userType === 'Client'
                })
            }
        },
        async mounted() {
            await this.getAllUsers()
        },
        methods: {
            ...mapActions([
                'getAllUsers',
            ]),
            ...mapMutations([
                'set_modifyUserInfo',
                'set_modifyInfoModalVisible',
            ]),
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

<style scoped>
    .client-wrapper {
        padding: 50px;
    }
    .highlight {
        background-color: rgb(255, 192, 105);
    }
</style>
