<template>
    <div class="hotel-info">
        <div class="base-info">
            <a-form :form="form" style="border-radius: 20px; background-color: whitesmoke">
                <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="酒店名称">
                    <span>{{ hotelName }}</span>
                </a-form-item>

                <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="酒店商圈">
                    <a-select
                            v-decorator="['bizRegion',{ rules: [{ required: false, message: '请选择酒店商圈' }]}]"
                            v-if="modify"
                    >
                        <a-select-option value="XiDan">西单</a-select-option>
                        <a-select-option value="XianLin">仙林</a-select-option>
                        <a-select-option value="GuLou">鼓楼</a-select-option>
                        <a-select-option value="XinJie">新街口</a-select-option>
                        <a-select-option value="GuanQianStreet">观前街</a-select-option>
                        <a-select-option value="JinJiHu">金鸡湖</a-select-option>
                        <a-select-option value="WangFuJing">王府井</a-select-option>
                        <a-select-option value="NanLuoGuXiang">南锣鼓巷</a-select-option>
                        <a-select-option value="TianAnMen">天安门</a-select-option>
                        <a-select-option value="SanLiTun">三里屯</a-select-option>
                        <a-select-option value="JingAnSi">静安寺</a-select-option>
                        <a-select-option value="WaiTan">外滩</a-select-option>
                        <a-select-option value="LuJiaZui">陆家嘴</a-select-option>
                        <a-select-option value="HuaQiangBei">华强北</a-select-option>
                        <a-select-option value="SanGuoCheng">三国城</a-select-option>
                        <a-select-option value="LingShanDaFo">灵山大佛</a-select-option>
                        <a-select-option value="YueLuShuYuan">岳麓书院</a-select-option>
                        <a-select-option value="TianXinGe">天心阁</a-select-option>
                        <a-select-option value="GuangZhouTa">广州塔</a-select-option>
                    </a-select>
                    <span v-else>{{ bizRegion }}</span>
                </a-form-item>

                <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="酒店地址">
                    <a-input
                            placeholder="请填写酒店地址"
                            v-decorator="['address', { rules: [{ required: false, message: '请填写酒店地址' }], initialValue: hotelAddress }]"
                            v-if="modify"
                    />
                    <span v-else>{{ hotelAddress }}</span>
                </a-form-item>

                <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="酒店简介">
                    <a-input
                            :rows="4"
                            placeholder="请填写酒店简介"
                            type="textarea"
                            v-decorator="['description', { rules: [{ required: false, message: '请填写酒店简介' }], initialValue: hotelDescription }]"
                            v-if="modify"
                    />
                    <span v-else>{{ hotelDescription }}</span>
                </a-form-item>

                <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="酒店星级">
                    <a-select
                            v-decorator="['hotelStar',{ rules: [{ required: false, message: '请选择酒店星级' }], initialValue: hotelStarEnum}]"
                            v-if="modify"
                    >
                        <a-select-option value="Three">三星级</a-select-option>
                        <a-select-option value="Four">四星级</a-select-option>
                        <a-select-option value="Five">五星级</a-select-option>
                    </a-select>
                    <span v-else>{{ hotelStar }}</span>
                </a-form-item>

                <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }" label="联系电话">
                    <a-input
                            v-decorator="['phoneNum', { rules: [{ required: false, message: '请输入手机号' }, { validator: this.handlePhoneNumber }], initialValue: phoneNum}]"
                            v-if="modify"
                    />
                    <span v-else>{{ phoneNum }}</span>
                </a-form-item>

                <!-- 按钮 -->
                <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                    <a-button @click="saveModify" icon="upload" style="border-radius: 20px" type="primary">
                        保存
                    </a-button>
                    <a-button @click="cancelModify" icon="close-circle" style="margin-left: 30px; border-radius: 20px"
                              type="default">
                        取消
                    </a-button>
                </a-form-item>
                <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                    <a-button @click="modifyInfo" icon="form" style="border-radius: 20px" type="primary">
                        修改信息
                    </a-button>
                </a-form-item>
            </a-form>
        </div>
    </div>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex';
    import {message} from 'ant-design-vue'

    export default {
        name: "ModifyHotelInfo",
        data() {
            return {
                modify: false,
                form: this.$form.createForm(this, {name: 'modifyHotelInfo'}),
            }
        },
        computed: {
            ...mapGetters([
                'hotelInfo',
            ]),
            hotelName() {
                return this.hotelInfo.name
            },
            bizRegion() {
                return this.hotelInfo.bizRegion
            },
            hotelAddress() {
                return this.hotelInfo.address
            },
            hotelDescription() {
                return this.hotelInfo.description
            },
            hotelStar() {
                return this.hotelInfo.hotelStar
            },
            hotelStarEnum() {
                const v = this.hotelInfo.hotelStar
                if (v==='三星级') return 'Three'
                if (v==='四星级') return 'Four'
                return 'Five'
            },
            phoneNum() {
                return this.hotelInfo.phoneNum
            },
        },
        methods: {
            ...mapActions([
                'updateHotelInfo'
            ]),
            modifyInfo() {
                this.modify = true
            },
            handlePhoneNumber(rule, value, callback) {
                const re = /1\d{10}/;
                if (re.test(value)) {
                    callback();
                } else {
                    if (value === '' || value.length===8) {
                        callback()
                    } else {
                        callback(new Error('请输入有效联系人手机号或座机号'));
                    }
                }
                callback()
            },
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            name: this.hotelInfo.name,
                            bizRegion: this.form.getFieldValue('bizRegion'),
                            address: this.form.getFieldValue('address'),
                            hotelStar: this.form.getFieldValue('hotelStar'),
                            description: this.form.getFieldValue('description'),
                            phoneNum: this.form.getFieldValue('phoneNum'),
                        }
                        this.updateHotelInfo(data)
                        this.modify = false
                        console.log(data)
                    } else {
                        message.error("请输入正确的信息")
                    }
                })
            },
            cancelModify() {
                message.info('取消修改')
                this.modify = false
            }
        }
    }
</script>

<style lang="less" scoped>
    .hotel-info {
        width: 100%;

        .info {
            margin-top: 20px;
            float: left;
            width: 100%;
        }
    }
</style>
