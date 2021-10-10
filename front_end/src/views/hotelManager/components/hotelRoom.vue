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
                :rowKey="record=>record.id"
                style="background-color: white; padding: 10px; border-radius: 20px"
                :locale="{emptyText: '暂时没有客房信息'}"
                bordered
        >
            <span slot="price" slot-scope="text">
                <a-tag color="pink">¥ {{ text }}</a-tag>
            </span>
            <span slot="total" slot-scope="text">
                <a-tag color="purple">房间数：{{text}}</a-tag>
            </span>
            <span slot="action" slot-scope="record">
                <a-button @click="deleteHotelRoom(record.bedType)" icon="close-circle" type="danger" size="small">删除</a-button>
            </span>
        </a-table>

        <addRoomModal></addRoomModal>
    </div>

</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import addRoomModal from "./addRoomModal";
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
        title: '房间数目',
        dataIndex: 'total',
        scopedSlots: { customRender: 'total'},
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
        addRoomModal,
    },
    computed: {
        ...mapGetters([
            'hotelInfo',
            'userInfo'
        ]),
        roomList() {
            return this.hotelInfo.rooms
        }
    },
    methods: {
        ...mapActions([
            'deleteRoom'
        ]),
        ...mapMutations([
            'set_addRoomModalVisible'
        ]),

        addRoom() {
            this.set_addRoomModalVisible(true)
        },
        deleteHotelRoom(t){
            const params = {
                hotelId: this.userInfo.hotelID,
                roomType: t==='单人床'?'BigBed':t==='双人床'?'DoubleBed':t==='三人床'?'Family':'Luxury'
            }
            this.deleteRoom(params)
        },
    }

}
</script>

<style scoped>

</style>