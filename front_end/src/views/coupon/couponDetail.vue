<template>
    <a-modal
            :footer="null"
            :visible="couponDetailVisible"
            @cancel="cancel"
            title="优惠详情"
            width="800px"
    >
        <div v-if="currentCouponInfo.srcId">
            <a-descriptions bordered size="default">
                <a-descriptions-item label="优惠券名称">
                    <a-tag color="purple">{{ currentCouponInfo.couponName }}</a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="优惠券类型">
                    <a-tag color="pink">{{ currentCouponInfo.couponType === 1 ? '生日优惠' :
                        currentCouponInfo.couponType === 2 ? '多间优惠' :
                        currentCouponInfo.couponType === 3 ? '满减优惠' : '限时优惠'}}
                    </a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="折扣" v-if="currentCouponInfo.couponType !== 3">
                    <a-tag color="blue">{{currentCouponInfo.discount*100}}%</a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="达标单次预定房间数" v-if="currentCouponInfo.couponType === 2">
                    <a-tag color="orange">{{currentCouponInfo.targetRoomNum}}</a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="达标金额" v-if="currentCouponInfo.couponType === 3">
                    <a-tag color="pink">{{currentCouponInfo.targetMoney}}</a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="优惠金额" v-if="currentCouponInfo.couponType === 3">
                    <a-tag color="red">{{currentCouponInfo.discountMoney}}</a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="优惠券简介">
                    {{ currentCouponInfo.description }}
                </a-descriptions-item>
                <a-descriptions-item label="使用时间" v-if="currentCouponInfo.couponType === 4">
                    {{currentCouponInfo.startTime}} ～ {{currentCouponInfo.endTime}}
                </a-descriptions-item>
            </a-descriptions>
            <br/>
            <div>
                <a-icon theme="twoTone" type="info-circle"/>
                <span>满减优惠券优惠固定的金额，其他优惠券优惠方式为打折</span>
                <a-button @click="handleOK" size="small" style="float: right" type="primary">确定</a-button>
            </div>
        </div>
        <div v-else>
            <a-descriptions bordered size="default">
                <a-descriptions-item label="优惠券名称">
                    <a-tag color="purple">{{ currentCouponInfo.couponName }}</a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="优惠券类型">
                    <a-tag color="pink">{{ currentCouponInfo.couponType === 1 ? '节日优惠' :
                        currentCouponInfo.couponType === 2 ? 'VIP指定商圈优惠' : '企业优惠'}}
                    </a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="折扣">
                    <a-tag color="red">{{currentCouponInfo.discount*100}}%</a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="优惠券简介">
                    {{ currentCouponInfo.description }}
                </a-descriptions-item>
                <a-descriptions-item label="使用时间" v-if="currentCouponInfo.couponType === 1">
                    {{currentCouponInfo.startTime}} ～ {{currentCouponInfo.endTime}}
                </a-descriptions-item>
                <a-descriptions-item label="受用商圈" v-if="currentCouponInfo.couponType === 2">
                    {{currentCouponInfo.bizRegion}}
                </a-descriptions-item>
                <a-descriptions-item label="受用VIP等级" v-if="currentCouponInfo.couponType === 2">
                    <a-tag color="blue">{{currentCouponInfo.vipLevel}}</a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="受用VIP企业" v-if="currentCouponInfo.couponType === 3">
                    <a-tag color="blue">{{currentCouponInfo.corporateName}}</a-tag>
                </a-descriptions-item>
            </a-descriptions>
            <br/>
            <div>
                <a-icon theme="twoTone" type="info-circle"/>
                <span>合作企业优惠只对指定企业的顾客可用，而VIP指定商圈优惠各个等级VIP顾客能享受的折扣不同</span>
                <a-button @click="handleOK" size="small" style="float: right" type="primary">确定</a-button>
            </div>
        </div>
    </a-modal>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: 'couponDetail',
        data() {
            return {}
        },

        computed: {
            ...mapGetters([
                'couponDetailVisible',
                'currentCouponInfo',
            ])
        },

        beforeCreate() {

        },

        mounted() {

        },

        methods: {
            ...mapMutations([
                'set_couponDetailVisible'
            ]),
            handleOK() {
                this.set_couponDetailVisible(false)
            },
            cancel() {
                this.set_couponDetailVisible(false)
            }
        }

    }
</script>

<style scoped>

</style>