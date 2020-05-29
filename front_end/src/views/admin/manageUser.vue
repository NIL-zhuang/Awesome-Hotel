<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店工作人员" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addManager"><a-icon type="plus" />添加酒店工作人员</a-button>
                </div>
                <a-table
                    :columns="columns_of_manager"
                    :dataSource="managerList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="text, record">
                        <a-button type="danger" @click="order(record)">删除用户</a-button>
                    </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="网站营销人员" key="2">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addSalesPerson"><a-icon type="plus" />添加网站营销人员</a-button>
                </div>
                <a-table
                        :columns="columns_of_salesPerson"
                        :dataSource="salesPersonList"
                        bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="text, record">
                        <a-button type="danger" @click="order(record)">删除用户</a-button>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddManagerModal></AddManagerModal>
        <addSalesPersonModal></addSalesPersonModal>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddManagerModal from './components/addManagerModal'
import addSalesPersonModal from "./components/addSalesPersonModal";
const columns_of_manager = [
    {  
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {  
        title: '用户名',
        dataIndex: 'userName',
    },
    {
        title: '所属酒店',
        dataIndex: 'hotelID',
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
  ];
const columns_of_salesPerson = [
    {
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {
        title: '用户名',
        dataIndex: 'userName',
    },
    {
        title: '用户密码',
        dataIndex: 'password',
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
];
export default {
    name: 'manageHotel',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns_of_manager,
            columns_of_salesPerson,
            data: [],
            form: this.$form.createForm(this, { name: 'manageUser' }),
        }
    },
    components: {
        AddManagerModal,
        addSalesPersonModal,
    },
    computed: {
        ...mapGetters([
            'addManagerModalVisible',
            'addSalesPersonModalVisible',
            'managerList',
            'salesPersonList',
        ])
    },
    mounted() {
        this.getManagerList()
        this.getSalesPersonList()
    },
    methods: {
        ...mapActions([
            'getManagerList',
            'getSalesPersonList',
        ]),
        ...mapMutations([
            'set_addManagerModalVisible',
            'set_addSalesPersonModalVisible',
        ]),
        addManager() {
            this.set_addManagerModalVisible(true)
        },
        addSalesPerson() {
            this.set_addSalesPersonModalVisible(true)
        },
    }
}
</script>
<style scoped lang="less">
    .manageUser-wrapper {
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
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>