<script setup>
import { ref, onMounted } from 'vue'
import { getAllList, getDataSourceTableList,importTable } from '@/api/index'
import { ElMessage } from 'element-plus';
const props = defineProps({
    visible: {
        type: Boolean,
        required: false
    },
})
const emit = defineEmits(['ok', 'cancel'])
const dialogTableVisible = ref(props.visible)
const options = ref([])
const tableData = ref([])
const selectDatasource = ref()
const currentView = ref(1)
const multipleTableRef = ref()
const multipleSelection = ref([])
onMounted(() => {
    loadData()
})
const loadData = () => {
    getAllList().then(res => {
        options.value = res
    }).catch(() => {
        ElMessage.error('获取数据源失败')
    })
}


const getTableList = () => {
    if (!selectDatasource.value) {
        ElMessage.error('请选择数据源')
        return
    }
    getDataSourceTableList(selectDatasource.value).then(res => {
        tableData.value = res
    }).catch(() => {
        ElMessage.error('获取数据源表失败')
    })
}
const ok = () => {
    if (currentView.value === 1) {
        getTableList()
        currentView.value = 2
    }else{
        importModel()
    }
}

const importModel = () => {
    if (multipleSelection.value.length === 0) {
        ElMessage.error('请选择要导入的表')
        return
    }
    let tableNamdes = multipleSelection.value.map(item => item.tableName)
    importTable(selectDatasource.value,tableNamdes).then(res => {
        ElMessage.success('导入成功')
        emit('ok')
    }).catch(() => {
        ElMessage.error('导入失败')
    })
    currentView.value = 1
}
const cancel = () => {
    emit('cancel')
    currentView.value = 1
}
const handleSelectionChange = (val) => {
  multipleSelection.value = val
}
</script>

<template>
    <div>
        <!--数据源选择器-->
        <el-dialog v-model="props.visible" title="数据源选择">
            <el-select v-model="selectDatasource" class="m-2" placeholder="Select" size="large" v-if="currentView === 1">
                <el-option v-for="item in options" :key="item.id" :label="item.connName" :value="item.id" />
            </el-select>
            <div v-else>
                <el-table ref="multipleTableRef" :data="tableData" style="width: 100%"
                    @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55" />
                    <el-table-column property="tableName" label="表名" width="220" />
                    <el-table-column property="tableComment" label="描述" />
                </el-table>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="cancel">取消</el-button>
                    <el-button type="primary" @click="ok">
                        确认
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
