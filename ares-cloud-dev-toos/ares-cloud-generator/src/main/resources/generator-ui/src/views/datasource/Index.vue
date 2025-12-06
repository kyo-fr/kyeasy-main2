<script setup>
import { ref,reactive, onMounted } from 'vue'
import {ElMessage} from 'element-plus'
import { getDataSource, addDataSource, updateDataSource, delDataSource, testDataSource } from '@/api/index'

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
  dbType: undefined,
  connName: undefined,
  connUrl: undefined,
  username: undefined,
  password: undefined,
}
const currentData = ref({ ...defData })
onMounted(() => {
  loadData()
})
const loadData = () => {
  let queryData = { page: currentPage.value, limit: pageSize.value }
  if (searchVal.value) {
    queryData = { ...queryData, connName: searchVal.value }
  }
  getDataSource(queryData).then((res) => {
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
  currentData.value = { ...defData }
  visible.value = true
}
const edit = (row) => {
  currentData.value = { ...row }
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
        updateDataSource(currentData.value).then(() => {
          query()
        }).catch(() => {
          ElMessage.error('更新失败')
        })
      } else {
        addDataSource(currentData.value).then(() => {
          query()
        }).catch(() => {
          ElMessage.error('新增失败')
        })
      }
    }
  })
}

const del = (row) => {
  delDataSource([row.id]).then(() => {
    ElMessage.success('删除成功')
    query()
  }).catch(() => {
    ElMessage.error('删除失败')
  })
}
const test = (row) => {
  testDataSource(row.id).then(() => {
    ElMessage.success('连接成功')
  }).catch(() => {
    ElMessage.error('连接失败')
  })
}
const rules = reactive({
  dbType: [
    {
      required: true,
      message: '请输入连接类型',
      trigger: 'blur',
    },
  ],
  connName: [
    {
      required: true,
      message: '请输入连接名称',
      trigger: 'blur',
    },
  ],
  connUrl: [
    {
      required: true,
      message: '请输入url',
      trigger: 'blur',
    },
  ],
  username: [
    {
      required: true,
      message: '请输入用户名',
      trigger: 'blur',
    },
  ],
  password: [
    {
      required: true,
      message: '请输入密码',
      trigger: 'blur',
    },
  ],
})
</script>

<template>
  <div class="page-contenter">
    <div class="table-header">
      <div class="search-input">
        <el-input v-model="searchVal" placeholder="连接名" clearable @clear="query" />
      </div>
      <el-button type="info" @click="query">查询</el-button>
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <div class="table-contenter">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="connName" label="连接名" align="center" width="200" />
        <el-table-column prop="dbType" label="数据库类型" align="center" width="180" />
        <el-table-column prop="connUrl" label="连接url" align="center" />
        <el-table-column prop="username" label="用户名" align="center" />
        <el-table-column prop="password" label="密码" align="center" />
        <el-table-column fixed="right" label="操作" width="180" align="center">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="edit(scope.row)">编辑</el-button>
            <el-button link type="success" size="small" @click="test(scope.row)">测试链接</el-button>
            <el-button link type="danger" size="small" @click="del(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-contenter">
        <el-pagination v-model:current-page="currentPage1" :page-size="pageSize" :small="small" :background="false"
          layout="total, prev, pager, next" :total="totalVal" @current-change="handleCurrentChange" />
      </div>
    </div>
    <el-dialog v-model="visible" :title="currentData.id ? '修改' : '新增'">
      <el-form :model="currentData" :rules="rules" label-width="120px" ref="ruleFormRef">
        <el-form-item label="连接名" prop="connName">
          <el-input v-model="currentData.connName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="数据库类型" prop="dbType">
          <el-select v-model="currentData.dbType">
            <el-option label="MySQL" value="MySQL" />
            <el-option label="Oracle" value="Oracle" />
            <el-option label="PostgreSQL" value="PostgreSQL" />
            <el-option label="SQLServer" value="SQLServer" />
          </el-select>
        </el-form-item>
        <el-form-item label="连接url" prop="connUrl">
          <el-input v-model="currentData.connUrl" autocomplete="off" />
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="currentData.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="currentData.password" autocomplete="off" />
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
