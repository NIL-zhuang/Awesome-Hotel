<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane key="1" tab="网站会员">
                <div style="margin-top: 50px;">
                    <div style="float: left; margin-left: 5%">
                        <ClientMembershipCoupon></ClientMembershipCoupon>
                    </div>
                    <div style="float: left; margin-left: 5%; text-align: center; width: 270px">
                        <a-card style="width: 270px;">
                            <p>VIP网站会员特权</p>
                            <img
                                    alt="example"
                                    class="logo"
                                    src="@/assets/membershipLogo.svg"
                            />
                            <span> 尊贵标识</span>
                            <a-divider type="vertical"></a-divider>
                            <img
                                    alt="example"
                                    class="logo"
                                    src="@/assets/couponLogo.svg"
                            />
                            <span> 丰富优惠</span>
                        </a-card>

                        <span style="text-align: center;" v-if="this.userInfo.vipType==='Client'">
                            <div class="membershipInfo">
                                您的等级：
                                <span :key="index" v-for="index of 5">
                                    <img
                                            alt="example"
                                            src="@/assets/star.svg"
                                            style="width: 32px; height: 32px"
                                            v-if="index <= userVIP.level"
                                    />
                                    <img
                                            alt="example"
                                            src="@/assets/starGray.svg"
                                            style="width: 32px; height: 32px"
                                            v-else
                                    />
                                </span>
                            </div>
                            <div style="padding: 10px; margin-top: 15px">
                                <a-row style="display: inline-flex">
                                    <a-statistic :value="userVIP.consumption" title="当前累计消费"></a-statistic>
                                    <a-statistic :value="userVIP.reduction*100" style="margin-left: 10px"
                                                 title="当前消费减免">
                                        <template #suffix>%</template>
                                    </a-statistic>
                                </a-row>
                                <a-row>
                                    <a-statistic :value="userInfo.birthday" title="您的生日"></a-statistic>
                                </a-row>
                            </div>
                        </span>
                        <span v-else-if="this.userVIP.status===0">
                            <div class="membershipInfo">
                                <a-tag color="red">您的VIP已被冻结</a-tag>
                            </div>
                        </span>
                        <span v-else>
                            <a-button @click="registerSiteMembership" style="margin-top: 20px" type="primary"><a-icon
                                    type="user"/>注册会员</a-button>
                        </span>
                    </div>
                </div>
            </a-tab-pane>
            <a-tab-pane key="2" tab="企业会员">
                <div style="margin-top: 50px;">
                    <div style="float: left; margin-left: 5%">
                        <CorpMembershipCoupon></CorpMembershipCoupon>
                    </div>
                    <div style="float: left; margin-left: 5%; text-align: center; width: 270px">
                        <a-card style="width: 270px;">
                            <p>VIP企业会员特权</p>
                            <img
                                    alt="example"
                                    class="logo"
                                    src="@/assets/couponLogo.svg"
                            />
                            <span> 丰富优惠</span>
                        </a-card>
                        <span style="text-align: center;" v-if="JSON.stringify(this.corpVIP)!=='{}'">
                            <div v-if="corpVIP.status === 1">
                                <div class="membershipInfo">
                                    <span>您的企业：{{ corpVIP.corporationName }}</span>
                                </div>
                                <div class="membershipInfo">
                                    您的等级：
                                    <span :key="index" v-for="index of 3">
                                        <img
                                                alt="example"
                                                src="@/assets/star.svg"
                                                style="width: 32px; height: 32px"
                                                v-if="index <= corpVIP.level"
                                        />
                                        <img
                                                alt="example"
                                                src="@/assets/starGray.svg"
                                                style="width: 32px; height: 32px"
                                                v-else
                                        />
                                    </span>
                                </div>
                                <div style="display: inline-flex; padding: 10px; margin-top: 15px">
                                    <a-statistic :value="corpVIP.consumption" title="当前累计消费"></a-statistic>
                                    <a-statistic :value="corpVIP.reduction*100" style="margin-left: 10px"
                                                 title="当前消费减免">
                                        <template #suffix>%</template>
                                    </a-statistic>
                                </div>
                            </div>
                            <div class="membershipInfo" v-else>
                                <a-tag color="red">您的企业({{ corpVIP.corporationName }})VIP被冻结</a-tag>
                            </div>
                        </span>
                        <span v-else>
                            <a-button @click="registerCorporationMembership" style="margin-top: 20px" type="primary"><a-icon
                                    type="user"/>注册企业会员</a-button>
                        </span>
                    </div>

                </div>
            </a-tab-pane>
            <a-tab-pane key="3" tab="网站优惠">
                <site-coupon-table user-type="Client"></site-coupon-table>
            </a-tab-pane>
            <a-tab-pane key="4" tab="联系网站">
                <a-list :data-source="salesPersonTel" item-layout="horizontal">
                    <a-list-item class="contact" slot="renderItem" slot-scope="item">
                        <a-list-item-meta
                                description="拨打电话联系网站营销人员"
                        >
                            <a slot="title">{{ item}}</a>
                        </a-list-item-meta>
                    </a-list-item>
                </a-list>
            </a-tab-pane>
        </a-tabs>
        <RegisterSiteMembership></RegisterSiteMembership>
        <RegisterCorporationMembership></RegisterCorporationMembership>
    </div>
</template>

<script>
    import ClientMembershipCoupon from "./components/ClientMembershipCoupon";
    import CorpMembershipCoupon from "./components/CorpMembershipCoupon";
    import RegisterSiteMembership from "./components/RegisterSiteMembership";
    import RegisterCorporationMembership from "./components/RegisterCorporationMembership";
    import SiteCouponTable from '../coupon/siteCouponTable';
    import moment from 'moment';
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import {message} from "ant-design-vue";

    export default {
        name: "usermembership",
        data() {
            this.dateFormat = 'YYYY-MM-DD';
            return {
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
            ClientMembershipCoupon,
            CorpMembershipCoupon,
            RegisterSiteMembership,
            RegisterCorporationMembership,
            SiteCouponTable,
        },
        async mounted() {
            await this.getUserInfo()
            await this.getUserVIP(Number(this.userInfo.id))
            if (this.userInfo.corporation) {
                await this.corpVIPCheck(this.userInfo.corporation)
                this.getCorpVIP(this.userInfo.corporation)
            }
            this.getAllSalesmanTel()
        },
        computed: {
            ...mapGetters([
                'userId',
                'userInfo',
                'userVIP',
                'corpVIP',
                'isCorpVIP',
                'salesPersonTel',
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
                'getCorpVIP',
                'corpVIPCheck',
                'getAllSalesmanTel',
            ]),
            registerSiteMembership() {
                this.set_registerSiteMembershipModalVisible(true);
            },
            registerCorporationMembership() {
                this.set_registerCorporationMembershipModalVisible(true);
            },

            // modifyInfo() {
            //     this.modify = true
            // },
            // saveModify() {
            //     this.form.validateFields((err, values) => {
            //         if (!err) {
            //             const data = this.form.getFieldValue('birthday').format("YYYY-MM-DD")
            //             this.updateUserBirthday(data).then(() => {
            //                 this.modify = false
            //             })
            //         } else {
            //             message.error("请输入正确的信息")
            //         }
            //     })
            // },
            //
            // cancelModify() {
            //     message.info('取消修改')
            //     this.modify = false
            // },
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

<style lang="less" scoped>
    .info-wrapper {
        padding: 50px;

        .ant-tabs-bar {
            padding-left: 30px
        }

        .contact {
            background-color: white;
            text-align: center;
        }
    }
</style>
