<template>
    <a-modal
            :visible="registerMembershipModalVisible"
            title="注册会员"
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
        name: "registerMembership",
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
                'registerMembershipModalVisible'
            ])
        },
        methods: {
            ...mapMutations([
                'set_registerMembershipModalVisible'
            ]),
            ...mapActions([
               'registerMembership'
            ]),
            cancelRegister() {
                this.set_registerMembershipModalVisible(false);
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
                            this.set_registerMembershipModalVisible(false);
                        })
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>