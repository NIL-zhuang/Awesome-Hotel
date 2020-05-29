<template>
    <div class="header">
        <div class="label">
            <img src="@/assets/logo.svg" class="logo" alt="logo" @click="jumpToHome">
            <span class="title">Awesome Hotel</span>
        </div>
        <a-menu v-model="current" mode="horizontal" theme="light">
            <a-menu-item key="1" @click="selectMenu">
                <router-link to="/hotel/hotelList">
                    <a-icon type="home" />首页
                </router-link>
            </a-menu-item>
            <a-menu-item key="2" @click="jumpToUserInfo" v-if="userInfo.userType==='Client'">
                <a-icon type="user" />个人中心
            </a-menu-item>
            <a-menu-item key="3" @click="selectMenu" v-if="userInfo.userType==='HotelManager'">
                <router-link :to="{ name: 'manageHotel'}">
                     <a-icon type="switcher" />酒店经营
                </router-link>
            </a-menu-item>
            <a-menu-item key="4" @click=jumpToManagerInfo v-if="userInfo.userType==='HotelManager'">
                <a-icon type="user" />工作人员
            </a-menu-item>
            <a-menu-item key="5" @click="searchHotel" v-if="userInfo.userType==='Client'">
                <router-link :to="{ name: 'searchHotel'}">
                    <a-icon type="search" />搜索酒店
                </router-link>
            </a-menu-item>
            <a-menu-item key="6" @click="registerAsMember" v-if="userInfo.userType==='Client'">
                <router-link :to="{ name: 'userMembership'}">
                    <a-icon type="usergroup-add"/>会员中心
                </router-link>
            </a-menu-item>
            <a-menu-item key="7" @click="registerAsMember" v-if="userInfo.userType==='HotelManager'">
                <router-link :to="{ name: 'hotelMembership'}">
                    <a-icon type="usergroup-add"/>注册企业会员
                </router-link>
            </a-menu-item>
            <a-menu-item key="8" @click="manageUser" v-if="userInfo.userType==='Admin'">
                <router-link :to="{ name: 'manageUser'}">
                    <a-icon type="user" />账户管理
                </router-link>
            </a-menu-item>
            <a-menu-item key="9" @click="manageAdminInfo" v-if="userInfo.userType==='Admin'">
                <router-link :to="{ name: 'adminInfo'}">
                    <a-icon type="user" />个人信息
                </router-link>
            </a-menu-item>
            <a-menu-item key="10" @click="manageOrders" v-if="userInfo.userType==='SalesPerson'">
                <router-link :to="{ name: 'manageOrders'}">
                    <a-icon type="shopping-cart" />订单管理
                </router-link>
            </a-menu-item>
            <a-menu-item key="11" @click="jumpToManageSiteCoupon" v-if="userInfo.userType==='SalesPerson'">
                <router-link :to="{ name: 'manageSiteCoupon'}">
                    <a-icon type="tag" />优惠策略
                </router-link>
            </a-menu-item>
            <a-menu-item key="12" @click="jumpToManageUserCredit" v-if="userInfo.userType==='SalesPerson'">
                <router-link :to="{ name: 'manageUserCredit'}">
                    <a-icon type="user" />信用管理
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
                    <a-menu-item  @click="jumpToHome">
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
import { mapGetters, mapActions, mapMutations } from 'vuex'
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
        if (this.$route.name === 'hotelList' || this.$route.name === 'hotelDetail' || this.$route.name === 'searchHotel') {
            this.current = ['1']  // crx在这里做了修改 5.6 添加了第三个条件
        } else if (this.$route.name === 'userInfo') {
            this.current = ['2']
        } else if (this.$route.name === 'manageHotel') {
            this.current = ['3']
        } else {
            this.current = ['4']
        }

    },
    methods: {
        ...mapMutations([

        ]),
        ...mapActions([
            'logout',
            'getUserInfo'
        ]),
        selectMenu(v){
            // 也许可以在这里限定酒店工作人员能看到的酒店
        },
        async quit() {
            await this.$store.dispatch('logout')
            await this.$router.push(`/login?redirect=${this.$route.fullPath}`)
        },
        jumpToUserInfo() {
            this.$router.push({ name: 'userInfo', params: { userId: this.userId } })
        },
        jumpToManagerInfo() {
            this.$router.push({ name: 'managerInfo', params: { userId: this.userId } })
        },
        jumpToHome() {

        },
        searchHotel(){
            this.$router.push('/hotel/searchHotel')
        },
        registerAsMember(){
            if (this.userInfo.userType==='Client')
                this.$router.push('/user/membership')
            else if (this.userInfo.userType==='HotelManger')
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
<style scoped lang="less">
    .header {
        display: flex;
        line-height: 44px;
        height: 44px;
        align-items: center;
        justify-content: space-between;
        min-width: 800px;
        .label{
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
                display:flex;
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