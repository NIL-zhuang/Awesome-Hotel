<template>
    <a-modal
            title="申诉异常"
            :visible="argueAbnormalOrderModalVisible"
            :footer="null"
            @cancel="handleCancel"
    >
        <a-form :form="form">
            <a-form-item label="申诉内容" v-bind="formItemLayout">
                <a-textarea
                    :auto-size="{ minRows: 3, maxRows: 5 }"
                    v-decorator="['reason', { rules: [{ required: true, message: '请填写您的申诉内容'}], initialValue: this.currentOrderComment.comment }]"
                    v-if="argumentModify"
                />
                <span v-else>
                    {{ this.currentOrderComment.comment }}
                </span>
            </a-form-item>
            <div style="margin-left: 65%">
                <a-button @click="handleCancel" type="primary" v-if="!hasArgued">取消</a-button>
                <a-button @click="modifyInfo" type="primary" v-if="hasArgued">修改</a-button>
                <a-divider type="vertical"></a-divider>
                <a-button @click="handleOk" type="primary">
                    <span v-if="!hasArgued">提交</span>
                    <span v-else>确定</span>
                </a-button>

            </div>
        </a-form>
    </a-modal>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    export default {
        name: "ArgueOrder",
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
                form: this.$form.createForm(this, {name: 'argueOrder'}),
            }
        },
        computed: {
            ...mapGetters([
                'argueAbnormalOrderModalVisible',
                'orderInfo',
                'currentOrderComment',
                'argumentModify',
            ]),
            hasArgued() {
                return this.currentOrderComment.comment !== null
            },
        },
        methods: {
            ...mapMutations([
                'set_argueAbnormalOrderModalVisible',
                'set_argumentModify',
            ]),
            ...mapActions([
                'argueAbnormalOrder',
                'getOrderComment',
            ]),
            handleOk() {
                if (this.argumentModify) {
                    this.form.validateFields((err, values) => {
                        if(!err){
                            const data = {
                                orderId: this.orderInfo.id,
                                reason: this.form.getFieldValue('reason')
                            }
                            this.argueAbnormalOrder(data).then(() => {
                                this.form.resetFields()
                                this.set_argueAbnormalOrderModalVisible(false)
                            })
                        }
                    })
                } else {
                    this.set_argueAbnormalOrderModalVisible(false)
                }
                this.set_argumentModify(false)
            },
            handleCancel() {
                this.form.resetFields()
                this.set_argueAbnormalOrderModalVisible(false)
                this.set_argumentModify(false)
            },
            modifyInfo() {
                this.set_argumentModify(true)
            }
        }

    }
</script>

<style scoped>

</style>
