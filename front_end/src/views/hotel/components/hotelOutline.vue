<template>
    <div>
        <a-tabs>
            <a-tab-pane key="1" tab="酒店点评">
                <div style="font-size: large;padding: 10px;">
                    <div style="display: inline-flex">
                        <a-statistic :value="this.currentHotelInfo.rate.toFixed(1)" class="statistic" title="酒店评分"></a-statistic>
                        <a-statistic :value="this.currentHotelInfo.sanitation.toFixed(1)" class="statistic"
                                     title="卫生评分"></a-statistic>
                        <a-statistic :value="this.currentHotelInfo.environment.toFixed(1)" class="statistic"
                                     title="环境评分"></a-statistic>
                        <a-statistic :value="this.currentHotelInfo.service.toFixed(1)" class="statistic"
                                     title="服务评分"></a-statistic>
                        <a-statistic :value="this.currentHotelInfo.equipment.toFixed(1)" class="statistic"
                                     title="设施评分"></a-statistic>
                        <a-statistic :value="this.currentHotelInfo.commentTime" class="statistic"
                                     title="当前被评价次数"></a-statistic>
                        <a-statistic :value="this.currCollections" class="statistic" title="当前酒店收藏数"></a-statistic>
                    </div>
                    <a-button @click="unstar" icon="close-circle" style="float: right" v-if="currHotelCollectedByUser">
                        取消收藏
                    </a-button>
                    <a-button @click="star" icon="star" style="float: right" type="primary" v-else>
                        收藏该酒店
                    </a-button>
                    <br/><br/>
                    <HotelComment></HotelComment>
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
                        <a-form :form="this.form">
                            <a-form-item>
                                <a-textarea
                                        :placeholder="hint"
                                        :rows="4" style="border-radius: 20px; "
                                        v-decorator="[
                                            'question', { rules: [{ required: true, message: '请输入您的问题' }]}
                                        ]"
                                />
                            </a-form-item>
                            <a-form-item>
                                <a-button :loading="submitting" @click="handleSubmit" html-type="submit"
                                          icon="question-circle"
                                          type="primary">
                                    我要提问
                                </a-button>
                            </a-form-item>
                        </a-form>
                    </div>
                </a-comment>
                <a-list
                        :data-source="hotelQuestion"
                        :header="`当前共${hotelQuestion.length}个提问`"
                        :locale="{emptyText: '暂时没有问题'}"
                        class="comment-list"
                        item-layout="horizontal"
                        style="background-color: white; font-size: larger; padding: 10px; border-radius: 20px; "
                >
                    <a-list-item slot="renderItem" slot-scope="item" style="display: inline">
                        <a-tag color="blue" v-if="item.userId===userId">您的提问</a-tag>
                        <a-comment :author="item.userName">
                            <p slot="content" style="font-size: large;">
                                {{ item.question }}
                            </p>
                            <a-button :icon="answersVisible.get(item.id)?'up-circle':'down-circle'"
                                      @click="showOrHideAnswers(item.id)" size="small"
                                      type="primary">
                                {{answersVisible.get(item.id)?'收起回答':'展开回答'}}
                            </a-button>
                            <a-divider v-if="userId!==item.userId" type="vertical"/>
                            <a-button @click="answerAquestion(item.id)" icon="edit" size="small"
                                      v-if="userId!==item.userId">
                                回答问题
                            </a-button>
                            <a-divider type="vertical" v-if="item.userId===userId"/>
                            <a-popconfirm
                                    @confirm="deleteMyQuestion(item.id)"
                                    cancelText="取消"
                                    okText="确定"
                                    title="你确定要删除问题吗？"
                            >
                                <a-button icon="minus-circle" size="small" type="danger" v-if="item.userId===userId">
                                    删除问题
                                </a-button>
                            </a-popconfirm>
                            <a-list
                                    :data-source="item.answers"
                                    :header="`${item.answers.length}个回答`"
                                    :locale="{emptyText: '该问题暂时没有回答'}"
                                    class="comment-list"
                                    item-layout="horizontal"
                                    v-if="answersVisible.get(item.id)"
                            >
                                <a-list-item slot="renderItem" slot-scope="item" style="display: inline">
                                    <a-tag color="orange" v-if="userId===item.userId">您的回答</a-tag>
                                    <a-comment :author="item.userName">
                                        <p slot="content">
                                            {{ item.answer }}
                                        </p>
                                    </a-comment>
                                    <a-popconfirm
                                            @confirm="deleteMyAnswer(item.answerId)"
                                            cancelText="取消"
                                            okText="确定"
                                            title="你确定要删除回答吗？"
                                    >
                                        <a-button icon="minus-circle" size="small" type="danger"
                                                  v-if="item.userId===userId">删除回答
                                        </a-button>
                                    </a-popconfirm>
                                </a-list-item>
                            </a-list>
                        </a-comment>
                    </a-list-item>
                </a-list>
                <a-drawer
                        :visible="answerFormVisible"
                        @close="answerFormClose"
                        title="回答问题"
                >
                    <a-form :form="this.answerForm">
                        <a-form-item>
                            <a-textarea
                                    :rows="4"
                                    v-decorator="['answer', { rules: [{ required: true, message: '请输入您的回答' }]}]"
                            />
                        </a-form-item>
                        <a-form-item>
                            <a-button @click="submitAnswer" icon="check-circle" type="primary">提交回答</a-button>
                        </a-form-item>
                    </a-form>
                </a-drawer>
            </a-tab-pane>
            <a-tab-pane key="3" tab="酒店优惠">
                <HotelCouponTable :hotel-id="this.currentHotelInfo.id" user-type="Client"></HotelCouponTable>
            </a-tab-pane>
        </a-tabs>
    </div>
