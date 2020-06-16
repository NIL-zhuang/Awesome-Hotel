<template>
    <div style="margin-top: 50px;">
        <div style="float: left; margin-left: 5%">
            <membership-coupon ></membership-coupon>
        </div>
        <div style="float: left; margin-left: 5%; text-align: center; width: 270px">
            <a-card style="width: 270px;">
                <p>VIP网站会员特权</p>
                <img
                    alt="example"
                    src="@/assets/membershipLogo.svg"
                    class="logo"
                />
                <span> 尊贵标识</span>
                <a-divider type="vertical"></a-divider>
                <img
                    alt="example"
                    src="@/assets/couponLogo.svg"
                    class="logo"
                />
                <span> 丰富优惠</span>
            </a-card>

            <span v-if="this.userInfo.vipType==='Client'" style="text-align: center;">
                <div class="membershipInfo">
                    您的等级：
                    <span v-for="index of 5" :key="index">
                        <img
                                v-if="index <= userVIP.level"
                                alt="example"
                                src="@/assets/star.svg"
                                style="width: 32px; height: 32px"
                        />
                        <img
                                v-else
                                alt="example"
                                src="@/assets/starGray.svg"
                                style="width: 32px; height: 32px"
                        />
                    </span>
                </div>
            </span>
            <span v-else-if="JSON.stringify(this.userVIP)!=='{}'">
                <div class="membershipInfo">
                    <a-tag color="red">您已被冻结</a-tag>
                </div>
            </span>
            <span v-else>
                <a-button type="primary" @click="registerSiteMembership" style="margin-top: 20px"><a-icon type="user" />注册普通会员</a-button>
            </span>

            <a-form :form="form" style="margin-top: 30px; margin-left: 17px; text-align: left" v-show="this.userInfo.vipType==='Client'">
                <a-form-item label="您的生日" v-bind="formItemLayout">
                    <a-date-picker :disabled="!modify"
                                   v-decorator="['birthday', { rules: [{ required: true, message: '请选择您的生日' }], initialValue: moment(this.userInfo.birthday, dateFormat)}]" />
                </a-form-item>

                <a-form-item label="" :wrapper-col="{ span: 16, offset: 9 }" v-if="modify">
                    <a-button type="primary" @click="saveModify">
                        保存
                    </a-button>
                    <a-button type="default" style="margin-left: 10px" @click="cancelModify">
                        取消
                    </a-button>
                </a-form-item>
                <a-form-item :wrapper-col="{ span: 16, offset: 9 }" v-else>
                    <a-button type="primary" @click="modifyInfo">
                        修改
                    </a-button>
                </a-form-item>

            </a-form>
        </div>
        <RegisterSiteMembership></RegisterSiteMembership>
    </div>
</template>

<script>
import membershipCoupon from "./components/membershipCoupon";
import RegisterSiteMembership from "./components/RegisterSiteMembership";
import moment from 'moment';
import { mapGetters, mapMutations, mapActions } from 'vuex'
import {message} from "ant-design-vue";
export default {
    name: "usermembership",
    data() {
        this.dateFormat = 'YYYY-MM-DD';
        return {
            modify: false,
            formLayout: 'horizontal',
            formItemLayout: {
                labelCol: {
                    span: 8,
                    offset: 0
                },
                wrapperCol: {
                    span: 14,
                    offset: 1,
                },

            },
            form: this.$form.createForm(this, {name: 'birthday'}),
        }
    },
    components: {
        membershipCoupon,
        RegisterSiteMembership,
    },
    async mounted() {
        await this.getUserInfo()
        if (this.userInfo.vipType !== 'Normal')
            await this.getUserVIP(Number(this.userInfo.id))
    },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo',
            'userVIP',
        ])
    },
    methods: {
        ...mapMutations([
            'set_registerSiteMembershipModalVisible',
            'set_registerCorporationMembershipModalVisible',
        ]),
        ...mapActions([
            'getUserVIP',
            'getUserInfo',
            'updateUserBirthday',
        ]),
        registerSiteMembership() {
            this.set_registerSiteMembershipModalVisible(true);
        },

        modifyInfo() {
            this.modify = true
        },

        saveModify() {
            this.form.validateFields((err, values) => {
                if (!err) {
                    const data = this.form.getFieldValue('birthday').format("YYYY-MM-DD")
                    this.updateUserBirthday(data).then(() => {
                        this.modify = false
                    })
                } else {
                    message.error("请输入正确的信息")
                }
            })
        },

        cancelModify() {
            this.modify = false
        },

        moment,
    }

}
</script>

<style scoped>
    .membershipInfo {
        margin-top: 25px;
        height: 32px;
        line-height: 32px;
        font-size: 14px;
        text-align: center;
    }
    .logo {
        width: 20px;
        height: 20px;
    }
</style>
