<template>
    <a-modal
            :visible="orderModalVisible"
            @cancel="cancelOrder"
            @ok="confirmOrder"
            cancelText="取消"
            okText="下单"
            title="预定详情"
    >
        <a-form :form="form">
            <a-form-item label="房型信息" v-bind="formItemLayout">
                <span>{{ currentOrderRoom.roomType }}</span>
            </a-form-item>
            <a-form-item label="入住-退房日期" v-bind="formItemLayout">
                <a-range-picker
                        :disabled="true"
                        format="YYYY-MM-DD"
                        v-decorator="[
                            'date',
                            { rules: [{ required: true, message: '请选择入住时间' }], initialValue: dateRange },
                        ]"
                />
            </a-form-item>
            <a-form-item label="入住人姓名" v-bind="formItemLayout">
                <a-input
                        v-decorator="[
                        'clientName',
                        { rules: [{required: true, message: '请填写入住人姓名' }], validateTrigger: 'blur', initialValue: this.userInfo.userName }
                    ]"
                />
            </a-form-item>
            <a-form-item label="手机号" v-bind="formItemLayout">
                <a-input
                        v-decorator="[
                        'phoneNumber',
                        { rules: [{ required: true, message: '请输入联系人手机' }, { validator: this.handlePhoneNumber }], validateTrigger: 'blur', initialValue: this.userInfo.phoneNumber }
                    ]"
                />
            </a-form-item>
            <a-form-item label="房间数" v-bind="formItemLayout">
                <a-select
                        @change="changeRoomNum"
                        placeholder="请选择房间数"
                        v-decorator="[
                        'roomNum',
                        { rules: [{ required: true, message: '请选择房间数' }] },
                    ]"
                >
                    <a-select-option :value="1">
                        1
                    </a-select-option>
                    <a-select-option :value="2">
                        2
                    </a-select-option>
                    <a-select-option :value="3">
                        3
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="入住人数" v-bind="formItemLayout" v-if="this.form.getFieldValue('roomNum')">
                <a-input
                        placeholder="请选择入住人数，不能超过房间容纳人数"
                        v-decorator="[
                        'peopleNum',
                        { rules: [{ required: true, message: '请输入房间能容纳的入住人数' }, { validator : this.handlePeopleNum }],
                        validateTrigger: 'blur', initialValue: this.currentOrderRoom.peopleNum*Number(this.form.getFieldValue('roomNum'))},
                    ]"
                >
                </a-input>
            </a-form-item>
            <a-form-item label="有无儿童" v-bind="formItemLayout">
                <a-radio-group
                        v-decorator="[
                        'haveChild',
                        { rules: [{required: true, message: '请选择有无儿童入住', }, { validator : this.handleChildren}],
                        initialValue: 0 }
                    ]"
                >
                    <a-radio :value="1">有</a-radio>
                    <a-radio :value="0">无</a-radio>
                </a-radio-group>
            </a-form-item>
            <a-form-item label="房间单价" v-bind="formItemLayout">
                <span>{{ currentOrderRoom.price }}</span>
            </a-form-item>
            <a-form-item label="总价" v-bind="formItemLayout">
                <span>￥{{ totalPrice }}</span>
            </a-form-item>
            <a-divider></a-divider>
            <h2 v-if="orderMatchCouponList.length>0">优惠</h2>
            <a-radio-group @change="onCouponChange" defaultValue="0" v-model="checked">
                <a-table
                        :columns="columns"
                        :dataSource="orderMatchCouponList"
                        :rowKey="record=>record.id"
                        bordered
                        v-if="orderMatchCouponList.length>0"
                >
                    <a-radio
                            :value="record"
                            slot="id"
                            slot-scope="record"
                    >
                    </a-radio>
                    <span slot="discount" slot-scope="text">{{ text>0 ? (text * 100)+'%': '无' }}</span>
                </a-table>
            </a-radio-group>
            <a-form-item v-bind="formItemLayout" v-if="this.userInfo.vipType!=='Normal'">
                <a-tag color="blue">您是VIP顾客，当前等级{{this.userVIP.level}}，当前享受{{this.userVIP.reduction*100}}%折扣</a-tag>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" v-if="this.isCorpVIP">
                <a-tag color="blue">您的所属企业是我们的VIP企业，当前等级{{this.corpVIP.level}}，当前享受{{this.corpVIP.reduction*100}}%折扣
                </a-tag>
            </a-form-item>
            <a-form-item label="结算后总价" v-bind="formItemLayout">
                <span>￥{{ finalPrice ? finalPrice : totalPrice * vipDiscount * corpDiscount.toFixed(2)}}</span>
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import {message} from 'ant-design-vue'

    const moment = require('moment')
    const columns = [
        {
            title: '勾选',
            dataIndex: 'id',
            scopedSlots: {customRender: 'id'}
        },
        {
            title: '优惠名称',
            dataIndex: 'couponName',
            scopedSlots: {customRender: 'couponName'}
        },
        {
            title: '折扣',
            dataIndex: 'discount',
            scopedSlots: {customRender: 'discount'}
        },

        {
            title: '优惠简介',
            dataIndex: 'description',

        },
        {
            title: '满减优惠金额',
            dataIndex: 'discountMoney',
        },
    ];
    export default {
        name: 'orderModal',
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: {span: 12},
                        sm: {span: 6},
                    },
                    wrapperCol: {
                        xs: {span: 24},
                        sm: {span: 16},
                    },
                },
                totalPrice: '',
                columns,
                checked: 0,
                finalPrice: '',
                vipDiscount: 1.00,
                corpDiscount: 1.00,
            }
        },
        computed: {
            ...mapGetters([
                'orderModalVisible',
                'currentOrderRoom',
                'currentHotelId',
                'currentHotelInfo',
                'orderSuccess',
                'userId',
                'userInfo',
                'userVIP',
                'corpVIP',
                'isCorpVIP',
                'dateRange',
                'orderMatchCouponList'
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'orderModal'});
        },
        async mounted() {
            await this.getUserInfo() //防止一刷新就丢失userInfo
            if (this.userInfo.vipType !== 'Normal') {
                await this.getUserVIP(this.userId)
                if (this.userVIP.status === 1)
                    this.vipDiscount -= this.userVIP.reduction
            }
            if (this.userInfo.corporation) {
                await this.corpVIPCheck(this.userInfo.corporation)
                if (this.isCorpVIP) {
                    await this.getCorpVIP(this.userInfo.corporation)
                    this.corpDiscount -= this.corpVIP.reduction
                }
            }
        },
        methods: {
            ...mapMutations([
                'set_orderModalVisible',
                'set_orderMatchCouponList'
            ]),
            ...mapActions([
                'addOrder',
                'getOrderMatchCoupons',
                'getUserVIP',
                'getCorpVIP',
                'corpVIPCheck',
                'getUserInfo',
            ]),
            cancelOrder() {
                this.checked = 0
                this.set_orderMatchCouponList([])
                this.totalPrice = null
                this.finalPrice = null
                this.form.resetFields()
                this.set_orderModalVisible(false)
            },
            confirmOrder(e) {
                let outer = this
                this.$confirm({
                    title: '确定填写无误?',
                    content: '点击确认按钮后，自动提交订单。如果还想检查订单请点击取消。',
                    okText: '确认',
                    cancelText: '取消',
                    onOk() {
                        outer.handleSubmit(e)
                    },
                    onCancel() {
                    },
                });
            },
            changeRoomNum(v) {
                this.totalPrice = Number(v) * Number(this.currentOrderRoom.price) * moment(this.form.getFieldValue('date')[1]).diff(moment(this.form.getFieldValue('date')[0]), 'day')
                this.onCouponChange()
            },
            onCouponChange() {
                this.finalPrice = Math.round(this.totalPrice * this.vipDiscount * this.corpDiscount * 100) * 0.01
                if (this.checked !== 0) {
                    for (const item of this.orderMatchCouponList) {
                        if (item.id === this.checked) {
                            if (item.discountMoney !== 0) this.finalPrice = this.finalPrice - item.discountMoney
                            else this.finalPrice = this.finalPrice * item.discount
                            break
                        }
                    }
                }
                this.finalPrice = this.finalPrice.toFixed(2)
            },
            handlePhoneNumber(rule, value, callback) {
                const re = /1\d{10}/;
                if (re.test(value)) {
                    callback();
                } else {
                    if (value === '') {
                        callback()
                    } else {
                        callback(new Error('请输入有效联系人手机号'));
                    }
                }
                callback()
            },
            handlePeopleNum(rule, value, callback) {
                if (value <= 0 || value > this.currentOrderRoom.peopleNum * Number(this.form.getFieldValue('roomNum'))) {
                    callback(new Error('请输入房间能容纳的入住人数'))
                } else {
                    callback()
                }
            },
            handleChildren(rule, value, callback) {
                if ((!this.form.getFieldValue('peopleNum') || Number(this.form.getFieldValue('peopleNum')) === 1)
                    && Number(this.form.getFieldValue('haveChild')) === 1)
                    callback(new Error('未成年人不能单独入住'))
                else callback()
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll(async (err, values) => {
                    if (!err) {
                        const data = {
                            hotelId: this.currentHotelId,
                            hotelName: this.currentHotelInfo.name,
                            hotelPhoneNum: this.currentHotelInfo.phoneNum,
                            userId: Number(this.userId),
                            checkInDate: this.form.getFieldValue('date')[0].format("YYYY-MM-DD"),
                            checkOutDate: this.form.getFieldValue('date')[1].format("YYYY-MM-DD"),
                            roomType: this.currentOrderRoom.roomType === '大床房' ? 'BigBed' : this.currentOrderRoom.roomType === '双床房' ? 'DoubleBed' : 'Family',
                            roomNum: this.form.getFieldValue('roomNum'),
                            peopleNum: this.form.getFieldValue('peopleNum'),
                            haveChild: this.form.getFieldValue('haveChild'),
                            clientName: this.form.getFieldValue('clientName'),
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                            createDate: '',
                            price: this.finalPrice
                        }
                        await this.addOrder(data)
                        this.form.resetFields()
                        this.set_orderMatchCouponList([])
                        if (this.orderSuccess) {
                            await this.$router.push('/successOrder')
                        }
                    }
                });
            },
        },
        watch: {
            totalPrice() {
                if (this.totalPrice) {
                    let data = {
                        userId: this.userId,
                        hotelId: this.currentHotelId,
                        orderPrice: this.totalPrice,
                        roomNum: this.form.getFieldValue('roomNum'),
                        checkIn: this.form.getFieldValue('date')[0].format('YYYY-MM-DD'),
                        checkOut: this.form.getFieldValue('date')[1].format('YYYY-MM-DD'),
                    }
                    this.getOrderMatchCoupons(data)
                }
            }
        }
    }
</script>
