import { createRouter, createWebHistory } from 'vue-router'
import Project from '../views/project/Index.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'porject',
      component: Project
    },
    {
      path: '/field-type',
      name: 'FieldType',
      component: import('../views/FieldType.vue')
    },
    {
      path: '/datasouce',
      name: 'Datasource',
      component: import('../views/datasource/Index.vue')
    },
    {
      path: '/baseClass',
      name: 'BaseClass',
      component: import('../views/BaseClass.vue')
    },
    {
      path: '/model',
      name: 'Model',
      component: import('../views/model/Index.vue')
    },
    {
      path: '/model/details',
      name: 'ModelDetils',
      component: import('../views/model/Details.vue')
    },
    {
      path: '/model/add_or_update',
      name: 'ModelAddOrUpdate',
      component: import('../views/model/AddOrUpdate.vue')
    },
  ]
})

export default router
