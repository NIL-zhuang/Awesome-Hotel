<template>
    <div>
        <a-tabs>
            <a-tab-pane tab="酒店点评" key="1">
                <div style="font-size: large;padding: 10px">
                    <a-icon type="crown" class="eval"/>
                    评分
                    <a-tag color="orange">{{this.currentHotelInfo.rate}}</a-tag>
                    <a-button icon="like" @click="like" class="eval"/>
                    点赞
                    <a-tag color="pink">{{this.currentHotelInfo.rate}}</a-tag>
                    <a-button icon="star" @click="star" class="eval"/>
                    收藏
                    <a-tag color="blue">{{this.currentHotelInfo.rate}}</a-tag>
                </div>
            </a-tab-pane>
            <a-tab-pane tab="常见问题" key="2">
                <a-comment>
                    <a-avatar
                            slot="avatar"
                            src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                            alt="Han Solo"
                    />
                    <div slot="content">
                        <a-form-item>
                            <a-textarea :rows="4" :placeholder="hint" @change="handleChange" />
                        </a-form-item>
                        <a-form-item>
                            <a-button html-type="submit" :loading="submitting" type="primary" @click="handleSubmit">
                                我要提问
                            </a-button>
                        </a-form-item>
                    </div>
                </a-comment>
            </a-tab-pane>
            <a-tab-pane tab="酒店优惠" key="3">

            </a-tab-pane>
        </a-tabs>
    </div>
</template>

<script>
    import {mapGetters} from "vuex";

    export default {
        name: "hotelOutline",
        data() {
            return {
                hint: '输入您的问题',
                submitting: false,
            }
        },
        computed: {
            ...mapGetters([
                'currentHotelInfo',
            ])
        },
        mounted() {

        },
        methods: {
            like() {
                console.log('点赞')
            },
            star() {
                console.log('收藏')
            },
            handleChange() {

            },
            handleSubmit() {

            },
        }
    }
</script>

<style scoped>
    .eval {
        margin-left: 50px;
        background-color: transparent;
        border: transparent;
    }
</style>