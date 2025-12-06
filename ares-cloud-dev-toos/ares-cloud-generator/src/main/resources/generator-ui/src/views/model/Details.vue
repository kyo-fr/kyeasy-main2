<script setup>
import { ref, watch,reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { getTableDetails, addField, updateField, delField } from '@/api/index'
import { ElMessage } from 'element-plus';
const router = useRouter()
const route = useRoute()
const modelDetils = ref({})
const visible = ref(false)
const ruleFormRef = ref()
const modelId = ref(route.query.id)
//默认数据
const defData = {
    id: undefined,
    fieldName: undefined,
    fieldType: undefined,
    fieldComment: undefined,
    attrName: undefined,
    attrType: undefined,
    packageName: undefined,
    length:0,
    point:0,
    isNull:false,
    defValue: undefined,
}
const currentData = ref({ ...defData })
onMounted(() => {
    loadData()
})
const loadData = () => {
    console.log(route.query.id)
    getTableDetails(route.query.id).then((res => {
        modelDetils.value = { ...res }
    })).catch(() => {
        ElMessage.error('获取数据失败')
    })
}
const back = () => {
    router.go(-1)
}
const add = () => {
    currentData.value = { ...defData }
    visible.value = true
}
const edit = (row) => {
    currentData.value = { ...row }
    visible.value = true
}
const del = (row) => {
    delField([row.id]).then(() => {
        ElMessage.success('删除成功')
        loadData()
    }).catch(() => {
        ElMessage.error('删除失败')
    })
}
//提交
const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      visible.value = false
      if (currentData.value.id) {
        //修改
        updateField(modelId.value,[currentData.value]).then(()=>{
            loadData()
        }).catch(()=>{
          ElMessage.error('更新失败')
        })
      }else{
        addField(modelId.value,currentData.value).then(()=>{
            loadData()
        }).catch(()=>{
          ElMessage.error('新增失败')
        })
      }
    } 
  })
}
const rules = reactive({
    fieldComment: [
    {
      required: true,
      message: '请输入属性描述',
      trigger: 'blur',
    },
  ],
  fieldName: [
    {
      required: true,
      message: '请输入数据库属性名称',
      trigger: 'blur',
    },
  ],
  fieldType: [
    {
      required: true,
      message: '请输入数据库数据类型',
      trigger: 'blur',
    },
  ],
  attrName: [
    {
      required: true,
      message: '请输入模型属性名称',
      trigger: 'blur',
    },
  ],
  attrType: [
    {
      required: true,
      message: '请输入模型数据类型',
      trigger: 'blur',
    },
  ],
})
</script>

<template>
    <div class="page-contenter">
        <el-page-header @back="back">
            <template #content>
                <span class="text-large font-600 mr-3"> 模型详情 </span>
            </template>
            <el-card class="box-card">
                <template #header>
                    <div class="card-header">
                        <span>详情</span>
                    </div>
                </template>
                <div class="item">
                    <div class="label">描述:</div>
                    <div class="val">{{ modelDetils.tableComment }}</div>
                </div>
                <div class="item">
                    <div class="label">类名:</div>
                    <div class="val">{{ modelDetils.className }}</div>
                </div>
                <div class="item">
                    <div class="label">表名:</div>
                    <div class="val">{{ modelDetils.tableName }}</div>
                </div>
            </el-card>
            <el-card class="box-card">
                <template #header>
                    <div class="card-header">
                        <span>属性</span>
<!--                        <el-button class="button" type="primary" @click="add">新增</el-button>-->
                    </div>
                </template>

                <el-table :data="modelDetils.fieldList" border style="width: 100%">
                    <el-table-column prop="fieldComment" label="属性描述" align="center" width="200" />
                    <el-table-column prop="fieldName" label="属性名称" align="center" width="180" />
                    <el-table-column prop="attrType" label="模型数据类型" align="center" />
                    <el-table-column prop="fieldType" label="数据库类型" align="center" />
                    <el-table-column prop="attrName" label="模型属性" align="center" />
                    <el-table-column prop="primaryPk" label="是否主键" align="center">
                        <template #default="scope">
                            <div style="display: flex; align-items: center">
                                <el-tag v-if="scope.row.primaryPk" class="ml-2" type="success">是</el-tag>
                                <el-tag v-else class="ml-2" type="info">否</el-tag>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="length" label="长度" align="center" />
                    <el-table-column prop="point" label="小数点位数" align="center" />
                    <el-table-column prop="isNull" label="是否能够为空" align="center" />
                    <el-table-column prop="defValue" label="默认值" align="center" />
<!--                    <el-table-column fixed="right" label="操作" width="120" align="center">-->
<!--                        <template #default="scope">-->
<!--                            <el-button link type="primary" size="small" @click="edit(scope.row)">编辑</el-button>-->
<!--                            <el-button link type="danger" size="small" @click="del(scope.row)">删除</el-button>-->
<!--                        </template>-->
<!--                    </el-table-column>-->
                </el-table>
            </el-card>
        </el-page-header>
        <el-dialog v-model="visible" :title="currentData.id ? '修改' : '新增'">
            <el-form :model="currentData" :rules="rules" label-width="120px" ref="ruleFormRef">
                <el-form-item label="属性描述" prop="fieldComment">
                    <el-input v-model="currentData.fieldComment" autocomplete="off" />
                </el-form-item>
                <el-form-item label="数据库属性名称" prop="fieldName">
                    <el-input v-model="currentData.fieldName" autocomplete="off" />
                </el-form-item>
                <el-form-item label="数据库数据类型" prop="fieldType">
                    <el-input v-model="currentData.fieldType" autocomplete="off" />
                </el-form-item>
                <el-form-item label="模型属性名称" prop="attrName">
                    <el-input v-model="currentData.attrName" autocomplete="off" />
                </el-form-item>
                <el-form-item label="模型数据类型" prop="attrType">
                    <el-input v-model="currentData.attrType" autocomplete="off" />
                </el-form-item>
                <el-form-item label="长度" prop="length">
                    <el-input-number v-model="currentData.length" autocomplete="off" />
                </el-form-item>
                <el-form-item label="小数点位数" prop="point">
                    <el-input-number v-model="currentData.point" autocomplete="off" />
                </el-form-item>
                <el-form-item label="是否能够为空1" prop="isNull">
                    <el-input-number v-model="currentData.isNull" autocomplete="off" />
                </el-form-item>
                <el-form-item label="默认值" prop="defValue">
                    <el-input v-model="currentData.defValue" autocomplete="off" />
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
<style scoped>
.box-card {
    margin-bottom: 8px;
}

.item {
    display: flex;
    line-height: 32px;
}

.item .label {
    margin-right: 10px;
    font-weight: 600;
}
.button{
    float: right;
}
</style>
