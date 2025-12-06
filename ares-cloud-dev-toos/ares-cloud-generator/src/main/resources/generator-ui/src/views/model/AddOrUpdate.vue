<script setup>
import {ref, watch, reactive, onMounted} from 'vue'
import {useRouter, useRoute} from 'vue-router'
import {
  getTableDetails,
  addField,
  updateField,
  delField,
  getAllBaseModel,
  addModel,
  updateModel,
  importBaseModelFiled, getAllFiledType, getFiledMapping
} from '@/api/index'
import {ElMessage} from 'element-plus';

const router = useRouter()
const route = useRoute()
const modelDetils = ref({})
const visible = ref(false)
const ruleFormRef = ref()
const modelFormRef = ref()
const modelId = ref(route.query.id)
const baseModels = ref([])
const showImportFiledModel = ref(false)
const baseModelId = ref("")
const allFileType = ref([])
const filedMapping = ref({})
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
const modelRules = reactive({
  tableComment: [
    {
      required: true,
      message: '请输入模型描述',
      trigger: 'blur',
    },
  ],
  className: [
    {
      required: true,
      message: '请输入类名',
      trigger: 'blur',
    },
  ],
  tableName: [
    {
      required: true,
      message: '请输入表名',
      trigger: 'blur',
    },
  ],
  modelType: [
    {
      required: true,
      message: '请选择模型类型',
      trigger: 'blur',
    },
  ],
})
//默认数据
const defData = {
  id: undefined,
  fieldName: undefined,
  fieldType: undefined,
  fieldComment: undefined,
  attrName: undefined,
  attrType: undefined,
  packageName: undefined,
  length: 0,
  point: 0,
  isNull: 1,
  defValue: undefined,
  autoFill:"DEFAULT"
}

const currentData = ref({...defData})

const defModelData = {
  modelType: 2,
}
onMounted(() => {
  loadData(route.query.id)
  loadAllBaseModel()
  loadAllFileType()
  loadFileMapping()
})
/**
 * 加载基础数据
 */
const loadData = (id) => {
  if (id) {
    getTableDetails(id).then((res => {
      modelDetils.value = {...defModelData, ...res}
      modelId.value = res.id
    })).catch(() => {
      ElMessage.error('获取数据失败')
    })
  } else {
    modelDetils.value = {...defModelData}
  }
}
/**
 * 加载基础模型
 */
const loadAllBaseModel = () => {
  getAllBaseModel().then((res) => {
    baseModels.value = res
  }).catch(() => {
    ElMessage.error('获取数据失败')
  })
}
/**
 * 加载所有字段模型
 */
const loadAllFileType = () => {
  getAllFiledType().then((res)=>{
    allFileType.value = res
  }).catch(()=>{
    ElMessage.error('获取数据失败')
  })
}
/**
 * 加载所有字段模型
 */
const loadFileMapping = () => {
  getFiledMapping().then((res)=>{
    filedMapping.value = res
  }).catch(()=>{
    ElMessage.error('获取数据失败')
  })
}
/**
 * 返沪
 */
const back = () => {
  router.go(-1)
}
/**
 * 保存基础模型
 */
const saveModel = async () => {
  if (!modelFormRef.value) {
    return
  }
  await modelFormRef.value.validate((valid, fields) => {
    if (valid) {
      visible.value = false
      if (modelDetils.value.id) {
        //修改
        updateModel(modelDetils.value).then(() => {
          loadData(modelDetils.value.id)
        }).catch(() => {
          ElMessage.error('更新失败')
        })
      } else {
        //新增
        addModel(modelDetils.value).then((res) => {
          loadData(res.id)
        }).catch(() => {
          ElMessage.error('新增失败')
        })
      }
    }
  })
}
/**
 * 新增属性
 */
const add = () => {
  if (!modelDetils.value.id) {
    ElMessage.error('请先保存模型')
    return
  }
  currentData.value = {...defData}
  visible.value = true
}
/**
 * 编辑属性
 * @param row
 */
const edit = (row) => {
  currentData.value = {...row}
  visible.value = true
}
/**
 * 删除属性
 * @param row
 */
const del = (row) => {
  delField([row.id]).then(() => {
    ElMessage.success('删除成功')
    loadData(modelId.value)
  }).catch(() => {
    ElMessage.error('删除失败')
  })
}
/**
 * 导入属性点击
 */
const importFiled = () =>{
  if (!modelDetils.value.id) {
    ElMessage.error('请先保存模型')
    return
  }
  showImportFiledModel.value = true
  baseModelId.value = ""
}
/**
 * 执行导入
 */
const importBaseModelOk = () =>{
  if(!baseModelId.value){
    ElMessage.warning('请先选择要导入的模型')
    return
  }
  importBaseModelFiled(modelDetils.value.id,baseModelId.value).then(()=>{
    loadData(modelDetils.value.id)
    showImportFiledModel.value =false
  }).catch(()=>{
    ElMessage.error('导入属性失败')
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
        updateField(modelId.value, [{...currentData.value,modelId:String(currentData.value.modelId)}]).then(() => {
          loadData(modelId.value)
        }).catch(() => {
          ElMessage.error('更新失败')
        })
      } else {
        addField(modelId.value, currentData.value).then(() => {
          loadData(modelId.value)
        }).catch(() => {
          ElMessage.error('新增失败')
        })
      }
    }
  })
}
/**
 * 类型改变
 * @param val
 */
