<template>
    <div class="info">
        <a-tabs>
            <a-tab-pane key="1" tab="个人信息">
                <InfoForm></InfoForm>
            </a-tab-pane>

            <a-tab-pane key="2" tab="密码更改">
                <password-form></password-form>
            </a-tab-pane>

        </a-tabs>
    </div>

</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import InfoForm from '../../views/user/components/infoForm'
    import passwordForm from "../user/components/passwordForm";

    export default {
        name: "adminInfo",
        data() {
            return {

            }
        },
        components: {
            InfoForm,
            passwordForm,
        },
        computed: {
            ...mapGetters([

            ])
        },
        mounted() {
            this.getUserInfo()
        },
        methods: {
            ...mapActions([
                'getUserInfo'
            ])
        },
    }
</script>

<style lang="less" scoped>
    .info {
        padding: 30px;
        .ant-tabs-bar {
            padding-left: 30px;
        }
    }
</style>
