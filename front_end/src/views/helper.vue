<!--@author: crx-->
<!--@date: 20200618-->
<template>
    <div style="display: inline-flex">
        <div class="directory">
            <a-button @click="toggleCollapsed" style="margin: 10px">
                <a-icon :type="collapsed ? 'menu-unfold' : 'menu-fold'"/>
            </a-button>
            <a-menu
                    :default-selected-keys="['1a']"
                    :inline-collapsed="collapsed"
                    mode="inline"
                    style="border-radius: 20px"
            >
                <a-sub-menu key="1">
                    <span slot="title"><a-icon type="appstore"/><span>总览</span></span>
                    <a-menu-item key="1a" @click="showOverView">
                        应用简介
                    </a-menu-item>
                    <a-menu-item key="1b" @click="showAboutUs">
                        关于我们
                    </a-menu-item>
                </a-sub-menu>
                <a-sub-menu key="2" v-if="userInfo.userType==='Client'">
                    <span slot="title"><a-icon type="user"/><span>客户</span></span>
                    <a-menu-item key="2a" @click="showBrowse">
                        浏览酒店
                    </a-menu-item>
                    <a-menu-item key="2b" @click="showOrder">
                        预定酒店
                    </a-menu-item>
                    <a-menu-item key="2c" @click="showCreditRecord">
                        信用记录
                    </a-menu-item>
                    <a-menu-item key="2d" @click="showMember">
                        会员相关
                    </a-menu-item>
                </a-sub-menu>
                <a-sub-menu key="3" v-if="userInfo.userType==='HotelManager'">
                    <span slot="title"><a-icon type="team"/><span>酒店工作人员</span></span>
                    <a-menu-item key="3a" @click="showHotelAchievement">
                        酒店业绩
                    </a-menu-item>
                    <a-menu-item key="3b" @click="showManage">
                        酒店经营
                    </a-menu-item>
                    <a-menu-item key="3c" @click="showStrategy">
                        酒店优惠
                    </a-menu-item>
                </a-sub-menu>
                <a-sub-menu key="4" v-if="userInfo.userType==='SalesPerson'">
                    <span slot="title"><a-icon type="shopping-cart"/><span>网站营销人员</span></span>
                    <a-menu-item key="4a" @click="showWebAchievement">
                        网站业绩
                    </a-menu-item>
                    <a-menu-item key="4b" @click="showWebStrategy">
                        网站优惠
                    </a-menu-item>
                    <a-menu-item key="4c" @click="showMemberStrategy">
                        会员策略
                    </a-menu-item>
                    <a-menu-item key="4d" @click="showCreditManagement">
                        信用管理
                    </a-menu-item>
                </a-sub-menu>
                <a-sub-menu key="5" v-if="userInfo.userType==='Admin'">
                    <span slot="title"><a-icon type="key"/><span>网站管理员</span></span>
                    <a-menu-item key="5a" @click="showManageHotel">
                        增删酒店
                    </a-menu-item>
                </a-sub-menu>
            </a-menu>
        </div>
        <div class="content">
            <h1>Awesome Hotel®奥森酒店预定系统</h1>
            <div v-if="webOverview">
                <ul>
                    <li>本系统是Web应用，这意味着用户可以非常方便地使用，无需安装</li>
                    <li>本系统支持四种类型的账户，分别为顾客、酒店工作人员、网站营销人员、酒店管理员，不同的用户使用的用户界面不一样</li>
                    <li>按照您登陆账户的类型，你分别可以看到上述四种用户类型对应使用教程</li>
                    <li>本系统所有的密码均使用加密算法加密后存储，因此用户的账户安全有保障</li>
                    <li>本系统支持所有类型用户修改自己的部分信息，具体可修改的信息在系统对应界面查看即可</li>
                </ul>
            </div>
            <div v-if="aboutUs">
                <ul>
                    <li>本系统为"软工2⃣️100昏"小组的课程大作业，在Github开源</li>
                    <li>开发成员联系方式如下（按照加入开发小组顺序）</li>
                    <li>
                        <ul>
                            <li>庄子元 181830266@smail.nju.edu.cn</li>
                            <li>程荣鑫 181250021@smail.nju.edu.cn</li>
                            <li>韩禧 hanxi@smail.nju.edu.cn</li>
                            <li>郭礼华 181250038@smail.nju.edu.cn</li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div v-if="browse">
                <h3>用户有三种方式可以浏览酒店，分别是<i>"首页"</i>的酒店卡片、<i>"首页"</i>的所有酒店列表，以及使用网站的酒店搜索功能</h3>
                <ol>
                    <li>酒店卡片，你可以挑一个心仪的酒店，点击即可查看酒店详情</li>
                    <li>或者你也可以使用列表方式浏览酒店，列表具有相对完备的筛查功能</li>
                    <li>你还可以在<i>"搜索酒店"</i>页面输入期望的条件，从而搜索出符合条件的酒店</li>
                </ol>
            </div>
            <div v-if="order">
                <ul>
                    <li>
                        <ol>
                            <li>顾客首先在进入酒店详情页面之后输入希望预定的时间，默认今明两天，然后便可以看到该酒店在这个时间段的房间信息</li>
                            <li>选择心仪的房间，填写预定表单，注意不能超出房间人数上限，支持同时预定多间同类房间</li>
                        </ol>
                    </li>
                    <li>
                        在我们的系统中，酒店订单有5种状态
                        <ul>
                            <li>
                                <a-tag color="blue">未入住</a-tag>用户下了订单，但还没有入住
                            </li>
                            <li>
                                <a-tag color="pink">已撤销</a-tag>用户撤销的订单。需要注意的是，在入住当天撤销订单会扣除信用值
                            </li>
                            <li>
                                <a-tag color="orange">已入住</a-tag>用户入住之后的订单状态，入住之后便可以评价。
                            </li>
                            <li>
                                <a-tag color="green">已完成</a-tag>用户退房后完成订单。
                            </li>
                            <li>
                                <a-tag color="red">异常订单</a-tag>用户在入住时间到了仍未入住，有酒店工作人员标记为异常订单。异常订单可以由酒店工作人员补登处理，也可以发起申诉请网站方处理。
                            </li>
                        </ul>
                    </li>
                    <li>
                        <ol>
                            <li>酒店可以被顾客收藏，可以在<i>"个人中心"</i>页面看到自己的收藏列表</li>
                            <li>顾客可以对酒店提出相关问题，等待回答，也可以看到用户在该酒店提出的问题</li>
                            <li>顾客可以查看当前酒店优惠，并在预定时选择相关优惠，除了酒店方优惠，我们还提供网站的优惠，使用方式是和酒店一样的</li>
                        </ol>
                    </li>
                    <li>顾客可以看到自己在当前酒店的历史订单（如果有的话）</li>
                </ul>
            </div>
            <div v-if="creditRecord">
                <ul>
                    <li>顾客每一笔消费，都会引起信用值的变动，信用值小于等于0的顾客无法进行预定</li>
                    <li>
                        <ul>
                            <li>顾客每次守信地完成订单，都会增加等同订单价值的信用值</li>
                            <li>顾客的异常订单会扣除等于订单价值的信用值</li>
                            <li>顾客可以对异常订单扣除的信用进行申诉，视情况恢复一半或全部或者不恢复信用值</li>
                        </ul>
                    </li>
                    <li>每个新顾客都有三次免扣除信用的机会，如果是使用了免扣除机会但是补登入住或者申诉成功的情况，那么我们以信用值的形式作出补偿，免扣除机会不再恢复，补偿量取决于订单价值</li>
                    <li>顾客可以在<i>"个人中心-信用记录"</i>看到自己详细的信用变更记录</li>
                    <li>顾客可以被网站营销人员充值信用，由于本系统不考虑支付问题，因此由顾客自行联系网站营销人员，我们会提供网站营销人员的电话号码</li>
                </ul>
            </div>
            <div v-if="member">
                <ul>
                    <li>顾客可以注册会员，注册时登记生日即可，生日只可以填写一次，暂不支持修改</li>
                    <li>根据不同的VIP等级，顾客消费时可以享受不同的减免</li>
                    <li>注册会员后，顾客可以享受各大酒店的生日优惠券，也可以享受网站发放的指定商圈优惠券</li>
                    <li>用户还可以给自己所在的企业注册会员，由于本系统不考虑支付问题，因此登记企业名称即可，注册后，企业的所有用户都可以享受优惠</li>
                    <li>有关会员的事宜可以咨询网站营销人员，我们提供他们的电话号码</li>
                </ul>
            </div>
            <div v-if="hotelAchievement">
                <ul>
                    <li>酒店工作人员可以在<i>"首页"</i>看见近30天酒店营收图表📈</li>
                </ul>
            </div>
            <div v-if="manage">
                <ul>
                    <li>酒店工作人员可以在<i>"酒店经营-我的酒店"</i>修改所属酒店的信息</li>
                    <li>酒店工作人员可以在<i>"酒店经营-订单管理"</i>处理订单，可以执行订单、标记异常订单</li>
                    <li>酒店工作人员可以在<i>"酒店经营-优惠管理"</i>制定优惠券</li>
                    <li>酒店工作人员可以在<i>"酒店经营-客房管理"</i>输入客房信息</li>
                </ul>
            </div>
            <div v-if="strategy">
                <ul>
                    <li>系统提供四种优惠券给酒店工作人员制作</li>
                    <li>
                        <ol>
                            <li>生日优惠：只有VIP会员能享受该优惠券，在生日当天下单可以享受优惠</li>
                            <li>多间优惠：顾客一次预定指定数目的房间能够享受的优惠</li>
                            <li>满减优惠：顾客订单价值达到一定程度可以享受的减免优惠</li>
                            <li>限时优惠：顾客在限定时间段内入住酒店可以享受的优惠</li>
                        </ol>
                    </li>
                </ul>
            </div>
            <div v-if="webAchievement">
                <ul>
                    <li>网站营销人员可以看见近30天网站所有成功订单交易情况</li>
                    <li>还可以看见当前入驻的所有酒店</li>
                    <li>此外，网站营销人员能够监测未执行以及异常订单</li>
                </ul>
            </div>
            <div v-if="webStrategy">
                <ul>
                    <li>网站营销人员可以指定两种优惠券</li>
                    <li>
                        <ol>
                            <li>节日优惠：顾客在节日期间入住能够享受优惠</li>
                            <li>合作企业优惠券：VIP企业的顾客消费时享受的优惠</li>
                            <li>VIP特定商圈优惠券：VIP顾客在指定商圈消费能够享受的优惠，优惠额度取决于VIP等级</li>
                        </ol>
                    </li>
                </ul>
            </div>
            <div v-if="memberStrategy">
                <ul>
                    <li>网站营销人员可以制定分别普通会员、企业会员的等级，并提供达到每个等级需要的消费额</li>
                    <li>网站营销人员拥有冻结、恢复会员的权力</li>
                </ul>
            </div>
            <div v-if="creditManagement">
                <ul>
                    <li>网站营销人员可以在<i>"会员管理"</i>中为指定顾客充值信用值</li>
                    <li>网站营销人员可以处理顾客的信用申诉，选择为顾客恢复一半、全部或者不恢复申诉的信用</li>
                </ul>
            </div>
            <div v-if="manageHotels">
                <ul>
                    <li>网站管理员拥有增删酒店的权力</li>
                    <li>网站管理员能够为已有的酒店注册酒店工作人员账户</li>
                    <li>网站管理员也能够注册网站营销人员账户</li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>

    import {mapGetters} from "vuex";

    export default {
        name: "helper",
        data() {
            return {
                collapsed: true,

                webOverview: true,
                aboutUs: false,

                browse: false,
                order: false,
                creditRecord: false,
                member: false,

                hotelAchievement: false,
                manage: false,
                strategy: false,

                webAchievement: false,
                webStrategy: false,
                memberStrategy: false,
                creditManagement: false,

                manageHotels: false,
            };
        },
        computed: {
            ...mapGetters([
                'userInfo'
            ])
        },
        methods: {
            toggleCollapsed() {
                this.collapsed = !this.collapsed;
            },
            resetAll() {
                this.webOverview = false
                this.aboutUs = false

                this.browse = false
                this.order = false
                this.creditRecord = false
                this.member = false

                this.hotelAchievement = false
                this.manage = false
                this.strategy = false

                this.webAchievement = false
                this.webStrategy = false
                this.memberStrategy = false
                this.creditManagement = false

                this.manageHotels = false
            },
            showOverView(){
                this.resetAll()
                this.webOverview = true
            },
            showAboutUs(){
                this.resetAll()
                this.aboutUs = true
            },
            showBrowse(){
                this.resetAll()
                this.browse = true
            },
            showOrder(){
                this.resetAll()
                this.order = true
            },
            showCreditRecord(){
                this.resetAll()
                this.creditRecord = true
            },
            showMember(){
                this.resetAll()
                this.member = true
            },
            showHotelAchievement(){
                this.resetAll()
                this.hotelAchievement = true
            },
            showManage(){
                this.resetAll()
                this.manage = true
            },
            showStrategy(){
                this.resetAll()
                this.strategy = true
            },
            showWebAchievement(){
                this.resetAll()
                this.webAchievement = true
            },
            showWebStrategy(){
                this.resetAll()
                this.webStrategy = true
            },
            showMemberStrategy(){
                this.resetAll()
                this.memberStrategy = true
            },
            showCreditManagement(){
                this.resetAll()
                this.creditManagement = true
            },
            showManageHotel(){
                this.resetAll()
                this.manageHotels = true
            }
        },
    }
</script>

<style scoped>
    .directory {
        display: flex;
        margin: 20px;
        height: 100%;
    }

    .content {
        border-radius: 25px;
        margin-top: 20px;
        padding: 10px;
        min-width: 800px;
        height: 100%;
        font-size: large;
    }
</style>