</template>

<script>
    import HotelComment from "./HotelComment";
    import HotelCouponTable from '../../coupon/hotelCouponTable';
    import {mapActions, mapGetters, mapMutations} from "vuex";

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
                answerFormVisible: false,
                activeQuestionId: 0,
            }
        },
        components: {
            HotelComment,
            HotelCouponTable,
        },
        computed: {
            ...mapGetters([
                'currentHotelId',
                'currentHotelInfo',
                'userId',
                'userInfo',
                'couponList',
                'hotelQuestion',
                'answersVisible',
                'currCollections',
                'currHotelCollectedByUser',
            ])
        },
        async mounted() {
            this.getHotelQuestion()
            this.getCurrCollections()
            this.getUserCollectHotel(this.userId)
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'question'});
            this.answerForm = this.$form.createForm(this, {name: 'answer'});
        },
        methods: {
            ...mapActions([
                'getHotelCoupon',
                'addQuestion',
                'addAnswer',
                'getHotelQuestion',
                'getCurrCollections',
                'getUserCollectHotel',
                'addCollection',
                'annulCollection',
                'deleteQuestion',
                'deleteAnswer',
            ]),
            ...mapMutations([
                'set_answersVisible',
                'set_currCollections',
                'set_couponDetailVisible',
                'set_currentCouponInfo',
            ]),
            star() {
                const params = {
                    userID: this.userId,
                    hotelID: this.currentHotelId,
                }
                this.addCollection(params)
            },
            unstar() {
                const params = {
                    hotelId: this.currentHotelId,
                    userId: this.userId,
                }
                this.annulCollection(params)
            },
            showOrHideAnswers(id) {
                this.answersVisible.set(id, !this.answersVisible.get(id))
                const data = new Map(this.answersVisible)
                this.set_answersVisible(data)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            userId: this.userId,
                            hotelId: this.currentHotelInfo.id,
                            userName: this.userInfo.userName,
                            question: this.form.getFieldValue('question')
                        }
                        this.addQuestion(data)
                        this.form.resetFields()
                    }
                });
            },
            answerAquestion(id) {
                this.activeQuestionId = id
                this.answerFormVisible = true
            },
            answerFormClose() {
                this.answerFormVisible = false
            },
            submitAnswer(e) {
                e.preventDefault();
                this.answerForm.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            userId: this.userId,
                            userName: this.userInfo.userName,
                            questionId: this.activeQuestionId,
                            answer: this.answerForm.getFieldValue('answer')
                        }
                        this.addAnswer(data)
                        this.answerForm.resetFields()
                    }
                })
                this.answerFormVisible = false
            },
            showCouponDetail(record) {
                this.set_currentCouponInfo(record)
                this.set_couponDetailVisible(true)
            },
            deleteMyQuestion(id) {
                this.deleteQuestion(id)
            },
            deleteMyAnswer(id) {
                this.deleteAnswer(id)
            }
        }
    }
</script>

<style scoped>
    .statistic {
        margin: 5px;
    }
</style>
