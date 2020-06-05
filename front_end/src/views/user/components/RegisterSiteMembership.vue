<template>
    <a-modal
            :visible="registerSiteMembershipModalVisible"
            title="注册普通会员"
            cancelText="取消"
            okText="注册"
            @cancel="cancelRegister"
            @ok="confirmRegister"
            width="450px"
    >
        <a-form :form="form">
            <a-form-item v-bind="formItemLayout" label="您的生日">
                <a-date-picker
                        v-decorator="[
                            'birthday',
                            { rules: [{ required: true, message: '请选择您的生日' }]},
                        ]"
                />
            </a-form-item>
        </a-form>

    </a-modal>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    const moment = require('moment')
    export default {
        name: "registerSiteMembership",
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
                form: this.$form.createForm(this, { name: 'registerMembershipModal' }),
            }
        },
        computed: {
            ...mapGetters([
                'registerSiteMembershipModalVisible'
            ])
        },
        methods: {
            ...mapMutations([
                'set_registerSiteMembershipModalVisible'
            ]),
            ...mapActions([
               'registerMembership'
            ]),
            cancelRegister() {
                this.set_registerSiteMembershipModalVisible(false);
            },
            confirmRegister(e) {
                // TODO 注册会员的逻辑
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            birthday: moment(this.form.getFieldValue('birthday')).format('YYYY-MM-DD')
                        }
                        this.registerMembership(data).then(() => {
                            this.set_registerSiteMembershipModalVisible(false);
                        })
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>