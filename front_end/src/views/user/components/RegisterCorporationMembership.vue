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
            <a-form-item v-bind="formItemLayout" label="您的企业">
                <a-input
                    placeholder="请输入企业名"
                    v-decorator="[
                        'corporation',
                        { rules: [{ required: true, message: '请输入企业' }], initialValue: this.userInfo.corporation},
                    ]"
                    :disabled="hasCorporation"
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
                'registerCorporationMembershipModalVisible',
                'userInfo'
            ]),
            hasCorporation() {
                return this.userInfo.corporation !== null
            }
        },
        methods: {
            ...mapMutations([
                'set_registerCorporationMembershipModalVisible'
            ]),
            ...mapActions([
                'registerCorporationMembership',
                'updateUserCorporation',
            ]),
            cancelRegister() {
                this.set_registerCorporationMembershipModalVisible(false);
            },
            confirmRegister(e) {
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        let corpName = String(this.form.getFieldValue('corporation'))
                        // 如果用户尚未登记自己的企业名，就将这个注册的企业名作为他所在的企业
                        if (this.userInfo.corporation === null) {
                            this.updateUserCorporation(corpName)
                        }
                        this.registerCorporationMembership(corpName).then(() => {
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
