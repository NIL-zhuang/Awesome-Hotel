<!-- created by glh 2020-05-23 -->
<!-- 展示网站的销售业绩，作为网站营销人员的首页 -->
<template>
    <div>
        <div id="line_div" ref="chart" style="width: 1200px; height:600px; margin-top: 40px"/>
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

    import {mapActions, mapGetters, mapMutations} from "vuex";

    export default {
        name: 'SalesHomePage',
        computed: {
            ...mapGetters([
                'allOrderInMonth',
                'allOrderNumInMonth',
                'allOrderValueInMonth'
            ])
        },
        async mounted() {
            await this.getAllOrdersInMonth()
            this.drawLine()
        },
        methods: {
            ...mapActions([
                'getAllOrdersInMonth',
            ]),
            ...mapMutations([]),
            drawLine() {
                const line_div = this.$refs.chart
                echarts.init(line_div).setOption({
                    title: {text: '网站近30日交易情况（横轴下标为第n天，第31天表示当天）'},
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
                        name: '天',
                        data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                            21, 22, 23, 24, 25, 26, 27, 28, 29, 30, '31(今天)'
                        ],
                        nameTextStyle: {
                            fontSize: 14,
                            fontWeight: 'bolder'
                        }
                    },
                    yAxis: [
                        {
                            name: '执行订单数', nameTextStyle: {
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
                        data: this.allOrderNumInMonth,
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
                        data: this.allOrderValueInMonth,
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
</script>

<style scoped>

</style>