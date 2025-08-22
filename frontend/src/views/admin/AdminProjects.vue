<template>
  <div class="admin-projects">
    <div class="toolbar">
      <div class="left">
        <h2>项目管理</h2>
        <span class="tip">在这里创建、编辑或删除招标项目</span>
      </div>
      <div class="actions">
        <el-button type="primary" @click="goCreate">
          <el-icon style="margin-right:4px"><Plus /></el-icon>新建项目
        </el-button>
        <el-button @click="logout">退出登录</el-button>
      </div>
    </div>
    <el-table :data="projects" v-loading="loading" border>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="projectId" label="项目编号" width="180" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="bidder" label="招标单位" width="180" />
      <el-table-column prop="location" label="地区" width="140" />
      <el-table-column prop="category" label="类型" width="120" />
      <el-table-column fixed="right" label="操作" width="220">
        <template #default="{ row }">
          <el-button size="small" @click="goEdit(row)">进入详情</el-button>
          <el-button size="small" type="danger" @click="onDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import tenderApi from '../../api/tenderApi'

export default {
  name: 'AdminProjects',
  setup() {
    const router = useRouter()
    const projects = ref([])
    const loading = ref(false)

    const fetchList = async () => {
      loading.value = true
      try {
        const res = await tenderApi.getTenderProjects({ page:0, size:100 })
        projects.value = res.data.content
      } finally { loading.value = false }
    }

    const goCreate = () => router.push('/admin/projects/new')
    const goEdit = (row) => router.push(`/admin/projects/${row.id}`)

    const onDelete = async (row) => {
      await ElMessageBox.confirm(`确定删除【${row.title}】吗？`, '提示', { type:'warning' })
      try {
        await tenderApi.adminDeleteProject(row.id)
        ElMessage.success('已删除')
        fetchList()
      } catch (e) { ElMessage.error('删除失败') }
    }

    const logout = () => {
      localStorage.removeItem('admin_token')
      router.push('/admin')
    }

    onMounted(fetchList)

    return { projects, loading, goCreate, goEdit, onDelete, logout }
  }
}
</script>

<style scoped>
.admin-projects{ padding:20px; }
.toolbar{ display:flex; align-items:center; justify-content:space-between; margin-bottom:14px; }
.left{ display:flex; align-items:baseline; gap:10px; }
.left h2{ margin:0; font-weight:700; }
.left .tip{ color:#909399; font-size:13px; }
.actions{ display:flex; gap:8px; }
</style>


