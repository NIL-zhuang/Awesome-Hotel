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
                bordered
        >
                    <span slot="action" slot-scope="record">
                        <a-popconfirm
                                title="确定想删除该酒店吗？"
                                @confirm="deleteHotelById(record.id)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除</a-button>
                        </a-popconfirm>
                    </span>
        </a-table>

        <addHotelModal></addHotelModal>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import addHotelModal from "../../admin/components/addHotelModal";
    import { message } from 'ant-design-vue';
    const columns_of_hotels = [
        {
            title: '酒店名',
            dataIndex: 'name',
        },
        {
            title: '商圈',
            dataIndex: 'bizRegion',
        },
        {
            title: '地址',
            dataIndex: 'address',
        },
        {
            title: '酒店星级',
            dataIndex: 'hotelStar'
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
            scopedSlots: { customRender: 'action' },
        },
    ];
    export default {
        name: "AdminHomePage",
        data() {
            return {
                columns_of_hotels,
            }
        },
        components: {
            addHotelModal,
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
            ]),
            ...mapActions([
                'getHotelList',
                'deleteHotel',
            ]),
            addHotel() {
                this.set_addHotelModalVisible(true)
            },
            deleteHotelById(id){
                this.deleteHotel(id)
            },
        }
    }
</script>

<style scoped lang="less">
    .manageHotel-wrapper {
        padding: 50px;
    }
</style>