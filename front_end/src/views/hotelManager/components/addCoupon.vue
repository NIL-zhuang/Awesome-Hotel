<template xmlns:a-form="http://www.w3.org/1999/html">
    <a-modal
            :visible="addCouponVisible"
            title="添加优惠策略"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px">
            <a-form-item label="优惠券类型" v-bind="formItemLayout">
                <a-select
                        v-decorator="['type',{rules: [{required: true, message: '请选择类型'}]}]"
                        @change="changeType">
                    <a-select-option value="1">生日特惠</a-select-option>
                    <a-select-option value="2">多间特惠</a-select-option>
                    <a-select-option value="3">满减特惠</a-select-option>
                    <a-select-option value="4">限时特惠</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="券名" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写券名"
                        allow-clear
                        v-decorator="['name',{rules: [{required: true, message: '请输入券名'}]}]"
                />
            </a-form-item>
            <a-form-item label="优惠简介" v-bind="formItemLayout">
                <a-tooltip>
                    <template slot="title">
                        请简单填写一下优惠券的介绍
                    </template>
                    <a-input
                            type="textarea"
                            :rows="4"
                            allow-clear
                            placeholder="请填写优惠券简介"
                            v-decorator="['description',{rules: [{required: true, message: '请填写优惠券简介'}]}]"
                    />
                </a-tooltip>
            </a-form-item>
            <a-form-item label="使用时间" v-bind="formItemLayout" v-if="isTimeLimitedCoupon">
                <a-range-picker
                        format="YYYY-MM-DD"
                        v-decorator="[
                            'date',
                            { rules: [{ required: true, message: '请选择使用时间' }]},
                        ]"
                />
            </a-form-item>
            <a-form-item label="达标预定数" v-bind="formItemLayout" v-if="isMultipleCoupon">
                <a-input-number id="inputNumber" v-model="multipleNum" :min="1"/>
                <br/>当前值：{{ multipleNum }}
            </a-form-item>
            <a-form-item label="折扣" v-bind="formItemLayout" v-if="!isReductionCoupon">
                <a-tooltip>
                    <template slot="title">
                        请输入一个小于100%的折扣
                    </template>
                    <a-input-number
                            :min="0"
                            :max="99"
                            :formatter="value => `${value}%`"
                            :parser="value => value.replace('%', '')"
                            v-decorator="['discount',{rules: [{required: true, message: '请输入折扣'}], initialValue: 99}]"
                    />
                </a-tooltip>
            </a-form-item>
            <a-form-item label="达标金额" v-bind="formItemLayout" v-if="isReductionCoupon">
                <a-input
                        placeholder="请填写达标金额"
                        v-decorator="['targetMoney',{rules: [{required: true, message: '请填写达标金额'}]}]"
                />
            </a-form-item>
            <a-form-item label="优惠金额" v-bind="formItemLayout" v-if="isReductionCoupon">
                <a-input
                        placeholder="请填写优惠金额"
                        v-decorator="['discountMoney',{rules: [{required: true, message: '请填写优惠金额'}]}]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: 'addCouponModal',
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
                isMultipleCoupon: false,
                isReductionCoupon: false,
                isTimeLimitedCoupon: false,
                multipleNum: 1,
            }
        },
        computed: {
            ...mapGetters([
                'activeHotelId',
                'addCouponVisible',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'addCouponModal'});
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addCouponVisible'
            ]),
            ...mapActions([
                'addHotelCoupon'
            ]),
            cancel() {
                this.set_addCouponVisible(false)
            },
            resetAll(){
                this.isReductionCoupon = false
                this.isTimeLimitedCoupon = false
                this.isMultipleCoupon = false
            },
            changeType(v) {
                this.resetAll()
                if (v==='2'){
                    this.isMultipleCoupon = true
                } else if (v === '3') {
                    this.isReductionCoupon = true
                } else if (v === '4') {
                    this.isTimeLimitedCoupon = true
                } else {

                }
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            name: this.form.getFieldValue('name'),
                            description: this.form.getFieldValue('description'),
                            type: Number(this.form.getFieldValue('type')),
                            discount: this.type === 1 ? 1.00 : this.form.getFieldValue('discount') / 100,
                            targetMoney: Number(this.form.getFieldValue('targetMoney')),
                            discountMoney: Number(this.form.getFieldValue('discountMoney')),
                            hotelId: Number(this.activeHotelId),
                            status: 1,
                        }
                        this.addHotelCoupon(data)
                    }
                });
            },
        }
    }
</script>
