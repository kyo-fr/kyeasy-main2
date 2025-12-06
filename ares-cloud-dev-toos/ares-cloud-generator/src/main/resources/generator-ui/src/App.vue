<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <el-scrollbar>
        <div style="line-height: 60px;font-weight: 700;font-size: 19px; height: 60px; color:#fff;">
          <span>Ares-boot Code Gen</span>
        </div>
        <el-menu default-active="1" :default-openeds="['1']" class="el-menu-vertical" :router="true">
          <el-sub-menu index="1">
            <template #title>
              <el-icon>
                <Aim />
              </el-icon>
              <span>代码生成器</span>
            </template>
            <el-menu-item index="/">
              <template #title>
                <el-icon><HelpFilled /></el-icon>
                <span>项目</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/datasouce">
              <template #title>
                <el-icon><DataAnalysis /></el-icon>
                <span>数据源</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/baseClass">
              <template #title>
                <el-icon><Fold /></el-icon>
                <span>基类管理</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/field-type">
              <template #title>
                <el-icon><Tickets /></el-icon>
                <span>字段类型映射</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/model">
              <template #title>
                <el-icon><FolderChecked /></el-icon>
                <span>代码生成</span>
              </template>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import { Menu as IconMenu, Message, Setting } from '@element-plus/icons-vue'

const item = {
  date: '2016-05-02',
  name: 'Tom',
  address: 'No. 189, Grove St, Los Angeles',
}
const tableData = ref(Array.from({ length: 20 }).fill(item))
</script>

<style scoped>
.layout-container .el-header {
  position: relative;
  background-color: var(--vt-c-divider-dark-2);
  color: var(--el-text-color-primary);
}

.layout-container .el-aside {
  color: var(--el-text-color-primary);
  background: var(--vt-c-divider-dark-2);
}

.layout-container .el-menu {
  border-right: none;
}

.layout-container .el-main {
  padding: 10px;
  background-color: black;
}

.layout-container .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}

.layout-container {
  min-height: 100vh;
}</style>
