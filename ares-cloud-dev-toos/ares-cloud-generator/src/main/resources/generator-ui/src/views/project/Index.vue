<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getProject,addProject,updateProject,delProject } from '@/api/index'
import { ElMessage } from 'element-plus';
const tableData = ref([])
const currentPage = ref(1)
const totalVal = ref(0)
const pageSize = ref(10)
const searchVal = ref('')
const visible = ref(false)
const ruleFormRef = ref()
//默认数据
const defData = {
  id: undefined,
  projectName: undefined,
  projectCode: undefined,
  projectPackage: undefined,
  projectPath: undefined,
}
const currentData = ref({ ...defData })
onMounted(() => {
  loadData()
})
const loadData = () => {
  let queryData = { page: currentPage.value, limit: pageSize.value }
  if (searchVal.value) {
    queryData = { ...queryData, projectName: searchVal.value }
  }
  getProject(queryData).then((res) => {
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

const add = () => {
  currentData.value = {...defData}
  visible.value = true
}
const edit = (row) => {
  currentData.value = {...row}
  visible.value = true
}
//提交
const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      visible.value = false
      if (currentData.value.id) {
        //修改
        updateProject(currentData.value).then(()=>{
          query()
        }).catch(()=>{
          ElMessage.error('更新失败')
        })
      }else{
        addProject(currentData.value).then(()=>{
          query()
        }).catch(()=>{
          ElMessage.error('新增失败')
        })
      }
    } 
  })
}

const del = (row) => {
  delProject([row.id]).then(()=>{
    ElMessage.success('删除成功')
    query()
  }).catch(()=>{
    ElMessage.error('删除失败')
  })
}


const rules = reactive({
  projectName: [
    {
      required: true,
      message: '请输入项目名',
      trigger: 'blur',
    },
  ],
  projectCode: [
    {
      required: true,
      message: '请输入项目编码',
      trigger: 'blur',
    },
  ],
  projectPackage: [
    {
      required: true,
      message: '请输入包名',
      trigger: 'blur',
    },
  ],
  projectPath: [
    {
      required: true,
      message: '请输入项目路径',
      trigger: 'blur',
    },
  ],
})
</script>

<template>
  <div class="page-contenter">
    <div class="table-header">
      <div class="search-input">
        <el-input v-model="searchVal" placeholder="请输入项目名" clearable @clear="query" />
      </div>
      <el-button type="info" @click="query">查询</el-button>
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <div class="table-contenter">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="projectName" label="项目名" align="center" width="200" />
        <el-table-column prop="projectCode" label="项目编码" align="center" width="180" />
        <el-table-column prop="projectPackage" label="包名" align="center" />
        <el-table-column prop="projectPath" label="项目路径" align="center" />
        <el-table-column fixed="right" label="操作" width="120" align="center">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="edit(scope.row)">编辑</el-button>
            <el-button link type="danger" size="small" @click="del(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-contenter">
        <el-pagination v-model:current-page="currentPage1" :page-size="pageSize" :small="small" :background="false"
          layout="total, prev, pager, next" :total="totalVal" @current-change="handleCurrentChange" />
      </div>
    </div>
    <el-dialog v-model="visible" :title="currentData.id?'修改':'新增'">
      <el-form :model="currentData"
       :rules="rules"
       label-width="120px"
       ref="ruleFormRef">
        <el-form-item label="项目名"  prop="projectName">
          <el-input v-model="currentData.projectName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="项目编码" prop="projectCode">
          <el-input v-model="currentData.projectCode" autocomplete="off" />
        </el-form-item>
        <el-form-item label="包名" prop="projectPackage">
          <el-input v-model="currentData.projectPackage" autocomplete="off" />
        </el-form-item>
        <el-form-item label="项目路径" prop="projectPath">
          <el-input v-model="currentData.projectPath" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="visible = false">取消</el-button>
          <el-button type="primary" @click="submitForm(ruleFormRef)">
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
