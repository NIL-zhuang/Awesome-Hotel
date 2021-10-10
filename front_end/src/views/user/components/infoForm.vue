<template>
    <div class="info-wrapper">
        <a-form :form="form" style="padding: 10px; border-radius: 20px; background-color: whitesmoke">
            <a-form-item label="头像" v-bind="formItemLayout">
                <a-avatar icon="user" style="background-color: mediumpurple"/>
                <!--                        <a-button type="primary" icon="upload" style="margin-left: 20px">上传头像</a-button>-->
            </a-form-item>

            <a-form-item label="用户名" v-bind="formItemLayout">
                <a-input
                        v-decorator="['userName', { rules: [{ required: false, message: '请输入用户名' }], initialValue: userInfo.userName}]"
                        v-if="modify"
                />
                <span v-else>{{ userInfo.userName }}</span>
            </a-form-item>

            <a-form-item label="邮箱" v-bind="formItemLayout">
                <span>{{ userInfo.email }}</span>
            </a-form-item>

            <a-form-item label="手机号" v-bind="formItemLayout">
                <a-input
                        v-decorator="['phoneNumber', { rules: [{ required: false, message: '请输入手机号' },
                        { validator: this.handlePhoneNumber }], validateTrigger: 'blur' , initialValue: userInfo.phoneNumber}]"
                        v-if="modify"
                />
                <span v-else>{{ userInfo.phoneNumber}}</span>
            </a-form-item>

            <a-form-item label="VIP等级" v-bind="formItemLayout"
                         v-if="this.userInfo.userType==='Client' && this.userInfo.vipType ==='Client'">
                <div v-if="this.userVIP.status === 1">
                    <span>
                    <span :key="index" v-for="index of 5">
                        <img
                                alt="example"
                                src="@/assets/star.svg"
                                style="width: 20px; height: 20px"
                                v-if="index <= userVIP.level"
                        />
                        <img
                                alt="example"
                                src="@/assets/starGray.svg"
                                style="width: 20px; height: 20px"
                                v-else
                        />
                    </span>
                </span>
                </div>

            </a-form-item>
            <a-form-item label="VIP" v-bind="formItemLayout"
                         v-else-if="this.userInfo.userType==='Client' && JSON.stringify(this.userVIP) === '{}'">
                <span>
                    尚未成为会员，请前往会员中心注册
                </span>
            </a-form-item>
            <a-form-item label="VIP" v-bind="formItemLayout"
                         v-else-if="this.userInfo.userType==='Client'&&this.userVIP.status===0">
                <a-tag color="red">您已被冻结</a-tag>
            </a-form-item>

            <a-form-item label="所属企业" v-bind="formItemLayout" v-if="this.userInfo.userType==='Client'">
                <a-input
                        v-decorator="['corporation', {initialValue: this.userInfo.corporation}]"
                        v-if="modify"
                />
                <span v-else>
                    <span v-if="userInfo.corporation">
                        {{ userInfo.corporation }}&nbsp;
                        <span v-if="JSON.stringify(this.corpVIP)==='{}'">
                            <a-tag color="cyan">尚未成为企业会员</a-tag>
                        </span>
                        <span v-else>
                            <span v-if="corpVIP.status === 1"><a-tag color="blue">已成为企业会员</a-tag></span>
                            <span v-else><a-tag color="red">企业VIP被冻结</a-tag></span>
                        </span>
                    </span>
                    <span v-else>
                        尚未登记所属企业
                    </span>
                </span>
            </a-form-item>

            <a-form-item label="信用值" v-bind="formItemLayout" v-if="this.userInfo.userType==='Client'">
                <span>{{ userInfo.credit }}</span>
            </a-form-item>

            <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                <a-button @click="saveModify" icon="upload" style="border-radius: 20px" type="primary">
                    保存
                </a-button>
                <a-button @click="cancelModify" icon="close-circle"
                          style="margin-left: 30px; border-radius: 20px" type="default">
                    取消
                </a-button>
            </a-form-item>
            <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                <a-button @click="modifyInfo" icon="form" style="border-radius: 20px" type="primary">
                    修改信息
                </a-button>
            </a-form-item>

        </a-form>
    </div>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import {message} from "ant-design-vue";

    export default {
        name: "infoForm",
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
            }
        },
        computed: {
            ...mapGetters([
                'userInfo',
                'userVIP',
                'corpVIP',
                'isCorpVIP'
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'userInfo'})
        },
        async mounted() {
            await this.getUserInfo()
            if (this.userInfo.userType === 'Client') {
                await this.getUserVIP(this.userInfo.id)
                if(this.userInfo.corporation) {
                    this.corpVIPCheck(this.userInfo.corporation)
                    this.getCorpVIP(this.userInfo.corporation)
                }
            }
        },
        methods: {
            ...mapActions([
                'updateUserInfo',
                'corpVIPCheck',
                'getCorpVIP',
                'getUserInfo',
                'getUserVIP',
                'corpVIPCheck'
            ]),
            ...mapMutations([]),

            saveModify() {
                this.form.validateFields(async (err, values) => {
                    if (!err) {
                        const data = {
                            userName: this.form.getFieldValue('userName'),
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                            corporation: this.form.getFieldValue('corporation'),
                        }
                        await this.updateUserInfo(data)
                        this.modify = false
                    } else {
                        message.error("请输入正确的信息")
                    }
                })
            },

            modifyInfo() {
                this.modify = true
            },

            cancelModify() {
                message.info('取消修改')
                this.modify = false
            },

            handlePhoneNumber(rule, value, callback) {
                if (value) {
                    const re = /1\d{10}/;
                    if (re.test(value)) {
                        callback();
                    } else {
                        callback(new Error('请输入有效手机号'));
                    }
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
