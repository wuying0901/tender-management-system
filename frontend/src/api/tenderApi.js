import axios from 'axios'

// 使用相对路径，开发环境由 vue.config.js 代理到后端 8080
const API_BASE_URL = '/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器：为管理员接口自动附加Token
api.interceptors.request.use(
  config => {
    try {
      const isAdminApi = typeof config.url === 'string' && config.url.startsWith('/admin')
      if (isAdminApi) {
        const token = localStorage.getItem('admin_token')
        if (token) {
          config.headers = config.headers || {}
          config.headers.Authorization = `Bearer ${token}`
        }
      }
    } catch (e) {}
    return config
  },
  error => Promise.reject(error)
)

// 响应拦截器
api.interceptors.response.use(
  response => {
    return response
  },
  error => {
    console.error('API请求错误:', error)
    return Promise.reject(error)
  }
)

export default {
  // 管理员登录
  adminLogin(data) {
    return api.post('/admin/auth/login', data)
  },

  // 获取招标项目列表
  getTenderProjects(params = {}) {
    return api.get('/tender-projects', { params })
  },

  // 根据ID获取招标项目详情
  getTenderProjectById(id) {
    return api.get(`/tender-projects/${id}`)
  },

  // 根据项目编号获取招标项目详情
  getTenderProjectByProjectId(projectId) {
    return api.get(`/tender-projects/project-id/${projectId}`)
  },

  // 搜索招标项目
  searchTenderProjects(params = {}) {
    return api.get('/tender-projects/search', { params })
  },

  // 创建招标项目
  createTenderProject(data) {
    return api.post('/tender-projects', data)
  },

  // 更新招标项目
  updateTenderProject(id, data) {
    return api.put(`/tender-projects/${id}`, data)
  },

  // 删除招标项目
  deleteTenderProject(id) {
    return api.delete(`/tender-projects/${id}`)
  },

  // 管理员-创建/更新/删除（受保护）
  adminCreateProject(data) {
    return api.post('/admin/tender-projects', data)
  },
  adminUpdateProject(id, data) {
    return api.put(`/admin/tender-projects/${id}`, data)
  },
  adminDeleteProject(id) {
    return api.delete(`/admin/tender-projects/${id}`)
  },

  // 获取所有地区
  getAllLocations() {
    return api.get('/tender-projects/locations')
  },

  // 获取所有分类
  getAllCategories() {
    return api.get('/tender-projects/categories')
  }
}
