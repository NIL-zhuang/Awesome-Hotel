<template>
    <div>
        <a-table
                :columns="columns"
                :dataSource="allClientVIPList"
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

            <a-tag slot="status" color="red" slot-scope="text">
                <span v-if="text===1">正常</span>
                <span v-else>被冻结</span>
            </a-tag>

            <span slot="action" slot-scope="record">
                <a-button type="primary" size="small" v-if="record.status===1" @click="freezeClient(record.userId)">冻结</a-button>
                <a-button type="primary" size="small" v-else @click="restoreClient(record.userId)">恢复</a-button>
            </span>
        </a-table>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    const columns = [
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

    ];
    export default {
        name: "ManageClientMembership",
        data() {
            return {
                columns,
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
                console.log('freeze' + userId)
                this.freezeClientVIP(userId)
            },
            restoreClient(userId) {
                console.log('restore ' + userId)
                this.restoreClientVIP(userId)
            },
            userName (userId) {
                for(var i = 0;i < this.allUserList.length;i++) {
                    if (this.allUserList[i].id === userId)
                        return this.allUserList[i].userName
                }
                return ""
            },
        }
    }
</script>

<style scoped>

</style>
