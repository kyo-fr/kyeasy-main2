<script setup>
import { ref,reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus';
import { getBaseClass, addBaseClass, updateBaseClass, delBaseClass } from '@/api/index'
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
  code: undefined,
  packageName: undefined,
  fields: undefined,
  remark: undefined,
}
const currentData = ref({ ...defData })
onMounted(() => {
  loadData()
})
const loadData = () => {
  let queryData = { page: currentPage.value, limit: pageSize.value }
  if (searchVal.value) {
    queryData = { ...queryData, code: searchVal.value }
  }
  getBaseClass(queryData).then((res) => {
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
        updateBaseClass(currentData.value).then(() => {
          query()
        }).catch(() => {
          ElMessage.error('更新失败')
        })
      } else {
        addBaseClass(currentData.value).then(() => {
          query()
        }).catch(() => {
          ElMessage.error('新增失败')
        })
      }
    }
  })
}

const del = (row) => {
  delBaseClass([row.id]).then(() => {
    ElMessage.success('删除成功')
    query()
  }).catch(() => {
    ElMessage.error('删除失败')
  })
}
const rules = reactive({
  code: [
    {
      required: true,
      message: '基类名称',
      trigger: 'blur',
    },
  ],
  packageName: [
    {
      required: true,
      message: '请输入包名',
      trigger: 'blur',
    },
  ],
  fields: [
    {
      required: true,
      message: '公共字段',
      trigger: 'blur',
    },
  ]
})
</script>

<template>
  <div class="page-contenter">
    <div class="table-header">
      <div class="search-input">
        <el-input v-model="searchVal" placeholder="基类编辑" clearable @clear="query" />
      </div>
      <el-button type="info" @click="query">查询</el-button>
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <div class="table-contenter">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="code" label="基类名称" align="center" width="200" />
        <el-table-column prop="packageName" label="基类包名" align="center" width="180" />
        <el-table-column prop="fields" label="公共字段" align="center" />
        <el-table-column prop="remark" label="备注" align="center" />
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
        <el-form-item label="基类名称"  prop="code">
          <el-input v-model="currentData.code" autocomplete="off" />
        </el-form-item>
        <el-form-item label="基类包名" prop="packageName">
          <el-input v-model="currentData.packageName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="公共字段" prop="fields">
          <el-input v-model="currentData.fields" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="currentData.remark" autocomplete="off" />
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
