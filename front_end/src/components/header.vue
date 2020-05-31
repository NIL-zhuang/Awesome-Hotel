<template>
    <div class="header">
        <div class="label">
            <img @click="jumpToHome" alt="logo" class="logo" src="@/assets/logo.svg">
            <span class="title">Awesome Hotel</span>
        </div>
        <a-menu mode="horizontal" theme="light" v-model="current">
            <a-menu-item @click="selectMenu" key="1">
                <router-link :to="{ name: 'home' }">
                    <a-icon type="home"/>
                    首页
                </router-link>
            </a-menu-item>
            <a-menu-item @click="jumpToUserInfo" key="2" v-if="userInfo.userType==='Client'">
                <a-icon type="user"/>
                个人中心
            </a-menu-item>
            <a-menu-item @click="selectMenu" key="3" v-if="userInfo.userType==='HotelManager'">
                <router-link :to="{ name: 'manageHotel'}">
                    <a-icon type="switcher"/>
                    酒店经营
                </router-link>
            </a-menu-item>
            <a-menu-item @click=jumpToManagerInfo key="4" v-if="userInfo.userType==='HotelManager'">
                <a-icon type="user"/>
                工作人员
            </a-menu-item>
            <a-menu-item @click="searchHotel" key="5" v-if="userInfo.userType==='Client'">
                <router-link :to="{ name: 'searchHotel'}">
                    <a-icon type="search"/>
                    搜索酒店
                </router-link>
            </a-menu-item>
            <a-menu-item @click="registerAsMember" key="6" v-if="userInfo.userType==='Client'">
                <router-link :to="{ name: 'userMembership'}">
                    <a-icon type="usergroup-add"/>
                    会员中心
                </router-link>
            </a-menu-item>
            <a-menu-item @click="registerAsMember" key="7" v-if="userInfo.userType==='HotelManager'">
                <router-link :to="{ name: 'hotelMembership'}">
                    <a-icon type="usergroup-add"/>
                    注册企业会员
                </router-link>
            </a-menu-item>
            <a-menu-item @click="manageUser" key="8" v-if="userInfo.userType==='Admin'">
                <router-link :to="{ name: 'manageUser'}">
                    <a-icon type="user"/>
                    账户管理
                </router-link>
            </a-menu-item>
            <a-menu-item @click="manageAdminInfo" key="9" v-if="userInfo.userType==='Admin'">
                <router-link :to="{ name: 'adminInfo'}">
                    <a-icon type="user"/>
                    个人信息
                </router-link>
            </a-menu-item>
            <a-menu-item @click="manageOrders" key="10" v-if="userInfo.userType==='SalesPerson'">
                <router-link :to="{ name: 'manageOrders'}">
                    <a-icon type="shopping-cart"/>
                    订单管理
                </router-link>
            </a-menu-item>
            <a-menu-item @click="jumpToManageSiteCoupon" key="11" v-if="userInfo.userType==='SalesPerson'">
                <router-link :to="{ name: 'manageSiteCoupon'}">
                    <a-icon type="tag"/>
                    优惠策略
                </router-link>
            </a-menu-item>
            <a-menu-item @click="jumpToManageUserCredit" key="12" v-if="userInfo.userType==='SalesPerson'">
                <router-link :to="{ name: 'manageUserCredit'}">
                    <a-icon type="user"/>
                    信用管理
                </router-link>
            </a-menu-item>
        </a-menu>
        <div class="logout">
            <a-dropdown placement="bottomCenter">
                <div class="user">
                    <a-avatar src="./defaultAvatar.png"></a-avatar>
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
                    <a-menu-item>
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
        name: '',
        data() {
            return {
                current: ['1']
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
                case 'searchHotel':
                    this.current = ['5']
                    break
                case 'userMembership':
                    this.current = ['6']
                    break
                case 'hotelMembership':
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
                case 'manageUserCredit':
                    this.current = ['12']
                    break
            }
        },
        methods: {
            ...mapMutations([]),
            ...mapActions([
                'logout',
                'getUserInfo'
            ]),
            selectMenu(v) {
                // 也许可以在这里限定酒店工作人员能看到的酒店
            },
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
            jumpToHome() {
                this.$router.push('/homePage')
            },
            searchHotel() {
                this.$router.push('/hotel/searchHotel')
            },
            registerAsMember() {
                if (this.userInfo.userType === 'Client')
                    this.$router.push('/user/membership')
                else if (this.userInfo.userType === 'HotelManger')
                    this.$router.push('/hotel/membership')
            },
            jumpToManageHotelMembership() {

            },
            jumpToManageSiteCoupon() {
                this.$router.push('/salesPerson/manageSiteCoupon')
            },
            manageUser() {

            },
            manageOrders() {
                this.$router.push('/salesPerson/manageOrders')
            },
            jumpToManageUserCredit() {
                this.$router.push('/salesPerson/manageUserCredit')
            },
            manageAdminInfo() {

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
    }
</style>