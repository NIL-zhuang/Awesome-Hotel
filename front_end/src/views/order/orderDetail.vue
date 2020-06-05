<template>
    <a-modal
        :visible="orderDetailVisible"
        title="订单详情"
        :footer="null"
        width="800px"
        @cancel="cancel"
    >
        <a-descriptions bordered size="default">
            <a-descriptions-item label="客户名">
                {{ orderInfo.clientName }}
            </a-descriptions-item>
            <a-descriptions-item label="联系方式">
                {{ orderInfo.phoneNumber }}
            </a-descriptions-item>
            <a-descriptions-item label="下单时间">
                {{ orderInfo.createDate }}
            </a-descriptions-item>
            <a-descriptions-item label="酒店名称">
                {{ currentHotelInfo.name }}
            </a-descriptions-item>
            <a-descriptions-item label="酒店地址" span="2">
                {{ currentHotelInfo.bizRegion }} - {{ currentHotelInfo.address }}
            </a-descriptions-item>
            <a-descriptions-item label="房间类型">
                <span v-if="orderInfo.roomType === 'BigBed'">大床房</span>
                <span v-else-if="orderInfo.roomType === 'DoubleBed'">双床房</span>
                <span v-else-if="orderInfo.roomType === 'Family'">家庭房</span>
                <span v-else>{{ orderInfo.roomType }}</span>
            </a-descriptions-item>
            <a-descriptions-item label="房间数量">
                {{ orderInfo.roomNum }}
            </a-descriptions-item>
            <a-descriptions-item label="预订人数">
                {{ orderInfo.peopleNum }}
                <span v-if="orderInfo.haveChild">(有儿童)</span>
                <span v-else>(无儿童)</span>
            </a-descriptions-item>
            <a-descriptions-item label="入住与离店时间" span="2">
                {{ orderInfo.checkInDate }} ～ {{ orderInfo.checkOutDate }}
            </a-descriptions-item>
            <a-descriptions-item label="总价">
                {{ orderInfo.price }}
            </a-descriptions-item>
        </a-descriptions>
        <br/>
        <br/>
        <div>
            <a-icon type="question-circle" theme="twoTone"/>
            <span> 有疑问请咨询：
                <a-tag color="blue">
                    {{ orderInfo.hotelPhoneNum }}
                </a-tag>
            </span>
            <a-button type="primary" @click="handleOK" style="float: right">确定</a-button>
        </div>
    </a-modal>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: "OrderDetail",
    data () {
        return  {

        }
    },

    computed: {
        ...mapGetters([
            'orderDetailVisible',
            'orderInfo',
            'currentHotelInfo',
        ])
    },

    beforeCreate() {

    },

    mounted() {

    },

    methods: {
        ...mapMutations([
            'set_orderDetailVisible'
        ]),
        handleOK() {
            this.set_orderDetailVisible(false)
        },
        cancel() {
            this.set_orderDetailVisible(false)
        },
    }

}
</script>

<style scoped>

</style>
