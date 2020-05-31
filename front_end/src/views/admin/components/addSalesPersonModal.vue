<template>
    <a-modal
            :visible="addSalesPersonModalVisible"
            title="添加用户"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-Form :form="form">
            <a-form-item v-bind="formItemLayout" label="用户邮箱">
                <a-input
                        type="email"
                        v-decorator="[
                        'email',
                        {rules: [{ required: true, type: 'email', message: '请输入邮箱' }], validateTrigger: 'blur'}
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="用户名">
                <a-input
                        v-decorator="[
                        'userName',
                        { rules: [{required: true, message: '请输入用户名', }], validateTrigger: 'blur' }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="手机号">
                <a-input
                        v-decorator="[
                        'phoneNumber',
                        { rules: [{required: true, message: ' '}, { validator: this.handlePhoneNumber }], validateTrigger: 'blur' }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="密码">
                <a-input
                        v-decorator="[
                        'password',
                        { rules: [{required: true, message: ' ' }, { validator: this.handlePassword }], validateTrigger: 'blur' }
                    ]"
                />
            </a-form-item>
        </a-Form>
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "addSalesPersonModal",
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
                'addSalesPersonModalVisible',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'addSalesPersonModal' });
        },
        methods: {
            ...mapMutations([
                'set_addSalesPersonModalVisible',
                'set_addSalesPersonParams',
            ]),
            ...mapActions([
                'addSalesPerson'
            ]),
            cancel() {
                this.set_addSalesPersonModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault()
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            email: this.form.getFieldValue('email'),
                            userName: this.form.getFieldValue('userName'),
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                            password: this.form.getFieldValue('password'),
                        }
                        this.set_addSalesPersonParams(data)
                        this.addSalesPerson()
                        this.form.setFieldsValue({
                            'userName': '',
                            'password': '',
                            'email': '',
                            'phoneNumber': '',
                        })
                    }
                });
            },
            handlePassword(rule, value, callback) {
                if (value.length < 6) {
                    callback(new Error('密码长度至少6位'))
                }
                callback()
            },
            handlePhoneNumber(rule, value, callback) {
                const re = /1\d{10}/;
                if (re.test(value)) {
                    callback();
                } else {
                    callback(new Error('请输入有效手机号'));
                }
                callback()
            },
        }
    }
</script>

<style scoped>

</style>