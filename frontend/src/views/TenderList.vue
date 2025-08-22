<template>
  <div class="tender-list">
    <div class="container">
      <!-- 搜索和筛选区域 -->
      <div class="search-section">
        <div class="search-header">
          <h1>招标信息查询</h1>
          <div class="search-stats">
            共找到 <span class="highlight">{{ total }}</span> 条招标信息
          </div>
        </div>
        
        <!-- 搜索表单 -->
        <el-form :model="searchForm" class="search-form" inline>
          <el-form-item label="关键词">
            <el-input
              v-model="searchForm.title"
              placeholder="请输入项目标题或招标单位"
              clearable
              style="width: 300px"
            />
          </el-form-item>
          
          <el-form-item label="地区">
            <el-select
              v-model="searchForm.location"
              placeholder="请选择地区"
              clearable
              style="width: 200px"
            >
              <el-option
                v-for="location in locations"
                :key="location"
                :label="location"
                :value="location"
              />
            </el-select>
          </el-form-item>
          
          <el-form-item label="招标类型">
            <el-select
              v-model="searchForm.category"
              placeholder="请选择招标类型"
              clearable
              style="width: 200px"
            >
              <el-option
                v-for="category in categories"
                :key="category"
                :label="category"
                :value="category"
              />
            </el-select>
          </el-form-item>
          
          <el-form-item label="截止时间">
            <el-date-picker
              v-model="searchForm.dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              style="width: 300px"
            />
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" @click="handleSearch" :loading="loading">
              <el-icon><Search /></el-icon>
              搜索
            </el-button>
            <el-button @click="resetSearch">
              <el-icon><Refresh /></el-icon>
              重置
            </el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 项目列表 -->
      <div class="list-section">
        <div class="list-header">
          <div class="list-title">
            <h2>招标项目列表</h2>
            <el-tag type="info">{{ total }} 个项目</el-tag>
          </div>
          <div class="list-actions">
            <el-button size="small" @click="refreshList" :loading="loading">
              <el-icon><Refresh /></el-icon>
              刷新
            </el-button>
          </div>
        </div>

        <!-- 项目卡片列表 -->
        <div class="project-list" v-loading="loading">
          <div
            v-for="project in projects"
            :key="project.id"
            class="project-card"
            @click="viewProjectDetail(project.id)"
          >
            <div class="card-header">
              <div class="project-title">
                <h3>{{ project.title }}</h3>
                <div class="project-tags">
                  <el-tag type="primary" size="small">{{ project.category }}</el-tag>
                  <el-tag type="success" size="small">{{ project.location }}</el-tag>
                  <el-tag type="warning" size="small">{{ formatDate(project.publishTime) }}</el-tag>
                </div>
              </div>
              <div class="project-id">
                项目编号：{{ project.projectId }}
              </div>
            </div>

            <div class="card-content">
              <div class="project-info">
                <div class="info-item">
                  <span class="label">招标单位：</span>
                  <span class="value">{{ project.bidder }}</span>
                </div>
                <div class="info-item">
                  <span class="label">代理单位：</span>
                  <span class="value">{{ project.agency || '未获取到代理单位' }}</span>
                </div>
                <div class="info-item">
                  <span class="label">预估金额：</span>
                  <span class="value">{{ project.estimatedAmount || '--' }}</span>
                </div>
                <div class="info-item">
                  <span class="label">投标截止：</span>
                  <span class="value deadline" :class="{ 'urgent': isUrgent(project.bidDeadline) }">
                    {{ formatDateTime(project.bidDeadline) }}
                  </span>
                </div>
              </div>

                             <div class="project-actions">
                 <el-button size="small" type="primary" @click.stop="viewProjectDetail(project.id)">
                   查看详情
                 </el-button>
               </div>
            </div>

            <div class="card-footer">
              <div class="contact-info">
                <span>联系人：{{ project.contactName || '--' }}</span>
                <span>电话：{{ maskPhone(project.contactPhone) || '--' }}</span>
              </div>
              <div class="attachment-info" v-if="project.attachments?.length">
                <el-icon><Paperclip /></el-icon>
                <span>{{ project.attachments.length }} 个附件</span>
              </div>
            </div>
          </div>

          <!-- 空状态 -->
          <div v-if="!loading && projects.length === 0" class="empty-state">
            <el-empty description="暂无招标项目" />
          </div>
        </div>

        <!-- 分页 -->
        <div class="pagination-section" v-if="total > 0">
          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[10, 20, 50, 100]"
            :total="total"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div>
      </div>
    </div>

    
  </div>
