<template>
    <div>
        <div id="line_div" ref="chart" style="width: 1200px; height:600px; margin-top: 30px">

        </div>
        <p>展示酒店收到的订单</p>
    </div>
</template>

<script>
    import * as echarts from 'echarts/lib/echarts'
    // 引入折线图。
    import 'echarts/lib/chart/line'
    // 引入提示框组件、标题组件、工具箱组件。
    import 'echarts/lib/component/tooltip'
    import 'echarts/lib/component/title'
    import 'echarts/lib/component/toolbox'
    import 'echarts/lib/component/legend'
    import 'echarts/lib/chart/effectScatter'
    import {mapGetters} from "vuex";
    export default {
        name: "ManagerHomePage",
        async mounted() {
            this.drawLine()
        },
        computed: {
            ...mapGetters([
                'userInfo',
            ])
        },
        methods: {
            drawLine() {
                if (this.userInfo.userType === 'HotelManager' || this.userInfo.userType === 'SalesPerson') {
                    const line_div = this.$refs.chart
                    echarts.init(line_div).setOption({
                        title: {text: '酒店近七日营收'},
                        tooltip: {},
                        legend: {
                            data: ['订单', '收入']
                        },
                        toolbox: {
                            feature: {
                                dataView: {},
                                saveAsImage: {
                                    pixelRatio: 2
                                },
                            }
                        },
                        xAxis: {
                            name: '时间',
                            data: ['第一天', '第二天', '第三天', '第四天', '第五天', '第六天', '第七天'],
                            nameTextStyle: {
                                fontSize: 14,
                                fontWeight: 'bolder'
                            }
                        },
                        yAxis: [
                            {
                                name: '订单数', nameTextStyle: {
                                    fontSize: 14,
                                    fontWeight: 'bolder'
                                }
                            },
                            {
                                name: '收入（单位：元）', nameTextStyle: {
                                    fontSize: 14,
                                    fontWeight: 'bolder'
                                }
                            }
                        ],
                        series: [{
                            name: '订单',
                            type: 'line',
                            smooth: true,
                            data: [5, 20, 36, 10, 20, 25, 30],
                            yAxisIndex: 0,
                            emphasis: {
                                itemStyle: {
                                    color: 'red'
                                },
                                label: {
                                    show: true,
                                    formatter: '当日订单'
                                }
                            },
                        }, {
                            name: '收入',
                            type: 'line',
                            smooth: true,
                            data: [550, 300, 400, 700, 600, 500, 700],
                            yAxisIndex: 1,
                            emphasis: {
                                itemStyle: {
                                    color: 'white'
                                },
                                label: {
                                    show: true,
                                    formatter: '当日收入'
                                }
                            },
                        }]
                    })
                }
            },
        }
    }
</script>

<style scoped>

</style>