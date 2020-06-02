<template>
    <div class="hotel-info">
        <div class="base-info">
            <a-form :form="form">
                <a-form-item label="酒店名称" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                    <a-input
                            placeholder="请填写酒店名称"
                            v-decorator="['name', { rules: [{ required: true, message: '请输入酒店名称!' }], initialValue: hotelName }]"
                            v-if="modify"
                    />
                    <span v-else>{{ hotelName }}</span>
                </a-form-item>

                <a-form-item label="酒店地址" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                    <a-input
                            placeholder="请填写酒店地址"
                            v-decorator="['address', { rules: [{ required: true, message: '请填写酒店地址' }], initialValue: hotelAddress }]"
                            v-if="modify"
                    />
                    <span v-else>{{ hotelAddress }}</span>
                </a-form-item>

                <a-form-item label="酒店简介" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                    <a-input
                            type="textarea"
                            :rows="4"
                            placeholder="请填写酒店简介"
                            v-decorator="['description', { rules: [{ required: true, message: '请填写酒店简介' }], initialValue: hotelDescription }]"
                            v-if="modify"
                    />
                    <span v-else>{{ hotelDescription }}</span>
                </a-form-item>

                <!-- 按钮 -->
                <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                    <a-button type="primary" @click="saveModify">
                        保存
                    </a-button>
                    <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                        取消
                    </a-button>
                </a-form-item>
                <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                    <a-button type="primary" @click="modifyInfo">
                        修改信息
                    </a-button>
                </a-form-item>
            </a-form>
        </div>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex';
    import { message } from 'ant-design-vue'
export default {
    name: "modifyHotelInfo",
    data() {
        return {
            modify: false,
            form: this.$form.createForm(this, { name: 'modifyHotelInfo' }),
        }
    },
    computed: {
        ...mapGetters([
            'hotelInfo',
        ]),
        hotelName() {
            return this.hotelInfo.name
        },
        hotelAddress() {
            return this.hotelInfo.address
        },
        hotelDescription() {
            return this.hotelInfo.description
        },
    },
    methods: {
        modifyInfo() {
            this.modify = true
        },
        saveModify() {
            message.error('TODO 修改信息')
            this.modify = false
        },
        cancelModify() {
            message.info('取消修改')
            this.modify = false
        }
    }
}
</script>

<style scoped lang="less">
    .hotel-info {
        width: 100%;
        .info{
            margin-top: 20px;
            float: left;
            width: 100%;
        }
    }
</style>