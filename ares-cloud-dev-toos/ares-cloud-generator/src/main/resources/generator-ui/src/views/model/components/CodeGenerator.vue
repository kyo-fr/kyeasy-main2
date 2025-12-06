<script setup>

// 接收父组件传递的参数
import {onMounted, reactive, ref, watch} from "vue";
import {getTableDetails, listBaseClass, ModelGenCode} from "@/api/index.js";
import {ElMessage} from "element-plus";

const props = defineProps({
  id: {
    type: String
  },
  visibility: {
    type: Boolean,
    required: false
  }
})
// emit 用于向父组件发送事件
const emit = defineEmits(['ok', 'cancel'])
// 默认值
const defValues = {
  templateNames: ['java/Controller.java.ftl', 'java/Service.java.ftl', 'java/ServiceImpl.java.ftl',
    'java/Query.java.ftl', 'java/Entity.java.ftl', 'java/Dto.java.ftl', 'java/Convert.java.ftl', 'java/Repository.java.ftl', 'sql/sql.ftl'],
  apis: ['page', 'details', 'add', 'update', 'del'],
  genType: 'custom',
}
/**
 * 基础数据模型
 */
const entityBaseClass = ref([])
const rules = reactive({
  baseclassId: [
    {
      required: true,
      message: '请选择基础类',
      trigger: 'blur',
    },
  ],
})
/**
 * 表单
 */
const formData = ref({
  id: '',
  author: '',
  email:'',
  backendPath: '',
  className: '',
  baseclassId:'',
  moduleName: '',
  functionName:'',
  packageName: '',
  tableName: '',
  dis: '',
  templateNames: [],
  apis: [],
  genType: '',
  baseApi: '',
  version:'',
})
/**
 * 加载基础类
 */
const loadBaseClass = () => {
  listBaseClass().then((res)=>{
    entityBaseClass.value = res
  }).catch((err)=>{
    ElMessage.error('加载基础类失败')
  })
}
/**
 * 加载模型
 * @param id
 */
const loadModel = (id) => {
  if (id) {
    getTableDetails(id).then((res) => {
      formData.value = {
        ...formData.value,
        ...defValues,
        id: res.id,
        author: res.author,
        email:res.email,
        backendPath: res.backendPath,
        className: res.className,
        moduleName: res.moduleName,
        packageName: res.packageName,
        tableName: res.tableName,
        dis: res.tableComment,
        version:res.version,
        functionName:res.functionName,
        baseclassId: res.baseclassId,
        baseApi: `/api/${res.moduleName}/${res.functionName}`
      }
    }).catch((err) => {
      ElMessage.error('加载模型失败')
    })
  }
}
onMounted(()=>{
  loadBaseClass()
})
//模型变化
watch(
    () => props.id,
    (mewVal) => {
      loadModel(mewVal)
    },
    {immediate: true, deep: true}
);
/**
 * 关闭窗口
 */
const closeDialog = () => {
  emit('cancel')  // 将 dialogVisible 设置为 false，关闭对话框
}
/**
 * 提交
 */
const onSubmit = () => {
  ModelGenCode(formData.value).then(()=>{
    ElMessage.success("代码生成成功")
  }).catch(()=>{
    ElMessage.error("代码生成失败")
  })
}
</script>

<template>
  <el-dialog v-model="props.visibility" title="数据源选择" @close="closeDialog" width="780px" top="10px">
    <div>
      <el-card class="box-card">
        <div class="card-body">
          <el-form ref="form" :model="formData" :rules="rules" label-width="100px">
            <el-divider content-position="left">基础信息</el-divider>
            <el-form-item label="表名">
              <el-input v-model="formData.tableName" disabled></el-input>
            </el-form-item>
            <el-form-item label="类名">
              <el-input v-model="formData.className"></el-input>
            </el-form-item>
            <el-form-item label="作者">
              <el-input v-model="formData.author"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="formData.email"></el-input>
            </el-form-item>
            <el-form-item label="说明">
              <el-input v-model="formData.dis" ></el-input>
            </el-form-item>
            <el-divider content-position="left">代码属性</el-divider>
            <el-form-item label="包名">
              <el-input v-model="formData.packageName" ></el-input>
            </el-form-item>
            <el-form-item label="模块名称">
              <el-input v-model="formData.moduleName" ></el-input>
            </el-form-item>
            <el-form-item label="功能名称">
              <el-input v-model="formData.functionName" ></el-input>
            </el-form-item>
            <el-form-item label="实体基础类" prop="baseclassId">
              <el-select v-model="formData.baseclassId" placeholder="请选择">
                <el-option
                    v-for="item in entityBaseClass"
                    :key="item.id"
                    :label="`${item.packageName}.${item.code}`"
                    :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="生成文件类">
              <el-checkbox-group v-model="formData.templateNames">
                <el-checkbox label="java/Controller.java.ftl" name="java/Controller.java.ftl">控制器</el-checkbox>
                <el-checkbox label="java/Service.java.ftl" name="java/Service.java.ftl">服务接口</el-checkbox>
                <el-checkbox label="java/ServiceImpl.java.ftl" name="java/ServiceImpl.java.ftl">接口实现</el-checkbox>
                <el-checkbox label="java/Query.java.ftl" name="java/Query.java.ftl">查询模型</el-checkbox>
                <el-checkbox label="java/Entity.java.ftl" name="java/Entity.java.ftl">实体</el-checkbox>
                <el-checkbox label="java/Dto.java.ftl" name="java/Dto.java.ftl">Dto</el-checkbox>
                <el-checkbox label="java/Convert.java.ftl" name="java/Convert.java.ftl">实体和Dto转换器</el-checkbox>
                <el-checkbox label="java/Repository.java.ftl" name="java/Repository.java.ftl">Repository</el-checkbox>
                <el-checkbox label="sql/sql.ftl" name="sql/sql.ftl">建表sql</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="控制器接口">
              <el-checkbox-group v-model="formData.apis">
                <el-checkbox label="page" name="page">分页</el-checkbox>
                <el-checkbox label="all" name="all">获取所有</el-checkbox>
                <el-checkbox label="details" name="details">详情</el-checkbox>
                <el-checkbox label="add" name="add">新增</el-checkbox>
                <el-checkbox label="update" name="update">修改</el-checkbox>
                <el-checkbox label="del" name="del">根据主键删除</el-checkbox>
                <el-checkbox label="batchDel" name="batchDel">根据主键批量删除</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="基础Api地址">
              <el-input v-model="formData.baseApi" ></el-input>
            </el-form-item>
            <el-form-item label="版本">
              <el-input v-model="formData.version" ></el-input>
            </el-form-item>
            <el-form-item label="生成方式">
              <el-radio-group v-model="formData.genType">
                <el-radio label="zip">zip压缩文件</el-radio>
                <el-radio label="custom">自定义路径</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item v-if="formData.genType === 'custom'" label="自定义路径">
              <el-input v-model="formData.backendPath" ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">立即创建</el-button>
              <el-button @click="closeDialog">取消</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
    </div>
  </el-dialog>
</template>

<style scoped>

</style>