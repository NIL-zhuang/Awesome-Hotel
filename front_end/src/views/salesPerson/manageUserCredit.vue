<!-- created by glh 2020-05-23 -->
<!-- 网站营销人员为用户充值信用的地方 -->
<template>
    <div class="info-wrapper">
        <div class="info-card">
            <a-card style="width: 220px">
                <img
                    slot="cover"
                    alt="example"
                    src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png"
                />
                <a-card-meta :title=this.userName>
                    <template slot="description">
                        phone: {{ this.userPhoneNumber }}
                    </template>
                </a-card-meta>
            </a-card>
        </div>
        <div class="charge-card">
            <a-form :form="form" style="margin-top: 30px; text-align: left; padding: 5px; border: 1px solid #83c2f8;">

                <a-form-item label="当前信用值" :label-col="{ span: 7 }" :wrapper-col="{ span: 8, offset: 2 }">
                    <span>{{ this.currentUserInfo.credit }}</span>
                </a-form-item>

                <a-form-item label="充值金额" :label-col="{ span: 7 }" :wrapper-col="{ span: 12, offset: 2 }">
                    <a-input
                            placeholder="请输入充值金额"
                            v-decorator="['chargeMoney', { rules: [{ required: true, message: ' ' },
                                { validator: this.handleChargeMoney }], validateTrigger: 'blur' }]"
                    />
                </a-form-item>

                <a-form-item :wrapper-col="{ span: 8, offset: 9 }">
                    <a-button type="primary" @click="charge">
                        确认充值
                    </a-button>
                </a-form-item>

            </a-form>
            <br/><br/><br/>
            <span style="width: 150px">请输入用户邮箱</span>
            <br/>
            <a-input-search placeholder="请输入用户邮箱" enter-button @search="onSearch" id="searchEmail"/>
        </div>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import {message} from "ant-design-vue";
    export default {
        name: "manageUserCredit",
        data() {
            return {
                form: this.$form.createForm(this, { name: 'chargeMoney' }),
            }
        },
        computed: {
            ...mapGetters([
                'currentUserInfo',
                'currentUserId',
                'searchSuccess',
            ]),
            userName() {
                if (this.searchSuccess) {
                    return "用户："  + this.currentUserInfo.userName
                } else {
                    return '请输入用户ID'
                }
            },
            userPhoneNumber() {
                if (this.searchSuccess) {
                    return this.currentUserInfo.phoneNumber
                } else {
                    return ''
                }
            },
        },
        methods: {
            ...mapMutations([
                'set_currentUserEmail',
                'set_currentUserInfo',
            ]),
            ...mapActions([
                'getCurrentUserInfoByEmail',
                'chargeCredit'
            ]),
            onSearch(value) {
                this.set_currentUserEmail(value)
                this.getCurrentUserInfoByEmail()
            },
            handleChargeMoney(rule, value, callback) {
                const re = /(^[1-9]\d*$)/
                if (re.test(value)) {
                    callback()
                } else {
                    callback(new Error('请输入有效充值金额'))
                }
            },
            charge() {
                if(this.searchSuccess) {
                    this.form.validateFields((err, values) => {
                        if (!err) {
                            const data = {
                                chargeMoney: this.form.getFieldValue('chargeMoney'),
                            }
                            this.chargeCredit(Number(data.chargeMoney))

                        } else {
                            message.error("请输入有效金额")
                        }
                    })
                } else {
                    message.error('请先进行搜索')
                }
                this.form.setFieldsValue({
                    chargeMoney: '',
                })
            }
        }
    }
</script>

<style scoped lang="less">
    .info-wrapper {
        padding: 50px;
        text-align: center;
    }
    .info-card {
        display: inline-block;
    }
    .charge-card {
        margin-left: 80px;
        width: 300px;
        display: inline-block;
        text-align: center;

    }
</style>


