<template>
    <div class="box">
        <div class="searchtab">
            <div class="searchbox">
                <a-form :form="form">
                    <a-form-item v-bind="formItemLayout" label="时间">
                        <a-range-picker
                                format="YYYY-MM-DD"
                                v-decorator="['date', { rules: [{ required: true, message: '请选择入住时间' }], initialValue: dateRange}]"
                        />
                    </a-form-item>
                    <a-form-item v-bind="formItemLayout" label="地址">
                        <a-input
                                type="text" placeholder="请输入地址" class="searchInput"
                                v-decorator="['address']"
                        />
                    </a-form-item>
                    <a-form-item v-bind="formItemLayout" label="商圈">
                        <a-input
                                type="text" placeholder="请输入商圈" class="searchInput"
                                v-decorator="['bizRegion']"
                        />
                    </a-form-item>
                    <a-form-item v-bind="formItemLayout" label="关键词">
                        <a-select
                                mode="multiple"
                                placeholder="请选择酒店关键词"
                                v-decorator="['tags']"
                        >
                            <a-select-option v-for="tag in hotelTags" :key="tag">
                                {{ tag }}
                            </a-select-option>
                        </a-select>
                    </a-form-item>
                    <a-form-item v-bind="formItemLayout" label="价位上限 ¥">
                        <a-row>
                            <a-col :span="16">
                                <a-slider
                                        :min="1" :max="2000" v-model="value" @change="handleValueChange"
                                />
                            </a-col>
                            <a-col :span="4">
                                <a-input-number
                                        :min="1" :max="2000" v-model="value" style="margin-left: 16px"
                                />
                            </a-col>
                        </a-row>
                    </a-form-item>
                    <a-form-item v-bind="formItemLayout" label="评分下限">
                        <a-row>
                            <a-col :span="16">
                                <a-slider
                                        :min="0" :max="5" :step="0.1" v-model="rate" @change="handleRateChange"
                                />
                            </a-col>
                            <a-col :span="4">
                                <a-input-number
                                        :min="0" :max="5" :step="0.1" v-model="rate" style="margin-left: 16px"
                                />
                            </a-col>
                        </a-row>
                    </a-form-item>
                    <a-form-item v-bind="formItemLayout" label="酒店星级">
                        <a-checkbox :indeterminate="indeterminate" :checked="checkAll" @change="onCheckAllChange">
                            全选
                        </a-checkbox>
                        <a-checkbox-group :options="hotelStarOptions" @change="onChange"
                                          v-decorator="['stars',{initialValue: defaultCheckedList}]"
                        />
                    </a-form-item>
                    <a-form-item v-bind="formItemLayout">
                        <a-button type="primary" icon="search" @click="search" value="搜索" class="searchButton">
                            搜索酒店
                        </a-button>
                    </a-form-item>
                </a-form>
            </div>
<!--            <div class="presentation">-->
<!--                <img class="hotel_img"-->
<!--                     alt="example"-->
<!--                     src="@/assets/cover.jpeg"-->
<!--                     slot="cover"-->
<!--                     referrerPolicy="no-referrer"-->
<!--                />-->
<!--                <h1>这里存放一个表格</h1>-->
<!--            </div>-->
        </div>
        <div class="searchresult">
            <HotelCard :hotel="item" :key="item.index" @click.native="jumpToDetails(item.id)"
                       v-for="item in searchList"></HotelCard>
        </div>
    </div>
</template>

<script>
    import moment from "moment";
    import {mapActions, mapGetters} from "vuex";
    import HotelCard from "./components/hotelCard"

    const hotelStarOptions = ['三星级', '四星级', '五星级']
    const defaultCheckedList = ['三星级', '四星级', '五星级']
    export default {
        name: "searchHotel",
        components: {
            HotelCard,
        },
        computed: {
            ...mapGetters([
                'searchList'
            ])
        },
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: {span: 10},
                        sm: {span: 5},
                    },
                    wrapperCol: {
                        xs: {span: 20},
                        sm: {span: 10},
                    },
                },
                keyword: '',
                value: 1000,
                rate: 3.0,
                checkAll: true,
                indeterminate: false,
                hotelStarOptions,
                defaultCheckedList,
                dateRange: [moment(), moment().add(1,'d')],
                hotelTags: [
                    '温泉',
                    '干净整洁',
                    '便宜',
                    '早餐',
                    '体验很棒',
                    '晚安服务',
                    '酒',
                    '可乐',
                    '安静',
                    '情侣酒店',
                    '浪漫',
                    '安全',
                ]
            }
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'searchTable'});
        },
        methods: {
            ...mapActions([
                'searchHotel'
            ]),
            handleValueChange(value) {
                this.value = value
            },
            handleRateChange(rate) {
                this.rate = rate
            },
            onChange(checkedList) {
                this.indeterminate = !!checkedList.length && checkedList.length < hotelStarOptions.length;
                this.checkAll = checkedList.length === hotelStarOptions.length;
            },
            onCheckAllChange(e) {
                Object.assign(this, {
                    indeterminate: false,
                    checkAll: e.target.checked,
                });
            },
            jumpToDetails(id) {
                this.$router.push({name: 'hotelDetail', params: {hotelId: id}})
            },
            search(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            checkInDate: this.form.getFieldValue('date')[0].format('YYYY-MM-DD'),
                            checkOutDate: this.form.getFieldValue('date')[1].format('YYYY-MM-DD'),
                            address: this.form.getFieldValue('address')?this.form.getFieldValue('address'):'',
                            bizRegion: this.form.getFieldValue('bizRegion')?this.form.getFieldValue('bizRegion'):'',
                            keyWords: this.form.getFieldValue('tags')?this.form.getFieldValue('tags'):[],
                            maxPrice: this.value,
                            minScore: this.rate,
                            hotelStar: this.form.getFieldValue('stars'),
                        }
                        this.searchHotel(data)
                    }
                });
                console.log(this.searchList) // 完成记得删除
            },
        }
    }
</script>

<style scoped lang="less">
    .box {
        display: block;
    }

    .searchtab {
        width: 100%;
        display: flex;
        margin: 10px;
    }

    .searchbox {
        margin-top: 80px;
        width: 100%;
    }

    .searchButton {
        width: 200px;
        text-align: center;
        float: right;
    }

    .presentation {
        display: flex;
    }

    .hotel_img {
        margin: 20px;
        width: 400px;
        height: 600px;
    }

    .searchresult {
        width: 100%;
        margin: 10px;
    }
</style>