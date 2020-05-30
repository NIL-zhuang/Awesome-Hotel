<template>
    <a-modal
            :visible="registerHotelMembershipModalVisible"
            title="注册会员"
            cancelText="取消"
            okText="注册"
            @cancel="cancelRegister"
            @ok="confirmRegister"
            width="450px"
    >
        <a-form :form="form">
            <p>是否确定注册会员</p>
        </a-form>

    </a-modal>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from "vuex";

    export default {
        name: "registerHotelMembership",
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
            }
        },
        computed: {
            ...mapGetters([
                'registerHotelMembershipModalVisible',
                'manageHotelId',
            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, {name: 'registerMembershipModal'});
        },
        methods: {
            ...mapMutations([
                'set_registerHotelMembershipModalVisible'
            ]),
            ...mapActions([
               'registerHotelMembership'
            ]),
            cancelRegister() {
                this.set_registerHotelMembershipModalVisible(false);
            },
            confirmRegister() {
                // TODO 注册会员的逻辑
                const data = {
                    id: this.manageHotelId
                }
                this.registerHotelMembership(data)
            }
        }
    }
</script>

<style scoped>

</style>