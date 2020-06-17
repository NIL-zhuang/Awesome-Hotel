<template>
    <a-modal
            :visible="levelModifyModalVisible"
            @cancel="cancel"
            @ok="handleSubmit"
            cancelText="取消"
            okText="确定"
            title="修改不同等级对应的所需消费额"
    >
        <a-form :form="form" style="margin-top: 30px">
            <a-form-item label="等级" v-bind="formItemLayout">
                <span>{{ currentLevel }}</span>
            </a-form-item>
            <a-form-item label="所需消费额" v-bind="formItemLayout">
                <a-input
                        allow-clear
                        placeholder="请填写所需消费额"
                        v-decorator="['consumption',{rules: [{required: true, message: '请输入所需消费额'}]}]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    export default {
        name: "ModifyLevelConsumptionModal",
        data () {
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
            }
        },
        computed: {
            ...mapGetters([
                'levelModifyModalVisible',
                'currentLevel',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'levelModifyModal'});
        },
        methods: {
            ...mapMutations([
                'set_levelModifyModalVisible',
            ]),
            ...mapActions([
                'formulateALevel',
            ]),
            cancel() {
                this.set_levelModifyModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault()
                this.form.validateFieldsAndScroll((err, values) => {
                    if(!err) {
                        const params = {
                            level: Number(this.currentLevel),
                            requestConsumption: Number(this.form.getFieldValue('consumption'))
                        }
                        this.formulateALevel(params)
                    }
                })
            }

        }
    }
</script>

<style scoped>

</style>
