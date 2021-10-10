<template>
    <div>
        <div id="line_div" ref="chart" style="width: 1280px; height:600px; margin-top: 40px"/>
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
    import {mapGetters, mapActions, mapMutations} from "vuex";

    export default {
        name: "ManagerHomePage",
        async mounted() {
            await this.getUserInfo()
            await this.getHotelOrdersInMonth(this.userInfo.hotelID)
            this.drawLine()
        },
        computed: {
            ...mapGetters([
                'userInfo',
                'orderInMonth',
                'orderNumInMonth',
                'orderValueInMonth',
            ])
        },
        methods: {
            ...mapActions([
                'getUserInfo',
                'getHotelOrdersInMonth'
            ]),
            ...mapMutations([
                'set_manageHotelId',
            ]),
            drawLine() {
                const line_div = this.$refs.chart
                echarts.init(line_div).setOption({
                    title: {text: '酒店近30日营收（横轴下标为第n天，第31天表示当天）'},
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
                        // data: [19, 15, 20, 30, 25, 27, 32, 35, 29, 28,
                        //     26, 33, 37, 41, 27, 42, 47, 43, 50, 45,
                        // 46, 33, 43, 44, 49, 41, 38, 37, 29, 36, 39],
                        data: this.orderNumInMonth,
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
                        // data: [3800, 3000, 4000, 6300, 5200, 5600, 6400, 6958, 6000, 5875,
                        // 5700, 6776, 7400, 8200, 5435, 8475, 8800, 8400, 10000, 9296,
                        // 4920, 6900, 8600, 8800, 9800, 8333, 8200, 7900, 6443, 7342, 8198],
                        data: this.orderValueInMonth,
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