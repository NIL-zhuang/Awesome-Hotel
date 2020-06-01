<template>
    <a-modal
            :visible="modifyInfoModalVisible"
            title="修改信息"
            cancelText="取消"
            okText="修改"
            @cancel="cancelModify"
            @ok="confirmModify"
    >
        <a-form :form="form">
            <a-form-item v-bind="formItemLayout" label="用户名">
                <a-input
                        v-decorator="[
                        'userName',
                        { rules: [{ required: true, message: '请输入用户名' }], validateTrigger: 'blur', initialValue: this.modifyUserInfo.userName }
                    ]"
                />
            </a-form-item>

            <a-form-item v-bind="formItemLayout" label="手机号">
                <a-input
                        v-decorator="[
                        'phoneNumber',
                        { rules: [{ required: true, message: '请输入联系人手机' }, { validator: this.handlePhoneNumber }], validateTrigger: 'blur', initialValue: this.modifyUserInfo.phoneNumber }
                    ]"
                />
            </a-form-item>

            <a-form-item v-bind="formItemLayout" label="用户密码">
                <a-input
                        v-decorator="[
                        'password',
                        { rules: [{ required: true, message: ' ' }, { validator: this.handlePassword }], validateTrigger: 'blur' }
                    ]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    export default {
        name: "modifyInfoModal",
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
            }
        },
        computed: {
            ...mapGetters([
                'modifyInfoModalVisible',
                'modifyUserInfo',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'modifyInfoModal'});
        },
        methods: {
            ...mapMutations([
                'set_modifyInfoModalVisible',
            ]),
            ...mapActions([
                'adminUpdateUserInfo',
            ]),
            cancelModify() {
                this.set_modifyInfoModalVisible(false)
            },
            confirmModify(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            id: this.modifyUserInfo.id,
                            userName: this.form.getFieldValue('userName'),
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                            password: this.form.getFieldValue('password'),
                        }
                        this.adminUpdateUserInfo(data)

                    }
                })
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
            handlePassword(rule, value, callback) {
                if (value.length < 6) {
                    callback(new Error('密码长度至少6位'))
                }
                callback()
            },
        },
    }
</script>

<style scoped>

</style>