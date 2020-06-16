<template>
    <div>
        <a-table :columns="columns" :data-source="levelConsumption" bordered>
            <template slot="level" slot-scope="text">
                {{ text }}
            </template>
            <template slot="action" slot-scope="record">
                <a-button type="primary" size="small" @click="modifyLevel(record.level)">修改</a-button>
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
            key: 'level',
            scopedSlots: { customRender: 'level' },
        },
        {
            title: '所需消费额',
            dataIndex: 'consumption',
            scopedSlots: { customRender: 'consumption' },
        },
        {
            title: '操作',
            scopedSlots: { customRender: 'action' },
        },
    ];
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "ManageLevel",
        components: {ModifyLevelConsumptionModal},
        data () {
            return {
                columns,
            }

        },
        computed: {
            ...mapGetters([
                'levelConsumption',
            ])
        },
        async mounted() {
            this.getTheRequestOfLevel()
        },
        methods: {
            ...mapMutations([
               'set_levelModifyModalVisible',
                'set_currentLevel',
            ]),
            ...mapActions([
                'getTheRequestOfLevel'
            ]),
            modifyLevel(level) {
                this.set_levelModifyModalVisible(true)
                this.set_currentLevel(level)
            }
        },

    }
</script>

<style scoped>

</style>
