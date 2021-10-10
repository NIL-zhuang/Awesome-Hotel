<template>
    <div>
        <a-table
                :columns="columns"
                :data-source="levels"
                style="background-color: white; padding: 10px"
                :rowKey="record => record.level"
                bordered>
            <template slot="title">
                <h3>普通会员</h3>
            </template>
            <template slot="level" slot-scope="text">
                <a-tag color="blue">{{ text }}</a-tag>
            </template>
            <span slot="request" slot-scope="text">
                <a-tag color="pink">{{text}}</a-tag>
            </span>
            <span slot="reduction" slot-scope="text">
                <a-tag color="purple">{{text*100}}%</a-tag>
            </span>
            <template slot="action" slot-scope="record">
                <a-button icon="edit" @click="modifyLevel(record.level, 'Client', record.request, record.reduction)" size="small" type="primary">修改</a-button>
            </template>
        </a-table>
        <br/>
        <br/>
        <a-table
                :columns="columns"
                :data-source="corpLevels"
                :rowKey="record => record.level"
                style="background-color: white; padding: 10px"
                bordered>
            <template slot="title">
                <h3>企业会员</h3>
            </template>
            <template slot="level" slot-scope="text">
                <a-tag color="blue">{{ text }}</a-tag>
            </template>
            <span slot="request" slot-scope="text">
                <a-tag color="pink">{{text}}</a-tag>
            </span>
            <span slot="reduction" slot-scope="text">
                <a-tag color="purple">{{text*100}}%</a-tag>
            </span>
            <template slot="action" slot-scope="record">
                <a-button icon="edit" @click="modifyLevel(record.level, 'Corporation', record.request, record.reduction)" size="small" type="primary">修改</a-button>
            </template>
        </a-table>
        <modify-level-consumption-modal></modify-level-consumption-modal>
    </div>
</template>

<script>
    import {getTheRequestOfLevelAPI} from "../../../api/membership";
    import ModifyLevelConsumptionModal from "./ModifyLevelConsumptionModal";

    const columns = [
        {
            title: '等级',
            dataIndex: 'level',
            scopedSlots: {customRender: 'level'},
        },
        {
            title: '所需消费额',
            dataIndex: 'request',
            scopedSlots: {customRender: 'request'},
        },
        {
            title: '消费减免',
            dataIndex: 'reduction',
            scopedSlots: {customRender: 'reduction'},
        },
        {
            title: '操作',
            scopedSlots: {customRender: 'action'},
        },
    ];
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: "ManageLevel",
        components: {ModifyLevelConsumptionModal},
        data() {
            return {
                columns,
            }

        },
        computed: {
            ...mapGetters([
                'levels',
                'corpLevels',
            ])
        },
        async mounted() {
            this.getClientLevel()
            this.getCorpLevel()
        },
        methods: {
            ...mapMutations([
                'set_levelModifyModalVisible',
                'set_currentLevel',
            ]),
            ...mapActions([
                'getClientLevel',
                'getCorpLevel',
            ]),
            async modifyLevel(level, type, request, reduction) {
                await this.set_currentLevel({
                    level: level,
                    type: type,
                    request: request,
                    reduction: reduction,
                })
                this.set_levelModifyModalVisible(true)
            }
        },

    }
</script>

<style scoped>

</style>
