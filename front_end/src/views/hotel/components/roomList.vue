<template>
    <div class="room-list">
        <div class="filter">

        </div>
        <div class="list">
            <a-table
                :columns="columns"
                :dataSource="rooms"
                :rowKey="record => record.id"
                style="background-color: white; padding: 10px; margin-top: 20px; border-radius: 20px; "
                bordered
            >
                <span slot="price" slot-scope="text">
                    <a-tag color="pink">¥ {{ text }}</a-tag>
                </span>
                <span slot="curNum" slot-scope="text">
                    <a-tag color="purple">剩余房间：{{text}}</a-tag>
                </span>
                <span slot="action" slot-scope="record">
                    <a-button icon="plus-circle" type="primary" @click="order(record)">预定</a-button>
                </span>
            </a-table>
        </div>
        <OrderModal></OrderModal>
    </div>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import OrderModal from './orderModal'
const columns = [
    {  
      title: '房型',
      dataIndex: 'roomType',
    },
    {
      title: '床型',
      dataIndex: 'bedType',
    },
    {
      title: '早餐',
      dataIndex: 'breakfast',
    },
    {
      title: '入住人数',
      dataIndex: 'peopleNum',
    },
    {
      title: '房价',
      dataIndex: 'price',
      scopedSlots: { customRender: 'price'},
        sorter: (a, b) => a.price - b.price,
    },
    {
        title: '剩余房间',
        dataIndex: 'curNum',
        scopedSlots: { customRender: 'curNum'},
    },
    {
      title: '操作',
      scopedSlots: { customRender: 'action' },
    },
];
export default {
    name:'roomList',
    props: {
        rooms: {
            type: Array
        }
    },
    data() {
        return {
            columns,
        }
    },
    components: {
        OrderModal,
    },
    computed: {
        ...mapGetters([
            'orderModalVisible'
        ])
    },
    monuted() {

    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible',
            'set_currentOrderRoom',
            'set_orderSuccess'
        ]),
        ...mapActions([

        ]),
        order(record) {
            this.set_currentOrderRoom(record)
            this.set_orderSuccess(false)
            this.set_orderModalVisible(true)
        }
    }
}
</script>