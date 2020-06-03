<template>
    <a-modal
            :visible="registerCorporationMembershipModalVisible"
            title="注册企业会员"
            cancelText="取消"
            okText="注册"
            @cancel="cancelRegister"
            @ok="confirmRegister"
            width="450px"
    >
        <a-form :form="form">
            <a-form-item v-bind="formItemLayout" label="所属企业">
                <a-input
                        placeholder="请输入所属企业名"
                        v-decorator="[
                            'corporation',
                            { rules: [{ required: true, message: '请输入所属企业' }]},
                        ]"
                />
            </a-form-item>
        </a-form>

    </a-modal>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    export default {
        name: "registerCorporationMembership",
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
                form: this.$form.createForm(this, { name: 'registerCorporationMembershipModal' }),
            }
        },
        computed: {
            ...mapGetters([
                'registerCorporationMembershipModalVisible'
            ])
        },
        methods: {
            ...mapMutations([
                'set_registerCorporationMembershipModalVisible'
            ]),
            ...mapActions([
                'registerCorporationMembership'
            ]),
            cancelRegister() {
                this.set_registerCorporationMembershipModalVisible(false);
            },
            confirmRegister(e) {
                // TODO 注册会员的逻辑
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            corporation: String(this.form.getFieldValue('corporation'))
                        }
                        console.log(data)
                        this.registerCorporationMembership(data).then(() => {
                            this.set_registerCorporationMembershipModalVisible(false);
                        })
                        this.form.setFieldsValue({
                            corporation: ''
                        })
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>