</template>

<script>
import { ref, computed, onMounted, watch } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import dayjs from 'dayjs'

export default {
  name: 'TenderList',
  setup() {
    const store = useStore()
    const router = useRouter()
    
    const projects = computed(() => store.getters.tenderProjects)
    const loading = computed(() => store.getters.loading)
    const total = computed(() => store.getters.total)
    
    // 搜索表单
    const searchForm = ref({
      title: '',
      location: '',
      category: '',
      dateRange: []
    })
    
    // 分页
    const currentPage = ref(1)
    const pageSize = ref(10)
    
    // 筛选选项
    const locations = ref([])
    const categories = ref([])
    
    
    
    // 初始化
    onMounted(async () => {
      await Promise.all([
        loadProjects(),
        loadLocations(),
        loadCategories()
      ])
    })
    
    // 加载项目列表
    const loadProjects = async () => {
      const params = {
        page: currentPage.value - 1,
        size: pageSize.value,
        ...getSearchParams()
      }
      await store.dispatch('fetchTenderProjects', params)
    }
    
    // 加载地区列表
    const loadLocations = async () => {
      try {
        const response = await store.$api.getAllLocations()
        locations.value = response.data
      } catch (error) {
        console.error('加载地区列表失败:', error)
      }
    }
    
    // 加载分类列表
    const loadCategories = async () => {
      try {
        const response = await store.$api.getAllCategories()
        categories.value = response.data
      } catch (error) {
        console.error('加载分类列表失败:', error)
      }
    }
    
    // 获取搜索参数
    const getSearchParams = () => {
      const params = {}
      if (searchForm.value.title) params.title = searchForm.value.title
      if (searchForm.value.location) params.location = searchForm.value.location
      if (searchForm.value.category) params.category = searchForm.value.category
      if (searchForm.value.dateRange && searchForm.value.dateRange.length === 2) {
        params.startDate = searchForm.value.dateRange[0] + 'T00:00:00'
        params.endDate = searchForm.value.dateRange[1] + 'T23:59:59'
      }
      return params
    }
    
    // 搜索
    const handleSearch = () => {
      currentPage.value = 1
      loadProjects()
    }
    
    // 重置搜索
    const resetSearch = () => {
      searchForm.value = {
        title: '',
        location: '',
        category: '',
        dateRange: []
      }
      currentPage.value = 1
      loadProjects()
    }
    
    // 刷新列表
    const refreshList = () => {
      loadProjects()
    }
    
    
    
    // 查看项目详情
    const viewProjectDetail = (id) => {
      router.push(`/detail/${id}`)
    }
    
    
    
    // 分页处理
    const handleSizeChange = (size) => {
      pageSize.value = size
      currentPage.value = 1
      loadProjects()
    }
    
    const handleCurrentChange = (page) => {
      currentPage.value = page
      loadProjects()
    }
    
    // 格式化日期
    const formatDate = (date) => {
      if (!date) return '--'
      return dayjs(date).format('YYYY/MM/DD')
    }
    
    const formatDateTime = (date) => {
      if (!date) return '--'
      return dayjs(date).format('YYYY-MM-DD HH:mm')
    }
    
    // 掩码电话号码
    const maskPhone = (phone) => {
      if (!phone) return '--'
      return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
    }
    
    // 判断是否紧急
    const isUrgent = (deadline) => {
      if (!deadline) return false
      const now = dayjs()
      const deadlineDate = dayjs(deadline)
      const diffDays = deadlineDate.diff(now, 'day')
      return diffDays <= 7 && diffDays >= 0
    }
    
         return {
       projects,
       loading,
       total,
       searchForm,
       currentPage,
       pageSize,
       locations,
       categories,
       handleSearch,
       resetSearch,
       refreshList,
       
       viewProjectDetail,
       handleSizeChange,
       handleCurrentChange,
       formatDate,
       formatDateTime,
       maskPhone,
       isUrgent
     }
  }
}
</script>

