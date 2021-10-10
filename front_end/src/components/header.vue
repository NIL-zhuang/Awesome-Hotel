<template>
    <div class="header">
        <div class="label">
            <img @click="jumpToHome" alt="logo" class="logo" src="@/assets/logo.svg">
            <span class="title">Awesome Hotel 奥森</span>
        </div>
        <a-menu mode="horizontal" theme="light" v-model="current">
            <a-menu-item key="1">
                <router-link :to="{ name: 'home' }">
                    <a-icon type="home"/>
                    首页
                </router-link>
            </a-menu-item>
            <a-menu-item key="5" @click="jumpToSalesPersonInfo" v-if="userInfo.userType==='SalesPerson'">
                <a-icon type="user"/>
                营销人员
            </a-menu-item>
            <a-menu-item @click="jumpToUserInfo" key="2" v-if="userInfo.userType==='Client'">
                <a-icon type="user"/>
                个人中心
            </a-menu-item>
            <a-menu-item key="3" v-if="userInfo.userType==='HotelManager'">
                <router-link :to="{ name: 'manageHotel'}">
                    <a-icon type="switcher"/>
                    酒店经营
                </router-link>
            </a-menu-item>
            <a-menu-item @click="jumpToManagerInfo" key="4" v-if="userInfo.userType==='HotelManager'">
                <a-icon type="user"/>
                工作人员
            </a-menu-item>
            <a-menu-item key="6" v-if="userInfo.userType==='Client'">
                <router-link :to="{ name: 'userMembership'}">
                    <a-icon type="usergroup-add"/>
                    会员中心
                </router-link>
            </a-menu-item>
            <a-menu-item key="8" v-if="userInfo.userType==='Admin'">
                <router-link :to="{ name: 'manageUser'}">
                    <a-icon type="user"/>
                    账户管理
                </router-link>
            </a-menu-item>
            <a-menu-item key="9" v-if="userInfo.userType==='Admin'">
                <router-link :to="{ name: 'adminInfo'}">
                    <a-icon type="user"/>
                    管理员信息
                </router-link>
            </a-menu-item>
            <a-menu-item key="10" v-if="userInfo.userType==='SalesPerson'">
                <router-link :to="{ name: 'manageOrders'}">
                    <a-icon type="shopping-cart"/>
                    交易情况
                </router-link>
            </a-menu-item>
            <a-menu-item key="11" v-if="userInfo.userType==='SalesPerson'">
                <router-link :to="{ name: 'manageSiteCoupon'}">
                    <a-icon type="tag"/>
                    优惠策略
                </router-link>
            </a-menu-item>
            <a-menu-item key="12" v-if="userInfo.userType==='SalesPerson'">
                <router-link :to="{ name: 'manageMembership'}">
                    <a-icon type="user"/>
                    会员管理
                </router-link>
            </a-menu-item>
            <a-menu-item key="7">
                <router-link :to="{ name: 'helper'}">
                    <a-icon type="question-circle"/>
                    帮助
                </router-link>
            </a-menu-item>
        </a-menu>
        <div class="logout">
            <a-dropdown placement="bottomCenter">
                <div class="user">
                    <a-avatar style="background-color: mediumpurple" icon="user"/>
                    <span style="font-size: 14px">你好,</span>
                    <span v-if="userInfo.userType!=='Admin'">{{ userInfo.userName }}!</span>
                    <span v-else>管理员!</span>
                    <a-icon style="margin-left: 3px; font-size: 16px" type="down"></a-icon>
                </div>
                <a-menu slot="overlay">
                    <a-menu-item @click="jumpToHome">
                        <a-icon type="home"></a-icon>
                        首页
                    </a-menu-item>
                    <a-menu-item @click="openHelper">
                        <a-icon type="question-circle"></a-icon>
                        帮助
                    </a-menu-item>
                    <a-menu-item @click="quit">
                        <a-icon type="poweroff"></a-icon>
                        退出登录
                    </a-menu-item>
                </a-menu>
            </a-dropdown>
        </div>
    </div>
</template>
<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'

    export default {
        name: 'Header',
        data() {
            return {
                current: ['1'],
            }
        },
        computed: {
            ...mapGetters([
                'userId',
                'userInfo'
            ])
        },
        async mounted() {
            await this.getUserInfo(); // 防止刷新导致的信息丢失
            switch (this.$route.name) {
                case 'home':
                    this.current = ['1']
                    break
                case 'userInfo':
                    this.current = ['2']
                    break
                case 'manageHotel':
                    this.current = ['3']
                    break
                case 'managerInfo':
                    this.current = ['4']
                    break
                case 'salesPersonInfo':
                    this.current = ['5']
                    break
                case 'userMembership':
                    this.current = ['6']
                    break
                case 'helper':
                    this.current = ['7']
                    break
                case 'manageUser':
                    this.current = ['8']
                    break
                case 'adminInfo':
                    this.current = ['9']
                    break
                case 'manageOrders':
                    this.current = ['10']
                    break
                case 'manageSiteCoupon':
                    this.current = ['11']
                    break
                case 'manageMembership':
                    this.current = ['12']
                    break
            }
        },
        mutations: {
            set_current: (data) => {
                this.current = data
            }
        },
        methods: {
            ...mapMutations([]),
            ...mapActions([
                'logout',
                'getUserInfo'
            ]),
            async quit() {
                await this.$store.dispatch('logout')
                await this.$router.push(`/login?redirect=${this.$route.fullPath}`)
            },
            jumpToUserInfo() {
                this.$router.push({name: 'userInfo', params: {userId: this.userId}})
            },
            jumpToManagerInfo() {
                this.$router.push({name: 'managerInfo', params: {userId: this.userId}})
            },
            jumpToSalesPersonInfo() {
                this.$router.push({name: 'salesPersonInfo', params: {userId: this.userId}})
            },
            jumpToHome() {
                this.current = ['1']
                this.$router.push('/homePage')
            },
            registerAsMember() {
                this.$router.push('/user/membership')
            },
            jumpToManageSiteCoupon() {
                this.$router.push('/salesPerson/manageSiteCoupon')
            },
            manageOrders() {
                this.$router.push('/salesPerson/manageOrders')
            },
            openHelper() {
                this.current = ['7']
                this.$router.push('/helper')
            }
        }
    }
</script>
<style lang="less" scoped>
    .header {
        display: flex;
        line-height: 44px;
        height: 44px;
        align-items: center;
        justify-content: space-between;
        min-width: 800px;

        .label {
            height: 44px;
            line-height: 44px;
            vertical-align: middle;
            min-width: 400px;

            .logo {
                height: 44px;
                vertical-align: top;
                margin-right: 16px;
                border-style: none;
                cursor: pointer;
            }

            .title {
                font-size: 33px;
                color: rgba(0, 0, 0, .85);
                font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
                font-weight: 600;
                position: relative;
                top: 2px;
            }

            .search {
                width: 300px;
                margin-left: 30px
            }
        }

        .logout {
            margin-right: 40px;

            .user {
                cursor: pointer;
                display: flex;
                align-items: center;

                span {
                    margin-left: 5px
                }
            }
        }

    }
</style>
<style lang="less">
    .header {
        .ant-menu {
            background: none
        }
        .title {
            font-family: Arial, sans-serif;
        }
    }
</style>
