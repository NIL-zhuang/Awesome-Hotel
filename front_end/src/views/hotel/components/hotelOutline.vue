<template>
    <div>
        <a-tabs>
            <a-tab-pane key="1" tab="酒店点评">
                <div style="font-size: large;padding: 10px">
                    <a-icon class="eval" type="crown"/>
                    评分
                    <a-tag color="orange">{{this.currentHotelInfo.rate}}</a-tag>
                    <a-button @click="like" class="eval" icon="like"/>
                    点赞
                    <a-tag color="pink">{{this.currentHotelInfo.rate}}</a-tag>
                    <a-button @click="star" class="eval" icon="star"/>
                    收藏
                    <a-tag color="blue">{{this.currentHotelInfo.rate}}</a-tag>
                </div>
            </a-tab-pane>
            <a-tab-pane key="2" tab="常见问题">
                <a-comment>
                    <a-avatar
                            alt="Han Solo"
                            slot="avatar"
                            src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                    />
                    <div slot="content">
                        <a-form :form="form">
                            <a-form-item>
                                <a-textarea :placeholder="hint" :rows="4"
                                            v-decorator="[
                                            'question', { rules: [{ required: true, message: '请输入您的问题' }]}
                                        ]"
                                />
                            </a-form-item>
                            <a-form-item>
                                <a-button :loading="submitting" @click="handleSubmit" html-type="submit" type="primary">
                                    我要提问
                                </a-button>
                            </a-form-item>
                        </a-form>
                    </div>
                </a-comment>
            </a-tab-pane>
            <a-tab-pane key="3" tab="酒店优惠">
                <a-table
                        :columns="columns"
                        :dataSource="couponList"
                        bordered

                >
                    <template slot="title">
                        <h3>只有满减优惠有具体的优惠金额，其他类型的优惠券的优惠方式都是折扣。</h3>
                    </template>
                    <a-tag color="purple" slot="couponName" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <a-tag color="blue" slot="discount" slot-scope="disc">
                        {{disc===0.00?'暂无':100*disc+'%'}}
                    </a-tag>
                    <a-tag color="pink" slot="discountMoney" slot-scope="money">
                        {{money===0?'暂无':money}}
                    </a-tag>
                    <span slot="action">
                <a-button size="small" type="primary">查看详情</a-button>
            </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
    </div>
</template>

<script>
    import {mapActions, mapGetters} from "vuex";

    const columns = [
        {
            title: '优惠券名称',
            dataIndex: 'couponName',
            scopedSlots: {customRender: 'couponName'},
        },
        {
            title: '折扣',
            dataIndex: 'discount',
            scopedSlots: {customRender: 'discount'},
        },
        {
            title: '优惠简介',
            dataIndex: 'description',
        },
        {
            title: '优惠金额（满减）',
            dataIndex: 'discountMoney',
            scopedSlots: {customRender: 'discountMoney'},
        },
        {
            title: '操作',
            scopedSlots: {customRender: 'action'}
        }
    ];

    export default {
        name: "hotelOutline",
        data() {
            return {
                hint: '输入您的问题',
                submitting: false,
                columns,
            }
        },
        computed: {
            ...mapGetters([
                'currentHotelInfo',
                'userId',
                'couponList',
            ])
        },
        mounted() {
            this.getHotelCoupon(this.currentHotelInfo.id)
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'question'});
        },
        methods: {
            ...mapActions([
                'getHotelCoupon',
                'addQuestion',
            ]),
            like() {
                console.log('点赞')
            },
            star() {
                console.log('收藏')
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            userId: this.userId,
                            hotelId: this.currentHotelInfo.id,
                            question: this.form.getFieldValue('question'),
                        }
                        this.addQuestion(data)
                        this.form.resetFields()
                    }
                });
            },
        }
    }
</script>

<style scoped>
    .eval {
        margin-left: 50px;
        background-color: transparent;
        border: transparent;
    }
</style>