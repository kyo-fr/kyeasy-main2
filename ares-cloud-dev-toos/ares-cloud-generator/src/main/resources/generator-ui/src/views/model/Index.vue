<script setup>
import { ref, onMounted } from 'vue'
import { getTable,delTable,GenCode } from '@/api/index'
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
import Import from './components/Import.vue'
import CodeGenerator from "./components/CodeGenerator.vue";
const router = useRouter()
const tableData = ref([])
const currentPage = ref(1)
const totalVal = ref(0)
const pageSize = ref(10)
const searchVal = ref('')
const importVisible = ref(false)
const currentData = ref({})
const showCodeGenerator = ref(false)
onMounted(() => {
  loadData()
})
const loadData = () => {
  let queryData = { page: currentPage.value, limit: pageSize.value }
  if (searchVal.value) {
    queryData = { ...queryData, tableName: searchVal.value }
  }
  getTable(queryData).then((res) => {
    let { total, list } = res
    if (list && list.length > 0) {
      tableData.value = [...list]
    } else {
      tableData.value = []
    }
    totalVal.value = total
  })
}
const handleCurrentChange = (value) => {
  currentPage.value = value
  loadData()
}
const query = () => {
  currentPage.value = 1
  loadData()
}
const importOk = () => {
  importVisible.value = false
  query()
}
const details = (row) => {
  currentData.value = { ...row }
  router.push({
    path: '/model/details',
    query: {
      id: row.id
    }
  })
}
/**
 * 新增模型
 */
const addOrUpdate = (id) => {
  if (id){
    router.push({
      path: '/model/add_or_update',
      query:{
        id
      }
    })
  }else{
    router.push({
      path: '/model/add_or_update',
    })
  }
}
const del = (row) => {
  delTable([row.id]).then(()=>{
    ElMessage.success('删除成功')
    query()
  }).catch(()=>{
    ElMessage.error('删除失败')
  })
}

const genCode = (row) => {
  currentData.value = row
  showCodeGenerator.value = true
  // GenCode(row.id).then(()=>{
  //   ElMessage.success('生成成功')
  // }).catch(()=>{
  //   ElMessage.error('生成失败')
  // })
}
</script>

<template>
  <div class="page-contenter">
    <div class="table-header">
      <div class="search-input">
        <el-input v-model="searchVal" placeholder="表名" clearable @clear="query" />
      </div>
      <el-button type="info" @click="query">查询</el-button>
      <el-button type="success" @click="importVisible = true">导入数据表</el-button>
      <el-button type="primary" @click="addOrUpdate(undefined)">新增</el-button>
    </div>
    <div class="table-contenter">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="tableName" label="表名" align="center" width="200" />
        <el-table-column prop="tableComment" label="表说明" align="center" width="180" />
        <el-table-column prop="className" label="类名" align="center" />
        <el-table-column fixed="right" label="操作" width="220" align="center">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="details(scope.row)">查看</el-button>
            <el-button link type="primary" size="small" @click="addOrUpdate(scope.row.id)">修改</el-button>
            <el-button link type="success" size="small" @click="genCode(scope.row)">生成代码</el-button>
            <el-button link type="danger" size="small" @click="del(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-contenter">
        <el-pagination v-model:current-page="currentPage" :page-size="pageSize" :small="small" :background="false"
          layout="total, prev, pager, next" :total="totalVal" @current-change="handleCurrentChange" />
      </div>
    </div>
    <Import :visible="importVisible" @cancel="importVisible = false" @ok="importOk"/>
    <code-generator  :visibility="showCodeGenerator" :id="currentData.id" @cancel="showCodeGenerator = false"/>
  </div>
</template>
