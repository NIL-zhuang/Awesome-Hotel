<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane key="1" tab="我的信息">
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

                    <a-form-item label="VIP等级" v-bind="formItemLayout" v-if="this.userInfo.vipType==='Client'">
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
                    </a-form-item>
                    <a-form-item label="VIP" v-bind="formItemLayout" v-else-if="JSON.stringify(this.userVIP) ==='{}'">
                        <span>
                            <router-link :to="{ name: 'userMembership' }" @click="goToMembership">
                                尚未成为会员，点击注册
                            </router-link>
                        </span>
                    </a-form-item>
                    <a-form-item label="VIP等级" v-bind="formItemLayout" v-else>
                        <a-tag color="red">您已被冻结</a-tag>
                    </a-form-item>

                    <a-form-item label="所属企业" v-bind="formItemLayout">
                        <a-input
                                placeholder="请填写所属企业"
                                v-decorator="['corporation']"
                                v-if="modify"
                        />
                        <span v-else>{{ userInfo.corporate }}</span>
                    </a-form-item>

                    <a-form-item label="信用值" v-bind="formItemLayout">
                        <span>{{ userInfo.credit }}</span>
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
            </a-tab-pane>
            <a-tab-pane key="2" tab="我的订单">
                <a-table
                        :columns="columns_of_orders"
                        :dataSource="userOrderList"
                        bordered
                >
                    <a-tag color="red" slot="createDate" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <a-tag color="orange" slot="hotelName" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <span slot="roomType" slot-scope="text">
                        <a-tag color="green" v-if="text === 'BigBed'">大床房</a-tag>
                        <a-tag color="green" v-if="text === 'DoubleBed'">双床房</a-tag>
                        <a-tag color="green" v-if="text === 'Family'">家庭房</a-tag>
                    </span>
                    <a-tag color="red" slot="checkInDate" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <a-tag color="red" slot="checkOutDate" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <span slot="price" slot-scope="text">
                        <a-tag color="pink">￥ {{ text }}</a-tag>
                    </span>
                    <a-tag color="blue" slot="orderState" slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button @click="showOrderDetail(record)" size="small" type="primary">查看</a-button>

                        <a-divider type="vertical" v-if="record.orderState === '已预订'"></a-divider>
                        <a-popconfirm
                                @cancel="cancelCancelOrder"
                                @confirm="confirmCancelOrder(record.id)"
                                cancelText="取消"
                                okText="确定"
                                title="你确定撤销该笔订单吗？"
                                v-if="record.orderState === '已预订'"
                        >
                            <a-button size="small" type="danger">撤销</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState === '异常订单'"></a-divider>
                        <a-button size="small" type="default" v-if="record.orderState === '异常订单'">申诉</a-button>
                        <a-divider type="vertical" v-if="record.orderState === '已完成'"></a-divider>
                        <a-button size="small" type="default" v-if="record.orderState === '已完成'">评价</a-button>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane key="3" tab="信用记录">
                <a-table
                        :columns="columns_of_credit"
                        :dataSource="creditChangeList"
                        :locale="{emptyText: '暂时没有信用变更记录'}"
                        bordered
                >
                    <span slot="type" slot-scope="record">
                        <a-icon type="plus-circle" theme="twoTone" v-if="record.change>0"/>
                        <a-icon type="minus-circle" theme="twoTone" two-tone-color="red" v-else/>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>

        <orderDetail></orderDetail>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import orderDetail from '../order/orderDetail'
    import {message} from 'ant-design-vue';

    const columns_of_orders = [
        {
            title: '订单号',
            dataIndex: 'id',
            sorter: (a, b) => a.id - b.id,
        },
        {
            title: '下单时间',
            dataIndex: 'createDate',
            scopedSlots: {customRender: 'createDate'},
        },
        {
            title: '酒店名',
            dataIndex: 'hotelName',
            scopedSlots: {customRender: 'hotelName'}
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            scopedSlots: {customRender: 'roomType'}
        },
        {
            title: '入住时间',
            dataIndex: 'checkInDate',
            scopedSlots: {customRender: 'checkInDate'}
        },
        {
            title: '离店时间',
            dataIndex: 'checkOutDate',
            scopedSlots: {customRender: 'checkOutDate'}
        },
        {
            title: '入住人数',
            dataIndex: 'peopleNum',
        },
        {
            title: '订单价格',
            dataIndex: 'price',
            scopedSlots: {customRender: 'price'}
        },
        {
            title: '状态',
            filters: [{text: '已预订', value: '已预订'}, {text: '已撤销', value: '已撤销'}, {text: '已入住', value: '已入住'},
                {text: '已完成', value: '已完成'}, {text: '异常订单', value: '异常订单'}],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState', scopedSlots: {customRender: 'orderState'},
            filterMultiple: false,
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },

    ];
    const columns_of_credit = [
        {
            title: '变更类型',
            scopedSlots: {customRender: 'type'},
        },
        {
            title: '变更日期',
            dataIndex: 'changeDate',
        },
        {
            title: '变更数额',
            dataIndex: 'change',
        },
        {
            title: '变更后信用',
            dataIndex: 'now',
        },
        {
            title: '变更原因',
            dataIndex: 'reason',
        }
    ];
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
                pagination: {},
                columns_of_orders,
                columns_of_credit,
                form: this.$form.createForm(this, {name: 'coordinated'}),
            }
        },
        components: {
            orderDetail
        },
        computed: {
            ...mapGetters([
                'userId',
                'userInfo',
                'userVIP',
                'userOrderList',
                'creditChangeList',
                'orderDetailVisible',
            ])
        },
        async mounted() {
            await this.getUserInfo()
            if (this.userInfo.vipType !== 'Normal')
                this.getUserVIP(Number(this.userInfo.id))
            this.getUserOrders()
            this.getUserCredits(this.userId)
        },
        methods: {
            ...mapActions([
                'getUserInfo',
                'getUserVIP',
                'getUserOrders',
                'updateUserInfo',
                'cancelOrder',
                'getHotelById',
                'getUserCredits',
            ]),
            ...mapMutations([
                'set_orderDetailVisible',
                'set_orderInfo',
                'set_currentHotelId',
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
                        'corporation': this.userInfo.corporate,
                    })
                }, 0)
                this.modify = true
            },

            cancelModify() {
                this.modify = false
            },

            confirmCancelOrder(orderId) {
                this.cancelOrder(orderId)
            },

            cancelCancelOrder() {

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
            showOrderDetail(record) {
                this.set_orderInfo(record)
                this.set_currentHotelId(record.hotelId)
                this.getHotelById(record.hotelId)
                this.set_orderDetailVisible(true)
            },
            goToMembership() {
                // TODO 修改header上面的current
            }
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
