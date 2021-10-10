<template>
    <div>
        <a-table
            :columns="columns"
            :dataSource="allClientVIPList"
            :locale="{emptyText: '暂时没有普通会员'}"
            :rowKey="record => record.userId"
            bordered
        >
            <a-tag slot="userId" color="orange" slot-scope="text">
                {{ userName(text) }}
            </a-tag>

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
                    title="确定冻结该会员吗?"
                    ok-text="确定"
                    cancel-text="取消"
                    @confirm="freezeClient(record.userId)"
                    @cancel="cancel"
                    v-if="record.status===1"
                >
                    <a-button type="danger" size="small" >冻结</a-button>
                </a-popconfirm>

                <a-popconfirm
                        title="确定恢复该会员吗?"
                        ok-text="确定"
                        cancel-text="取消"
                        @confirm="restoreClient(record.userId)"
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
        name: "ManageClientMembership",
        data() {
            return {
                searchText: '',
                searchInput: null,
                searchedColumn: '',
                columns: [
                    {
                        title: '用户名',
                        dataIndex: 'userId',
                        scopedSlots: { customRender: 'userId' }
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
                        scopedSlots: { customRender: 'status' }
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
                'allClientVIPList',
                'allUserList',
            ]),
        },
        async mounted() {
            await this.getAllUsers()
            await this.getAllClientVIP()
        },
        methods: {
            ...mapActions([
                'getAllUsers',
                'getAllClientVIP',
                'freezeClientVIP',
                'restoreClientVIP',
            ]),
            freezeClient(userId) {
                this.freezeClientVIP(userId)
            },
            restoreClient(userId) {
                this.restoreClientVIP(userId)
            },
            userName(userId) {
                for (var i = 0; i < this.allUserList.length; i++) {
                    if (this.allUserList[i].id === userId)
                        return this.allUserList[i].userName
                }
                return ""
            },
            cancel() {
            },
        }
    }
</script>

<style scoped>
</style>
