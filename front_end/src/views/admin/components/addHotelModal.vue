<template>
    <a-modal
        :visible="addHotelModalVisible"
        title="添加酒店"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="酒店名">
                <a-input
                    placeholder="请填写酒店名称"
                    v-decorator="['hotelName', { rules: [{ required: true, message: '请填写酒店名称' }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店商圈" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'bizRegion',
                    { rules: [{ required: true, message: '请选择酒店商圈' }] }]"
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
            </a-form-item>
            <a-form-item label="酒店地址" v-bind="formItemLayout">
                <a-input
                    placeholder="请填写酒店地址"
                    v-decorator="['address', { rules: [{ required: true, message: '请填写酒店地址' }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店星级" v-bind="formItemLayout">
                <a-select
                    v-decorator="[
                    'hotelStar',
                    { rules: [{ required: true, message: '请选择酒店星级' }] }]"
                    @change="changeStar"
                >
                  <a-select-option value="Three">三星级</a-select-option>
                  <a-select-option value="Four">四星级</a-select-option>
                  <a-select-option value="Five">五星级</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="联系电话" v-bind="formItemLayout">
                <a-input
                    placeholder="请填写手机号"
                    v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }, { validator: this.handlePhoneNumber }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店简介" v-bind="formItemLayout">
                <a-input
                    type="textarea"
                    :rows="4"
                    placeholder="请填写酒店简介"
                    v-decorator="['description', { rules: [{ required: true, message: '请填写酒店简介' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import { message } from 'ant-design-vue'
export default {
    name: 'addHotelModal',
    data() {
        return {
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
            },
        }
    },
    computed: {
        ...mapGetters([
            'addHotelModalVisible'
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'addHotelModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addHotelParams',
            'set_addHotelModalVisible',
        ]),
        ...mapActions([
            'addHotel',
        ]),
        cancel() {
            this.set_addHotelModalVisible(false)
        },
        changeStar(v){

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
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        name: this.form.getFieldValue('hotelName'),
                        description: this.form.getFieldValue('description'),
                        bizRegion: this.form.getFieldValue('bizRegion'),
                        address: this.form.getFieldValue('address'),
                        phoneNum: this.form.getFieldValue('phoneNumber'),
                        hotelStar: this.form.getFieldValue('hotelStar'),
                    }
                    this.set_addHotelParams(data)
                    this.addHotel()
                    this.form.setFieldsValue({
                        hotelName: '',
                        description: '',
                        bizRegion: '',
                        address: '',
                        phoneNumber: '',
                        hotelStar: ''
                    })
                } else {
                    message.error('请填写正确的信息')
                }
            });
        },
    }
}
</script>
