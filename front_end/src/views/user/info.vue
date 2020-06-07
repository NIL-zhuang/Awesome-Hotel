<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane tab="我的信息" key="1">
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

                    <a-form-item label="VIP等级" v-bind="formItemLayout">
                        <span v-for="index of 5" :key="index">
                            <img
                                v-if="index <= userVIP.level"
                                alt="example"
                                src="@/assets/star.svg"
                                style="width: 20px; height: 20px"
                            />

                            <img
                                    v-else
                                    alt="example"
                                    src="@/assets/starGray.svg"
                                    style="width: 20px; height: 20px"
                            />
                        </span>

                    </a-form-item>

                    <a-form-item label="信用值" v-bind="formItemLayout">
                        <span>{{ userInfo.credit }}</span>
                    </a-form-item>

                    <a-form-item label="新密码" v-bind="formItemLayout" v-if="modify">
                        <a-input
                                type="password"
                                placeholder="请输入新密码"
                                v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' },
                                { validator: this.handlePassword }], validateTrigger: 'blur' }]"
                                v-if="modify"
                        />
                    </a-form-item>

                    <a-form-item label="确认密码" v-bind="formItemLayout" v-if="modify">
                        <a-input
                                type="password"
                                placeholder="请再次输入密码"
                                v-decorator="['passwordConfirm',
                                {rules: [{ required: true, message: '请输入确认密码' }, { validator: this.handlePasswordCheck }],
                                validateTrigger: 'blur'}]">
                            v-if="modify"
                        </a-input>
                    </a-form-item>

                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                        <a-button type="primary" @click="saveModify">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                        <a-button type="primary" @click="modifyInfo">
                            修改信息
                        </a-button>
                    </a-form-item>

                </a-form>
            </a-tab-pane>

            <a-tab-pane tab="我的订单" key="2">
                <a-table
                        :columns="columns_of_orders"
                        :dataSource="userOrderList"
                        bordered
                >
                    <a-tag slot="createDate" color="red" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <a-tag slot="hotelName" color="orange" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <span slot="roomType" slot-scope="text">
                        <a-tag color="green" v-if="text === 'BigBed'">大床房</a-tag>
                        <a-tag color="green" v-if="text === 'DoubleBed'">双床房</a-tag>
                        <a-tag color="green" v-if="text === 'Family'">家庭房</a-tag>
                    </span>
                    <a-tag slot="checkInDate" color="red" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <a-tag slot="checkOutDate" color="red" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <span slot="price" slot-scope="text">
                        <a-tag color="pink">￥ {{ text }}</a-tag>
                    </span>
                    <a-tag slot="orderState" color="blue" slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showOrderDetail(record)">查看</a-button>

                        <a-divider type="vertical" v-if="record.orderState === '已预订'"></a-divider>
                        <a-popconfirm
                                title="你确定撤销该笔订单吗？"
                                @confirm="confirmCancelOrder(record.id)"
                                @cancel="cancelCancelOrder"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState === '已预订'"
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState === '异常订单'"></a-divider>
                        <a-button type="default" size="small" v-if="record.orderState === '异常订单'">申诉</a-button>
                        <a-divider type="vertical" v-if="record.orderState === '已完成'"></a-divider>
                        <a-button type="default" size="small" v-if="record.orderState === '已完成'">评价</a-button>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="信用记录" key="3">
                <a-list
                        item-layout="horizontal"
                        :data-source="userOrderList">
                    <a-list-item slot="renderItem" slot-scope="item">
                        <a-list-item-meta
                                :description="item.hotelName"
                        >
                            <a slot="title">{{ item.id }}</a>
                        </a-list-item-meta>
                    </a-list-item>
                </a-list>
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
            title: '订单号',
            dataIndex: 'id',
        },
        {
            title: '酒店名',
            dataIndex: 'hotelName',
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            scopedSlots: {customRender: 'roomType'}
        },
        {
            title: '入住人数',
            dataIndex: 'peopleNum',
        },
        {
            title: '订单价格',
            dataIndex: 'price',
        },
        {
            title: '状态',
            filters: [{text: '已预订', value: '已预订'}, {text: '已撤销', value: '已撤销'}, {text: '已入住', value: '已入住'},
                {text: '已完成', value: '已完成'}, {text: '异常订单', value: '异常订单'}],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState', scopedSlots: {customRender: 'orderState'}

        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },

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
                data: [],
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
                'orderDetailVisible',
            ])
        },
        async mounted() {
            await this.getUserInfo()
            await this.getUserVIP(Number(this.userId))
            await this.getUserOrders()
        },
        methods: {
            ...mapActions([
                'getUserInfo',
                'getUserVIP',
                'getUserOrders',
                'updateUserInfo',
                'cancelOrder',
                'getHotelById',
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
                            password: this.form.getFieldValue('password')
                        }
                        this.updateUserInfo(data).then(() => {
                            this.modify = false
                        })
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
            }
        }

    }
</script>

<style scoped lang="less">
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
