import { createRouter, createWebHistory } from 'vue-router'
import TenderDetail from '../views/TenderDetail.vue'
import TenderList from '../views/TenderList.vue'
const AdminLogin = () => import('../views/admin/Login.vue')
const AdminProjects = () => import('../views/admin/AdminProjects.vue')
const AdminProjectDetail = () => import('../views/admin/AdminProjectDetail.vue')

const routes = [
  {
    path: '/',
    name: 'TenderList',
    component: TenderList
  },
  {
    path: '/detail/:id',
    name: 'TenderDetail',
    component: TenderDetail,
    props: true
  },
  {
    path: '/admin',
    name: 'AdminLogin',
    component: AdminLogin
  },
  {
    path: '/admin/projects',
    name: 'AdminProjects',
    component: AdminProjects
  },
  {
    path: '/admin/projects/new',
    name: 'AdminProjectCreate',
    component: AdminProjectDetail
  },
  {
    path: '/admin/projects/:id',
    name: 'AdminProjectEdit',
    component: AdminProjectDetail,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 简单路由守卫：未登录拦到 /admin
router.beforeEach((to, from, next) => {
  if (to.path.startsWith('/admin') && to.path !== '/admin') {
    const token = localStorage.getItem('admin_token')
    if (!token) return next('/admin')
  }
  next()
})

export default router
