<template>
    <a-list
            class="comment-list"
            :header="`共${hotelComments.length}条点评`"
            item-layout="horizontal"
            :data-source="hotelComments"
    >
        <a-list-item slot="renderItem" slot-scope="item">
            <a-comment :author="getUserNameById(item.userId)" :avatar="userLogo(item.userId)" class="hotelComment">
                <p slot="content">
                    总体评分: <a-rate :default-value="item.points" :disabled="true" /><br/>
                    卫生: <a-rate :default-value="item.sanitation" :disabled="true" />&nbsp;
                    环境: <a-rate :default-value="item.environment" :disabled="true" />&nbsp;
                    服务: <a-rate :default-value="item.service" :disabled="true" />&nbsp;
                    设施: <a-rate :default-value="item.equipment" :disabled="true" /><br/><br/>
                    <a-tag color="cyan">评价:</a-tag>{{ item.comment }}
                </p>
            </a-comment>
        </a-list-item>
    </a-list>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import moment from 'moment';

    export default {
        name: "HotelComment",
        data() {
            return {
                moment,
            }
        },
        computed: {
            ...mapGetters([
                'hotelComments',
                'allUserList',
            ]),
        },
        async mounted() {
            await this.getHotelComments()
            await this.getAllUsers()
        },
        methods:{
            ...mapActions([
                'getHotelComments',
                'getAllUsers',
            ]),
            getUserNameById(userId) {
                for (let i = 0; i < this.allUserList.length; i++) {
                    if(this.allUserList[i].id === userId) {
                        return this.allUserList[i].userName
                    }
                }
                return '未知用户'
            },
            userLogo(userId) {
                for (let i = 0; i < this.allUserList.length; i++) {
                    if(this.allUserList[i].id === userId) {
                        if(this.allUserList[i].vipType === 'Normal')
                            return 'https://colarhua-pic.oss-cn-shenzhen.aliyuncs.com/gary-jw-3/用户.svg'
                        else
                            return 'https://colarhua-pic.oss-cn-shenzhen.aliyuncs.com/gary-jw-3/会员.svg'
                    }
                }
                return 'https://colarhua-pic.oss-cn-shenzhen.aliyuncs.com/gary-jw-3/用户.svg'
            }
        },

    }
</script>

<style scoped>
    .hotelComment {
        padding-left: 30px;
        border: 1px solid #919191;
        width: 100%;
        border-radius: 10%;
    }
</style>
