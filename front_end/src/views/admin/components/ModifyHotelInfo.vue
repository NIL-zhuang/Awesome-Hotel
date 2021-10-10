<template>
    <a-modal
            :visible="modifyHotelModalVisible"
            title="修改酒店信息"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="酒店名">
                <span>{{ modifyHotelInfo.name }}</span>
            </a-form-item>
            <a-form-item label="酒店商圈" v-bind="formItemLayout">
                <span>{{ modifyHotelInfo.bizRegion }}</span>
            </a-form-item>
            <a-form-item label="酒店地址" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写酒店地址"
                        v-decorator="['address', { rules: [{ required: true, message: '请填写酒店地址' }], initialValue: this.modifyHotelInfo.address }]"
                />
            </a-form-item>
            <a-form-item label="酒店星级" v-bind="formItemLayout">
                <span>{{ modifyHotelInfo.hotelStar }}</span>
            </a-form-item>
            <a-form-item label="联系电话" v-bind="formItemLayout">
                <span>{{ modifyHotelInfo.phoneNum }}</span>
            </a-form-item>
            <a-form-item label="酒店简介" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :rows="4"
                        placeholder="请填写酒店简介"
                        v-decorator="['description', { rules: [{ required: true, message: '请填写酒店简介' }], initialValue: this.modifyHotelInfo.description }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import { message } from 'ant-design-vue'
    export default {
        name: "ModifyHotelInfo",
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: { span: 12 },
                        sm: { span: 6 },
                    },
                    wrapperCol: {
                        xs: { span: 24 },
                        sm: { span: 16 },
                    },
                },
            }
        },
        computed: {
            ...mapGetters([
                'modifyHotelModalVisible',
                'modifyHotelInfo',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'modifyHotelModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_modifyHotelModalVisible',
            ]),
            ...mapActions([
                'updateHotel',
            ]),
            cancel() {
                this.set_modifyHotelModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            description: this.form.getFieldValue('description'),
                            address: this.form.getFieldValue('address'),
                        }
                        this.updateHotel(data)
                        this.form.resetFields()
                    } else {
                        message.error('请填写正确的信息')
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>