const fileTypeChange = (val) =>{
  if(filedMapping.value){
    currentData.value.attrType = filedMapping.value[val]
  }else{
    currentData.value.attrType = undefined
  }
}

</script>

<template>
  <div class="page-contenter">
    <el-page-header @back="back">
      <template #content>
        <span class="text-large font-600 mr-3"> {{modelDetils.id ? "修改模型" : "新增模型"}} </span>
      </template>
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>模型详情</span>
          </div>
        </template>
        <el-form :model="modelDetils" :rules="modelRules" label-width="80px" ref="modelFormRef">
          <el-form-item label="描述" prop="tableComment">
            <el-input v-model="modelDetils.tableComment" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="表名" prop="tableName">
            <el-input v-model="modelDetils.tableName" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="类名" prop="className">
            <el-input v-model="modelDetils.className" autocomplete="off"/>
          </el-form-item>
          <el-form-item label="模型类型" prop="modelType">
            <el-select v-model="modelDetils.modelType" placeholder="请选择">
              <el-option :key="1" :label="`基础模型`" :value="1"></el-option>
              <el-option :key="2" :label="`业务模型`" :value="2"></el-option>
            </el-select>
          </el-form-item>
          <div class="form-footer-buts">
            <el-button type="primary" @click="saveModel()">
              保存
            </el-button>
          </div>
        </el-form>
      </el-card>
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>属性</span>
            <el-button-group class="button-group">
              <el-button type="primary" @click="importFiled">导入基础模型</el-button>
              <el-button type="primary" @click="add">新增</el-button>
            </el-button-group>
          </div>
        </template>
        <el-table :data="modelDetils.fieldList" border style="width: 100%">
          <el-table-column prop="fieldComment" label="属性描述" align="center" width="200"/>
          <el-table-column prop="fieldName" label="属性名称" align="center" width="180"/>
          <el-table-column prop="attrType" label="模型数据类型" align="center"/>
          <el-table-column prop="fieldType" label="数据库类型" align="center"/>
          <el-table-column prop="attrName" label="模型属性" align="center"/>
          <el-table-column prop="primaryPk" label="是否主键" align="center">
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <el-tag v-if="scope.row.primaryPk" class="ml-2" type="success">是</el-tag>
                <el-tag v-else class="ml-2" type="info">否</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="length" label="长度" align="center"/>
          <el-table-column prop="point" label="小数点位数" align="center"/>
          <el-table-column prop="isNull" label="是否能够为空" align="center"/>
          <el-table-column prop="defValue" label="默认值" align="center"/>
          <el-table-column fixed="right" label="操作" width="120" align="center">
            <template #default="scope">
              <el-button link type="primary" size="small" @click="edit(scope.row)">编辑</el-button>
              <el-button link type="danger" size="small" @click="del(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-page-header>
    <el-dialog v-model="visible" :title="currentData.id ? '修改' : '新增'">
      <el-form :model="currentData" :rules="rules" label-width="160px" ref="ruleFormRef">
        <el-form-item label="属性描述" prop="fieldComment">
          <el-input v-model="currentData.fieldComment" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="数据库属性名称" prop="fieldName">
          <el-input v-model="currentData.fieldName" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="数据库数据类型" prop="fieldType">
          <el-select v-model="currentData.fieldType" filterable clearable placeholder="请选择数据库数据类型" @change="fileTypeChange">
            <el-option
                v-for="item in allFileType"
                :key="item.id"
                :label="item.columnType"
                :value="item.columnType">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="模型属性名称" prop="attrName">
          <el-input v-model="currentData.attrName" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="模型数据类型" prop="attrType">
          <el-input v-model="currentData.attrType" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="长度" prop="length">
          <el-input-number v-model="currentData.length" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="小数点位数" prop="point">
          <el-input-number v-model="currentData.point" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="是否能够为空" prop="isNull">
          <el-radio-group v-model="currentData.isNull">
            <el-radio :label="1" :value="1">是</el-radio>
            <el-radio :label="2" :value="2">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="填充方式" prop="autoFill">
          <el-radio-group v-model="currentData.autoFill">
            <el-radio label="DEFAULT" value="DEFAULT">不自动填充</el-radio>
            <el-radio label="INSERT" value="INSERT">插入</el-radio>
            <el-radio label="UPDATE" value="UPDATE">更新</el-radio>
            <el-radio label="INSERT_UPDATE" value="INSERT_UPDATE">新增和修改</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="默认值" prop="defValue">
          <el-input v-model="currentData.defValue" autocomplete="off"/>
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
    <el-dialog v-model="showImportFiledModel" title="导入基础模型属性">
      <div style="height: 120px;padding-top: 22px">
        <el-select v-model="baseModelId" placeholder="请选择基础模型">
          <el-option
              v-for="item in baseModels"
              :key="item.id"
              :label="item.tableComment"
              :value="item.id">
          </el-option>
        </el-select>
      </div>
      <template #footer>
        <span class="dialog-footer">
            <el-button @click="showImportFiledModel = false">取消</el-button>
            <el-button type="primary" @click="importBaseModelOk">
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
  margin-bottom: 8px;
}

.item .label {
  margin-right: 10px;
  font-weight: 600;
}

.item .val {
  flex: 1;
}

.button-group {
  float: right;
}

.form-footer-buts {
  display: flex;
  justify-content: end;
  padding: 10px 0;
}
</style>
