<!-- created by glh 2020-05-23 -->
<!-- 添加网站优惠券 -->
<template xmlns:a-form="http://www.w3.org/1999/html">
    <a-modal
            :visible="addSiteCouponVisible"
            @cancel="cancel"
            @ok="handleSubmit"
            cancelText="取消"
            okText="确定"
            title="添加优惠策略"
    >
        <span><h3>制定想要的优惠券</h3><i>注：VIP指定商圈优惠一次会制定5张优惠券，分别对应5个等级</i></span>
        <a-form :form="form" style="margin-top: 30px">
            <a-form-item label="优惠券类型" v-bind="formItemLayout">
                <a-select
                        @change="changeType"
                        v-decorator="['type',{rules: [{required: true, message: '请选择类型'}]}]">
                    <a-select-option value="1">节日优惠</a-select-option>
                    <a-select-option value="2">VIP指定商圈优惠</a-select-option>
                    <a-select-option value="3">合作企业优惠</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="券名" v-bind="formItemLayout">
                <a-input
                        allow-clear
                        placeholder="请填写券名"
                        v-decorator="['name',{rules: [{required: true, message: '请输入券名'}]}]"
                />
            </a-form-item>
            <a-form-item label="优惠简介" v-bind="formItemLayout">
                <a-input
                        :rows="4"
                        allow-clear
                        placeholder="请填写优惠券简介"
                        type="textarea"
                        v-decorator="['description',{rules: [{required: true, message: '请填写优惠券简介'}]}]"
                />
            </a-form-item>
            <a-form-item label="使用时间" v-bind="formItemLayout" v-if="isTimeLimitedCoupon">
                <a-range-picker
                        :placeholder="['开始日期','结束日期']"
                        format="YYYY-MM-DD"
                        v-decorator="[
                            'date',
                            { rules: [{ required: true, message: '请选择使用时间' }]},
                        ]"
                />
            </a-form-item>
            <a-form-item label="商圈" v-bind="formItemLayout" v-if="isBizRegionCoupon">
                <a-input
                        placeholder="请填写指定商圈"
                        v-decorator="[
                            'bizRegion',
                            { rules: [{ required: true, message: '请填写指定商圈' }]},
                        ]"
                >
                </a-input>
            </a-form-item>
            <a-form-item label="企业名称" v-bind="formItemLayout" v-if="isCorporatonCoupon">
                <a-input
                        placeholder="请填写企业名称"
                        v-decorator="[
                            'corporation',
                            { rules: [{ required: true, message: '请填写企业名称' }]},
                        ]"
                >
                </a-input>
            </a-form-item>
            <a-form-item label="折扣" v-bind="formItemLayout" v-if="!isBizRegionCoupon">
                <a-tooltip>
                    <template slot="title">
                        请输入一个小于100%的折扣
                    </template>
                    <a-input-number
                            :formatter="value => `${value}%`"
                            :max="99"
                            :min="0"
                            :parser="value => value.replace('%', '')"
                            v-decorator="['discount',{rules: [{required: true, message: '请输入折扣'}], initialValue: 99}]"
                    />
                </a-tooltip>
            </a-form-item>
            <a-form-item label="VIPLv1减免" v-bind="formItemLayout" v-if="isBizRegionCoupon">
                <a-input-number
                        :formatter="value => `${value}%`"
                        :max="99"
                        :min="1"
                        :parser="value => value.replace('%', '')"
                        v-decorator="['lv1',{rules: [{required: true, message: '请输入减免额度'}], initialValue: 1}]"
                />
            </a-form-item>
            <a-form-item label="VIPLv2减免" v-bind="formItemLayout" v-if="isBizRegionCoupon">
                <a-input-number
                        :formatter="value => `${value}%`"
                        :max="99"
                        :min="1"
                        :parser="value => value.replace('%', '')"
                        v-decorator="['lv2',{rules: [{required: true, message: '请输入减免额度'}], initialValue: 2}]"
                />
            </a-form-item>
            <a-form-item label="VIPLv3减免" v-bind="formItemLayout" v-if="isBizRegionCoupon">
                <a-input-number
                        :formatter="value => `${value}%`"
                        :max="99"
                        :min="1"
                        :parser="value => value.replace('%', '')"
                        v-decorator="['lv3',{rules: [{required: true, message: '请输入减免额度'}], initialValue: 3}]"
                />
            </a-form-item>
            <a-form-item label="VIPLv4减免" v-bind="formItemLayout" v-if="isBizRegionCoupon">
                <a-input-number
                        :formatter="value => `${value}%`"
                        :max="99"
                        :min="1"
                        :parser="value => value.replace('%', '')"
                        v-decorator="['lv4',{rules: [{required: true, message: '请输入减免额度'}], initialValue: 4}]"
                />
            </a-form-item>
            <a-form-item label="VIPLv5减免" v-bind="formItemLayout" v-if="isBizRegionCoupon">
                <a-input-number
                        :formatter="value => `${value}%`"
                        :max="99"
                        :min="1"
                        :parser="value => value.replace('%', '')"
                        v-decorator="['lv5',{rules: [{required: true, message: '请输入减免额度'}], initialValue: 5}]"
                />
            </a-form-item>
        </a-form>
    </a-modal>

</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: "AddSiteCoupon",
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
                isTimeLimitedCoupon: false,
                isBizRegionCoupon: false,
                isCorporatonCoupon: false,
            }
        },
        computed: {
            ...mapGetters([
                'addSiteCouponVisible',
            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, {name: 'addCouponModal'});
        },
        methods: {
            ...mapMutations([
                'set_addSiteCouponVisible'
            ]),
            ...mapActions([
                'addSiteCoupon',
                'getSiteCoupon',
            ]),
            cancel() {
                this.set_addSiteCouponVisible(false)
            },
            resetAll() {
                this.isTimeLimitedCoupon = false
                this.isBizRegionCoupon = false
            },
            changeType(v) {
                this.resetAll()
                if (v === '1') {
                    this.isTimeLimitedCoupon = true
                } else if (v === '2') {
                    this.isBizRegionCoupon = true
                } else if (v === '3')
                    this.isCorporatonCoupon = true
            },
            handleSubmit(e) {
                e.preventDefault()
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        let data = {
                            name: this.form.getFieldValue('name'),
                            description: this.form.getFieldValue('description'),
                            type: Number(this.form.getFieldValue('type')),
                            corporateName: this.isCorporatonCoupon ? this.form.getFieldValue('corporation') : null,
                            startTime: this.isTimeLimitedCoupon ? this.form.getFieldValue('date')[0].format('YYYY-MM-DD') : null,
                            endTime: this.isTimeLimitedCoupon ? this.form.getFieldValue('date')[1].format('YYYY-MM-DD') : null,
                            region: this.isBizRegionCoupon ? this.form.getFieldValue('bizRegion') : null,
                            discount: !this.isBizRegionCoupon ? this.form.getFieldValue('discount') / 100 : null,
                            srcId: 0,
                            status: 1,
                        }
                        if (data.type === 2) {
                            for (let i = 1; i <= 5; i++) {
                                const reduce = this.form.getFieldValue('lv'+i)
                                data = {...data, vipLevel: i, discount: 1-reduce/100}
                                console.log(data)
                                this.addSiteCoupon(data)
                            }
                        } else {
                            this.addSiteCoupon(data)
                        }
                        this.form.resetFields()
                    }
                });
            },
        },
    }
</script>

<style scoped>

</style>