<style scoped>
.tender-list {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20px 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.search-section {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 24px;
  margin-bottom: 20px;
}

.search-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-header h1 {
  font-size: 20px;
  font-weight: 600;
  color: #1a1a1a;
  margin: 0;
}

.search-stats {
  color: #666;
  font-size: 14px;
}

.highlight {
  color: #1890ff;
  font-weight: 600;
}

.search-form {
  margin-bottom: 0;
}

.list-section {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  border-bottom: 1px solid #e8e8e8;
}

.list-title {
  display: flex;
  align-items: center;
  gap: 12px;
}

.list-title h2 {
  font-size: 16px;
  font-weight: 600;
  color: #1a1a1a;
  margin: 0;
}

.list-actions {
  display: flex;
  gap: 8px;
}

.project-list {
  padding: 0;
}

.project-card {
  padding: 20px 24px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: background-color 0.2s;
}

.project-card:hover {
  background-color: #f8f9fa;
}

.project-card:last-child {
  border-bottom: none;
}

.card-header {
  margin-bottom: 16px;
}

.project-title {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 8px;
}

.project-title h3 {
  font-size: 16px;
  font-weight: 600;
  color: #1a1a1a;
  margin: 0;
  flex: 1;
  line-height: 1.4;
}

.project-tags {
  display: flex;
  gap: 8px;
  flex-shrink: 0;
}

.project-id {
  font-size: 12px;
  color: #999;
}

.card-content {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 16px;
}

.project-info {
  flex: 1;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

.info-item {
  display: flex;
  align-items: center;
}

.info-item .label {
  color: #666;
  font-size: 14px;
  min-width: 80px;
}

.info-item .value {
  color: #1a1a1a;
  font-size: 14px;
  font-weight: 500;
}

.deadline {
  color: #ff4d4f;
  font-weight: 600;
}

.deadline.urgent {
  color: #ff7875;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% { opacity: 1; }
  50% { opacity: 0.7; }
  100% { opacity: 1; }
}

.project-actions {
  display: flex;
  gap: 8px;
  flex-shrink: 0;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 12px;
  border-top: 1px solid #f0f0f0;
}

.contact-info {
  display: flex;
  gap: 16px;
  font-size: 12px;
  color: #666;
}

.attachment-info {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: #666;
}

.empty-state {
  padding: 60px 0;
  text-align: center;
}

.pagination-section {
  padding: 20px 24px;
  display: flex;
  justify-content: center;
  border-top: 1px solid #e8e8e8;
}

.contact-modal {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.contact-item {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px solid #f0f0f0;
}

.contact-item:last-child {
  border-bottom: none;
}

.contact-label {
  color: #666;
  font-weight: 500;
  min-width: 100px;
}

.contact-value {
  color: #1a1a1a;
  font-weight: 500;
  flex: 1;
}

@media (max-width: 768px) {
  .container {
    padding: 0 10px;
  }
  
  .search-section {
    padding: 16px;
  }
  
  .search-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
  
  .search-form {
    display: flex;
    flex-direction: column;
  }
  
  .search-form .el-form-item {
    margin-bottom: 12px;
  }
  
  .list-header {
    padding: 16px;
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .project-card {
    padding: 16px;
  }
  
  .project-title {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
  
  .project-tags {
    align-self: flex-start;
  }
  
  .card-content {
    flex-direction: column;
    gap: 16px;
  }
  
  .project-info {
    grid-template-columns: 1fr;
  }
  
  .project-actions {
    width: 100%;
    justify-content: space-between;
  }
  
  .card-footer {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
  
  .contact-info {
    flex-direction: column;
    gap: 4px;
  }
}
</style>
