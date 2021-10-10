<template>
    <a-modal
            title="发布评论"
            :visible="commentOrderModalVisible"
            :footer="null"
            @cancel="handleCancel"
    >
        <a-form :form="form">

            <a-form-item label="酒店名称" v-bind="formItemLayout">
                <span>{{ orderInfo.hotelName }}</span>
            </a-form-item>
            <a-form-item label="总体评分" v-bind="formItemLayout">
                <span v-if="!hasCommented">
                    <a-rate v-decorator="['points', { rules: [{ required: true, message: '请为酒店打分' }] }]" />
                </span>
                <span v-else>
                    <a-rate :value="points" :disabled="true" />
                </span>

            </a-form-item>
            <a-form-item label="卫生条件" v-bind="formItemLayout">
                <span v-if="!hasCommented">
                    <a-rate v-decorator="['sanitation', { rules: [{ required: true, message: '请为酒店卫生条件打分'}] }]" />
                </span>
                <span v-else>
                    <a-rate :value="sanitation" :disabled="true" />
                </span>
            </a-form-item>
            <a-form-item label="环境" v-bind="formItemLayout">
                <span v-if="!hasCommented">
                    <a-rate v-decorator="['environment', { rules: [{ required: true, message: '请为酒店环境打分'}] }]" />
                </span>
                <span v-else>
                    <a-rate :value="environment" :disabled="true" />
                </span>
            </a-form-item>
            <a-form-item label="服务" v-bind="formItemLayout">
                <span v-if="!hasCommented">
                    <a-rate v-decorator="['service', { rules: [{ required: true, message: '请为酒店服务打分'}] }]" />
                </span>
                <span v-else>
                    <a-rate :value="service" :disabled="true" />
                </span>
            </a-form-item>
            <a-form-item label="设备" v-bind="formItemLayout">
                <span v-if="!hasCommented">
                    <a-rate v-decorator="['equipment', { rules: [{ required: true, message: '请为酒店设备打分'}] }]" />
                </span>
                <span v-else>
                    <a-rate :value="equipment" :disabled="true" />
                </span>
            </a-form-item>
            <a-form-item label="您的评价" v-bind="formItemLayout">
                <a-textarea v-if="!hasCommented"
                    :auto-size="{ minRows: 3, maxRows: 5 }"
                    v-decorator="['comment', { rules: [{ required: true, message: '请为酒店设备打分'}] }]"
                />
                <span v-else>
                    {{ currentOrderComment.comment }}
                </span>
            </a-form-item>
            <div style="margin-left: 65%">
                <a-button @click="handleCancel" v-if="!hasCommented" type="primary">取消</a-button>
                <a-button @click="annulComment" v-else type="danger">撤回</a-button>
                <a-divider type="vertical"></a-divider>
                <a-button @click="handleOk" type="primary">
                    <span v-if="!hasCommented">发布</span>
                    <span v-else>确定</span>
                </a-button>

            </div>
        </a-form>
    </a-modal>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    export default {
        name: "CommentOrder",
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: {span: 12},
                        sm: {span: 6},
                    },
                    wrapperCol: {
                        xs: {span: 24},
                        sm: {span: 16},
                    },
                },
                form: this.$form.createForm(this, {name: 'coordinated'}),
            }
        },
        computed: {
            ...mapGetters([
                'commentOrderModalVisible',
                'orderInfo',
                'currentOrderComment',
            ]),
            hasCommented() {
                return this.currentOrderComment.points !== null
            },
            points() {
                if(this.currentOrderComment.points !== null) {
                    return this.currentOrderComment.points
                } else {
                    return 0
                }
            },
            sanitation() {
                if(this.currentOrderComment.sanitation !== null) {
                    return this.currentOrderComment.sanitation
                } else {
                    return 0
                }
            },
            environment() {
                if(this.currentOrderComment.environment !== null) {
                    return this.currentOrderComment.environment
                } else {
                    return 0
                }
            },
            service() {
                if(this.currentOrderComment.service !== null) {
                    return this.currentOrderComment.service
                } else {
                    return 0
                }
            },
            equipment() {
                if(this.currentOrderComment.equipment !== null) {
                    return this.currentOrderComment.equipment
                } else {
                    return 0
                }
            }


        },
        methods: {
            ...mapMutations([
                'set_commentOrderModalVisible',
            ]),
            ...mapActions([
                'addComment',
                'getOrderComment',
                'annulOrderComment',
                'getUserOrders',
            ]),
            handleOk() {
                if (!this.hasCommented) {
                    this.form.validateFields((err, values) => {
                        if(!err){
                            const data = {
                                userId: this.orderInfo.userId,
                                orderId: this.orderInfo.id,
                                points: Number(this.form.getFieldValue('points')),
                                sanitation: Number(this.form.getFieldValue('sanitation')),
                                environment: Number(this.form.getFieldValue('environment')),
                                service: Number(this.form.getFieldValue('service')),
                                equipment: Number(this.form.getFieldValue('equipment')),
                                comment: this.form.getFieldValue('comment')
                            }
                            this.addComment(data).then(() => {
                                this.form.resetFields()
                                // this.getUserOrders() --6.25
                            })
                        }
                    })
                } else {
                    this.set_commentOrderModalVisible(false)
                }
            },
            annulComment() {
                this.annulOrderComment(this.orderInfo.id).then(() => {
                    this.getUserOrders()
                    this.getOrderComment(this.orderInfo.id)
                    this.form.resetFields()
                })
            },
            handleCancel() {
                this.form.resetFields()
                this.set_commentOrderModalVisible(false)
            },
        }

    }
</script>

<style scoped>

</style>
