import { createStore } from 'vuex'
import tenderApi from '../api/tenderApi'

export default createStore({
  state: {
    tenderProjects: [],
    currentProject: null,
    loading: false,
    total: 0
  },
  mutations: {
    SET_TENDER_PROJECTS(state, projects) {
      state.tenderProjects = projects
    },
    SET_CURRENT_PROJECT(state, project) {
      state.currentProject = project
    },
    SET_LOADING(state, loading) {
      state.loading = loading
    },
    SET_TOTAL(state, total) {
      state.total = total
    }
  },
  actions: {
    async fetchTenderProjects({ commit }, params = {}) {
      commit('SET_LOADING', true)
      try {
        const response = await tenderApi.getTenderProjects(params)
        commit('SET_TENDER_PROJECTS', response.data.content)
        commit('SET_TOTAL', response.data.totalElements)
      } catch (error) {
        console.error('获取招标项目列表失败:', error)
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async fetchTenderProjectById({ commit }, id) {
      commit('SET_LOADING', true)
      try {
        const response = await tenderApi.getTenderProjectById(id)
        commit('SET_CURRENT_PROJECT', response.data)
      } catch (error) {
        console.error('获取招标项目详情失败:', error)
      } finally {
        commit('SET_LOADING', false)
      }
    }
  },
  getters: {
    tenderProjects: state => state.tenderProjects,
    currentProject: state => state.currentProject,
    loading: state => state.loading,
    total: state => state.total
  },
  // 添加API到store实例
  install: function(app) {
    app.config.globalProperties.$api = tenderApi
  }
})
