<template>
    <div class="info-wrapper">
        <a-form :form="form" style="margin-top: 30px">

            <a-form-item label="头像" v-bind="formItemLayout">
                <a-avatar src="./defaultAvatar.png"></a-avatar>
                <!--                        <a-button type="primary" icon="upload" style="margin-left: 20px">上传头像</a-button>-->
            </a-form-item>

            <a-form-item label="用户名" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写用户名"
                        v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                        v-if="modify"
                />
                <span v-else>{{ userInfo.userName }}</span>
            </a-form-item>

            <a-form-item label="邮箱" v-bind="formItemLayout">
                <span>{{ userInfo.email }}</span>
            </a-form-item>

            <a-form-item label="手机号" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写手机号"
                        v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' },
                            { validator: this.handlePhoneNumber }], validateTrigger: 'blur' }]"
                        v-if="modify"
                />
                <span v-else>{{ userInfo.phoneNumber}}</span>
            </a-form-item>

            <a-form-item label="新密码" v-bind="formItemLayout" v-if="modify">
                <a-input
                        placeholder="请输入新密码"
                        type="password"
                        v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' },
                                { validator: this.handlePassword }], validateTrigger: 'blur' }]"
                        v-if="modify"
                />
            </a-form-item>

            <a-form-item label="确认密码" v-bind="formItemLayout" v-if="modify">
                <a-input
                        placeholder="请再次输入密码"
                        type="password"
                        v-decorator="['passwordConfirm',
                                {rules: [{ required: true, message: '请输入确认密码' }, { validator: this.handlePasswordCheck }],
                                validateTrigger: 'blur'}]">
                    v-if="modify"
                </a-input>
            </a-form-item>

            <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                <a-button @click="saveModify" type="primary">
                    保存
                </a-button>
                <a-button @click="cancelModify" style="margin-left: 30px" type="default">
                    取消
                </a-button>
            </a-form-item>
            <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                <a-button @click="modifyInfo" type="primary">
                    修改信息
                </a-button>
            </a-form-item>

        </a-form>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import {message} from 'ant-design-vue';
    export default {
        name: 'info',
        data() {
            return {
                modify: false,
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
                form: this.$form.createForm(this, {name: 'coordinated'}),
            }
        },
        computed: {
            ...mapGetters([
                'userId',
                'userInfo',
            ])
        },
        async mounted() {
            await this.getUserInfo()
        },
        methods: {
            ...mapActions([
                'getUserInfo',
                'updateUserInfo',
            ]),
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            userName: this.form.getFieldValue('userName'),
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                            password: this.form.getFieldValue('password'),
                            corporation: this.form.getFieldValue('corporation')
                        }
                        this.updateUserInfo(data)
                        this.modify = false
                    } else {
                        message.error("请输入正确的信息")
                    }
                })
            },

            modifyInfo() {
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'userName': this.userInfo.userName,
                        'phoneNumber': this.userInfo.phoneNumber,
                    })
                }, 0)
                this.modify = true
            },

            cancelModify() {
                this.modify = false
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

            handlePassword(rule, value, callback) {
                if (value.length < 6) {
                    callback(new Error('密码长度至少6位'))
                }
                callback()
            },

            handlePasswordCheck(rule, value, callback) {
                const password = this.form.getFieldValue('registerPassword')
                console.log(password)
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

<style lang="less" scoped>
    .info-wrapper {
        padding: 50px;

        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
