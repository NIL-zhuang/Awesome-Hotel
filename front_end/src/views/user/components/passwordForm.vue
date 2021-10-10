<template>
    <div class="info-wrapper">
        <a-form :form="form" style="padding: 10px; border-radius: 20px; background-color: whitesmoke">
            <a-form-item label="新密码" v-bind="formItemLayout">
                <a-input
                    placeholder="请输入新密码"
                    type="password"
                    v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' },
                            { validator: this.handlePassword }], validateTrigger: 'blur' }]"
                />
            </a-form-item>

            <a-form-item label="确认密码" v-bind="formItemLayout">
                <a-input
                    placeholder="请再次输入密码"
                    type="password"
                    v-decorator="['passwordConfirm',
                            {rules: [{ required: true, message: '请输入确认密码' }, { validator: this.handlePasswordCheck }],
                            validateTrigger: 'blur', initialValue: ''}]">
                </a-input>
            </a-form-item>

            <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
                <a-button @click="saveModify" icon="upload" style="border-radius: 20px" type="primary">
                    保存
                </a-button>
            </a-form-item>

        </a-form>
    </div>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import {message} from "ant-design-vue";
    export default {
        name: "passwordForm",
        data() {
            return {
                formLayout: 'horizontal',
                formItemLayout: {
                    labelCol: {
                        span: 3
                    },
                    wrapperCol: {
                        span: 8,
                        offset: 1,
                    },
                },
            }
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'passwordForm'})
        },
        methods: {
            ...mapActions([
                'updateUserPassword',
            ]),
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const password = this.form.getFieldValue('password')
                        this.updateUserPassword(password)
                        this.form.resetFields()
                    } else {
                        message.error("请输入正确的信息")
                    }
                })
            },
            handlePassword(rule, value, callback) {
                if (value) {
                    if (value.length < 6) {
                        callback(new Error('密码长度至少6位'))
                    }
                }
                callback()
            },

            handlePasswordCheck(rule, value, callback) {
                const password = this.form.getFieldValue('password')
                if (value === undefined) {
                    callback(new Error('请输入密码'))
                }
                if (value && password && value.trim() !== password.trim()) {
                    callback(new Error('两次密码不一致'))
                }
                callback()
            },
        }
    }
</script>

<style scoped>
    .info-wrapper {
        padding: 10px;
    }
</style>
