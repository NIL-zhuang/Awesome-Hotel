<!-- created by glh 2020-05-23 -->
<!-- 用来处理异常订单的界面 -->
<template>
    <a-modal
        :visible="handleAbnormalOrderVisible"
        title="异常订单详情"
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
                {{ orderInfo.checkInDate }} - {{ orderInfo.checkOutDate }}
            </a-descriptions-item>
            <a-descriptions-item label="总价">
                {{ orderInfo.price }}
            </a-descriptions-item>
            <a-descriptions-item label="顾客申诉">
                <span v-if="currentOrderComment.comment !== ''">
                    {{ currentOrderComment.comment }}
                </span>
                <span v-else>
                    顾客尚未申诉
                </span>
            </a-descriptions-item>
        </a-descriptions>
        <br/>
        <br/>
        <div>
            <a-icon type="question-circle" theme="twoTone"/>
            <span> 有疑问请联系酒店方：
                <a-tag color="blue">
                    {{ currentHotelInfo.phoneNum }}
                </a-tag>
            </span>
            <div style="float: right">
                <a-button type="primary" @click="recoverHalf">恢复50%</a-button>
                <a-divider type="vertical"></a-divider>
                <a-button type="primary" @click="recoverAll">恢复100%</a-button>
                <a-divider type="vertical"></a-divider>
                <a-button type="primary" @click="handleOK">暂不处理</a-button>
            </div>
                    </div>
    </a-modal>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: "handleAbnormalOrder",
    data() {
        return {

        }
    },
    computed: {
        ...mapGetters([
            'handleAbnormalOrderVisible',
            'orderInfo',
            'currentHotelInfo',
            'currentOrderComment',
        ])
    },
    methods: {
        ...mapMutations([
            'set_handleAbnormalOrderVisible'
        ]),
        ...mapActions([
            'handleAbnormalOrder'
        ]),
        handleOK() {
            console.log(this.orderInfo)
            this.set_handleAbnormalOrderVisible(false)
        },
        cancel() {

            this.set_handleAbnormalOrderVisible(false)
        },
        recoverHalf() {
            const params = {
                orderId: this.orderInfo.id,
                ratio: 0.5,
            }
            this.handleAbnormalOrder(params)
        },
        recoverAll() {
            const params = {
                orderId: this.orderInfo.id,
                ratio: 1,
            }
            this.handleAbnormalOrder(params)
        }
    }
}
</script>

<style scoped>

</style>
