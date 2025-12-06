import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import * as path from 'path'  // 正确导入 path 模块

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  build: {
    outDir: path.resolve(__dirname, '../static'), // 指定输出目录
    emptyOutDir: true,  // 打包时清空输出目录
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  //下面是配置代理的所有内容
  //这里用server,和vue之前配置的有所不同
  server: {
    //方式二:设置多个代理
    proxy: {
      '^/api': {
        //target是代理的目标路径
        target: "http://localhost:5100",
        changeOrigin: true, //必须要开启跨域
        //pathRewrite重写请求的路径,
        rewrite: (path) => path.replace(/\/api/, ""), // 路径重写
      },
    },
  },
})
