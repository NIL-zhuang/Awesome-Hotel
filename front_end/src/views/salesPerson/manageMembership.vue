<template>
    <div>
        <div class="info-wrapper">
            <a-tabs>
                <a-tab-pane tab="信誉充值" key="1">
                    <manage-user-credit></manage-user-credit>
                </a-tab-pane>

                <a-tab-pane tab="用户会员管理" key="2">
                    <manage-client-membership></manage-client-membership>
                </a-tab-pane>

                <a-tab-pane tab="企业会员管理" key="3">
                    <manage-corp-membership></manage-corp-membership>
                </a-tab-pane>

                <a-tab-pane tab="会员等级管理" key="4">
                    <manage-level></manage-level>
                </a-tab-pane>
            </a-tabs>

        </div>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import manageUserCredit from "./manageUserCredit";
    import ManageClientMembership from "./components/ManageClientMembership";
    import ManageCorpMembership from "./components/ManageCorpMembership";
    import ManageLevel from "./components/ManageLevel";
    export default {
        name: "manageMembership",
        data() {
            return {

            }
        },
        components: {
            ManageLevel,
            manageUserCredit,
            ManageClientMembership,
            ManageCorpMembership,
        }
    }
</script>

<style scoped lang="less">
    .info-wrapper {
        padding: 50px;
    }
</style>
<style lang="less">
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
