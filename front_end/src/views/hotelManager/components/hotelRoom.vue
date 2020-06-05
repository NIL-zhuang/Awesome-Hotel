<template>
    <div>
        <div style="width: 100%; text-align: right; margin: 20px 0">
            <a-button type="primary" @click="addRoom">
                <a-icon type="plus"/>录入客房
            </a-button>
        </div>

        <a-table
                :columns="columns_of_rooms"
                :dataSource="roomList"
                bordered
        >
            <span slot="price" slot-scope="text">
                <a-tag color="pink">¥ {{ text }}</a-tag>
            </span>
            <span slot="curNum" slot-scope="text">
                <a-tag color="purple">剩余房间：{{text}}</a-tag>
            </span>
            <span slot="action">
                <a-button type="danger" size="small">删除</a-button>
            </span>
        </a-table>

        <addRoomModal></addRoomModal>
    </div>

</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddRoomModal from "./addRoomModal";
const columns_of_rooms = [
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
    name: "HotelRoom",
    data() {
        return {
            columns_of_rooms,
        }
    },
    components: {
        AddRoomModal,
    },
    computed: {
        ...mapGetters([
            'hotelInfo'
        ]),
        roomList() {
            return this.hotelInfo.rooms
        }
    },
    methods: {
        ...mapMutations([
            'set_addRoomModalVisible'
        ]),

        addRoom() {
            this.set_addRoomModalVisible(true)
        }
    }

}
</script>

<style scoped>

</style>