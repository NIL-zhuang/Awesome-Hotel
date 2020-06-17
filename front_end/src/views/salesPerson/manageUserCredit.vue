<!-- created by glh 2020-05-23 -->
<!-- 网站营销人员为用户充值信用的地方 -->
<template>
    <div class="info-wrapper">
        <div class="charge" v-if="searchSuccess">
            <div style="text-align: left">
                <a-button type="primary" @click="goBack" style="margin-left: 20%">
                    <a-icon type="left"/>
                    Backward
                </a-button>
            </div>

            <br/>

            <div class="charge-card">
                <a-form :form="form"
                        style="margin-top: 30px; text-align: left; padding: 5px; border: 1px solid #83c2f8;">

                    <a-form-item label="用户名" :label-col="{ span: 7 }" :wrapper-col="{ span: 8, offset: 2 }">
                        <span>{{ this.userName }}</span>
                    </a-form-item>

                    <a-form-item label="手机号" :label-col="{ span: 7 }" :wrapper-col="{ span: 8, offset: 2 }">
                        <span>{{ this.userPhoneNumber }}</span>
                    </a-form-item>

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
            </div>
        </div>


        <div class="search" v-else>
            <span style="width: 150px">请输入用户邮箱</span>
            <br/><br/>
            <a-input-search placeholder="请输入用户邮箱" enter-button @search="onSearch" id="searchEmail" style="width: 380px"/>
        </div>
    </div>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import {message} from "ant-design-vue";

    export default {
        name: "manageUserCredit",
        data() {
            return {
                form: this.$form.createForm(this, {name: 'chargeMoney'}),
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
                    return this.currentUserInfo.userName
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
                'set_searchSuccess',
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
                if (this.searchSuccess) {
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
            },
            goBack() {
                this.set_searchSuccess(false)
            }
        }
    }
</script>

<style scoped lang="less">
    .info-wrapper {
        padding: 50px;
        text-align: center;
    }

    .charge-card {
        margin-left: 80px;
        width: 300px;
        display: inline-block;
        text-align: center;
    }

    .search {
        text-align: center;
        width: 100%;
    }
    .charge {
        width: 100%;
    }
</style>


