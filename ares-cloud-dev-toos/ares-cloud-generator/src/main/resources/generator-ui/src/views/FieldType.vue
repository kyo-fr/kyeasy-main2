<script setup>
import {ref,onMounted} from 'vue'
import {getFieldtype} from '@/api/index'
const tableData = ref([])
const currentPage = ref(1)
const totalVal = ref(0)
const pageSize = ref(10)
const searchVal = ref('')
onMounted(()=>{
  loadData()
})
const loadData = () =>{
  let queryData = { page: currentPage.value, limit: pageSize.value }
  if (searchVal.value) {
    queryData = { ...queryData, columnType: searchVal.value }
  }
  getFieldtype(queryData).then((res)=>{
    let {total,list} = res
    if (list && list.length > 0){
      tableData.value = [...list]
    }else{
      tableData.value = []
    }
    totalVal.value = total
  })
}
const query = () => {
  currentPage.value = 1
  loadData()
}
const handleCurrentChange = (value) =>{
 currentPage.value = value
 loadData()
}
</script>

<template>
 <div class="page-contenter">
  <div class="table-header">
      <div class="search-input">
        <el-input v-model="searchVal" placeholder="字段类型" clearable @clear="query" />
      </div>
      <el-button type="info" @click="query">查询</el-button>
    </div>
  <div class="table-contenter">
    <el-table :data="tableData" border style="width: 100%">
    <el-table-column prop="attrType" label="字段类型" align="center" width="180" />
    <el-table-column prop="columnType" label="属性类型" align="center" width="180" />
    <el-table-column prop="packageName" label="属性名" align="center" />
    <el-table-column fixed="right" label="Operations" width="120">
      <template #default>
        <el-button link type="primary" size="small" @click="handleClick"
          >Detail</el-button
        >
        <el-button link type="primary" size="small">Edit</el-button>
      </template>
    </el-table-column>
  </el-table>
  <div class="pagination-contenter">
    <el-pagination
      v-model:current-page="currentPage1"
      :page-size="pageSize"
      :small="small"
      :background="false"
      layout="total, prev, pager, next"
      :total="totalVal"
      @current-change="handleCurrentChange"
    />
  </div>
  </div>
 </div>
</template>
