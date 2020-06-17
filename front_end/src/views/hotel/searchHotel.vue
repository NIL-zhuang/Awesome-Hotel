<template>
    <div class="box">

        <div class="searchbox">
            <a-form :form="form">
                <a-form-item label="时间" v-bind="formItemLayout">
                    <a-range-picker
                            format="YYYY-MM-DD"
                            v-decorator="['date', { rules: [{ required: true, message: '请选择入住时间' }], initialValue: dateRange}]"
                    />
                </a-form-item>
                <a-form-item label="地址" v-bind="formItemLayout">
                    <a-input
                            class="searchInput" placeholder="请输入地址" type="text"
                            v-decorator="['address']"
                    />
                </a-form-item>
                <a-form-item label="商圈" v-bind="formItemLayout">
                    <a-input
                            class="searchInput" placeholder="请输入商圈" type="text"
                            v-decorator="['bizRegion']"
                    />
                </a-form-item>
                <a-form-item label="关键词" v-bind="formItemLayout">
                    <a-select
                            mode="multiple"
                            placeholder="请选择酒店关键词"
                            v-decorator="['tags']"
                    >
                        <a-select-option :key="tag" v-for="tag in hotelTags">
                            {{ tag }}
                        </a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="价位上限 ¥" v-bind="formItemLayout">
                    <a-row>
                        <a-col :span="16">
                            <a-slider
                                    :max="2000" :min="1" @change="handleValueChange" v-model="value"
                            />
                        </a-col>
                        <a-col :span="4">
                            <a-input-number
                                    :max="2000" :min="1" style="margin-left: 16px" v-model="value"
                            />
                        </a-col>
                    </a-row>
                </a-form-item>
                <a-form-item label="评分下限" v-bind="formItemLayout">
                    <a-row>
                        <a-col :span="16">
                            <a-slider
                                    :max="5" :min="0" :step="0.1" @change="handleRateChange" v-model="rate"
                            />
                        </a-col>
                        <a-col :span="4">
                            <a-input-number
                                    :max="5" :min="0" :step="0.1" style="margin-left: 16px" v-model="rate"
                            />
                        </a-col>
                    </a-row>
                </a-form-item>
                <a-form-item label="酒店星级" v-bind="formItemLayout">
                    <a-checkbox :checked="checkAll" :indeterminate="indeterminate" @change="onCheckAllChange">
                        全选
                    </a-checkbox>
                    <a-checkbox-group :options="hotelStarOptions" @change="onChange"
                                      v-decorator="['stars',{initialValue: defaultCheckedList}]"
                    />
                </a-form-item>
                <a-form-item v-bind="formItemLayout">
                    <a-button @click="search" class="searchButton" icon="search" type="primary" value="搜索">
                        搜索酒店
                    </a-button>
                </a-form-item>
            </a-form>
        </div>

        <div class="searchresult">
            <div class="card-wrapper">
                <HotelCard :hotel="item" :key="item.index" @click.native="jumpToDetails(item.id)"
                           v-for="item in searchList"></HotelCard>
            </div>
        </div>
    </div>
</template>

<script>
    import moment from "moment";
    import {mapActions, mapGetters, mapMutations} from "vuex";
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
                dateRange: [moment(), moment().add(1, 'd')],
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
        destroyed() {
            this.set_searchList([]) //离开页面时清除搜索的结果
        },
        methods: {
            ...mapActions([
                'searchHotel'
            ]),
            ...mapMutations([
                'set_searchList'
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
                            address: this.form.getFieldValue('address') ? this.form.getFieldValue('address') : '',
                            bizRegion: this.form.getFieldValue('bizRegion') ? this.form.getFieldValue('bizRegion') : '',
                            keyWords: this.form.getFieldValue('tags') ? this.form.getFieldValue('tags') : [],
                            maxPrice: this.value,
                            minScore: this.rate,
                            hotelStar: this.form.getFieldValue('stars'),
                        }
                        this.searchHotel(data)
                    }
                });
            },
        }
    }
</script>

<style lang="less" scoped>
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

    .searchresult {
        width: 100%;
        padding: 50px 0;
        text-align: center;

        .card-wrapper {
            display: flex;
            justify-content: flex-start;
            flex-wrap: wrap;
            min-height: 500px;
        }
    }
</style>