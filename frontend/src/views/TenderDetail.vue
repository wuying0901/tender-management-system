<template>
  <div class="tender-detail">
    <div class="container">
      <!-- 主内容区域 -->
      <div class="main-content">
        <!-- 标题区域 -->
        <div class="header-section">
          <h1 class="main-title">{{ project?.title || '加载中...' }}</h1>
          <div class="title-tags">
            <el-tag type="primary" size="small">招标公告</el-tag>
            <el-tag type="success" size="small">{{ project?.location || '--' }}</el-tag>
            <el-tag type="warning" size="small">{{ formatDate(project?.publishTime) }}</el-tag>
          </div>
          
          <div class="attachment-badge" v-if="project?.attachments?.length">
            <el-icon><Paperclip /></el-icon>
            含附件
          </div>
        </div>

        <!-- 正文摘要 -->
        <div class="summary-section">
          <h2>正文摘要</h2>
          <div class="summary-grid">
            <div class="summary-item">
              <span class="label">项目编号</span>
              <span class="value">{{ project?.projectId || '--' }}</span>
            </div>
            <div class="summary-item">
              <span class="label">招标单位</span>
              <span class="value">
                {{ project?.bidder || '--' }}
                <el-tag size="small" type="info">50</el-tag>
              </span>
            </div>
            <div class="summary-item">
              <span class="label">报名截止时间</span>
              <span class="value">{{ formatDate(project?.registrationEndTime) || '--' }}</span>
            </div>
            <div class="summary-item">
              <span class="label">预估金额</span>
              <span class="value">{{ project?.estimatedAmount || '--' }}</span>
            </div>
            <div class="summary-item">
              <span class="label">代理单位</span>
              <span class="value">{{ project?.agency || '未获取到代理单位' }}</span>
            </div>
            <div class="summary-item">
              <span class="label">投标截止时间</span>
              <span class="value">{{ formatDate(project?.bidDeadline) || '--' }}</span>
            </div>
          </div>
        </div>

        <!-- 联系人信息 -->
        <div class="contact-section">
          <h2>本项目联系人 (1)</h2>
          <div class="contact-card">
            <div class="contact-info">
              <div class="contact-type">招标单位</div>
              <div class="contact-name">{{ project?.contactName || '--' }}</div>
              <div class="contact-phone">{{ maskPhone(project?.contactPhone) || '--' }}</div>
            </div>
            <el-button type="primary" size="small" @click="showContactDetails">
              查看联系人详情
            </el-button>
          </div>
        </div>

        <!-- 公告内容 -->
        <div class="announcement-section">
          <h2>公告内容</h2>
          <div class="announcement-list">
            <div class="announcement-item">
              <span class="item-label">招标项目编号</span>
              <span class="item-value">{{ project?.projectId || '--' }}</span>
            </div>
            <div class="announcement-item">
              <span class="item-label">采购单位</span>
              <span class="item-value">{{ project?.bidder || '--' }}</span>
            </div>
            <div class="announcement-item">
              <span class="item-label">资金来源</span>
              <span class="item-value">{{ project?.fundSource || '--' }}</span>
            </div>
            <div class="announcement-item">
              <span class="item-label">项目概况</span>
              <span class="item-value">{{ project?.projectScope || '--' }}</span>
            </div>
            <div class="announcement-item">
              <span class="item-label">监督联系人</span>
              <span class="item-value">{{ project?.supervisorName || '--' }}</span>
            </div>
            <div class="announcement-item">
              <span class="item-label">监督联系电话</span>
              <span class="item-value">
                {{ maskPhone(project?.supervisorPhone) || '--' }}
              </span>
            </div>
            <div class="announcement-item">
              <span class="item-label">文件获取截止时间</span>
              <span class="item-value">{{ formatDate(project?.documentEndTime) || '--' }}</span>
            </div>
          </div>
        </div>

        <!-- 标段信息 -->
        <div class="package-section">
          <h2>标段/包信息</h2>
          <div class="package-info">
            <div class="info-item">
              <span class="info-label">标段/包名称</span>
              <span class="info-value">{{ project?.title || '--' }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">标段包/编号</span>
              <span class="info-value">{{ project?.projectId || '--' }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">报名开始时间</span>
              <span class="info-value">{{ formatDate(project?.registrationStartTime) || '--' }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">报名截止时间</span>
              <span class="info-value">{{ formatDate(project?.registrationEndTime) || '--' }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">文件获取开始时间</span>
              <span class="info-value">{{ formatDate(project?.documentStartTime) || '--' }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">文件获取截止时间</span>
              <span class="info-value">{{ formatDate(project?.documentEndTime) || '--' }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">供应商提出澄清截止时间</span>
              <span class="info-value">{{ formatDate(project?.clarificationEndTime) || '--' }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">截标/开标时间</span>
              <span class="info-value">{{ formatDateTime(project?.bidDeadline) || '--' }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">招标范围</span>
              <span class="info-value">{{ project?.projectScope || '--' }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">投标人资格要求</span>
              <span class="info-value">{{ project?.qualification || '--' }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">监督人名称</span>
              <span class="info-value">{{ project?.supervisorName || '--' }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">监督人电话</span>
              <span class="info-value">
                {{ maskPhone(project?.supervisorPhone) || '--' }}
              </span>
            </div>
          </div>
        </div>

        <!-- 招标人联系方式 -->
        <div class="bidder-contact-section">
          <h2>招标人联系方式</h2>
          <div class="bidder-info">
            <div class="bidder-item">
              <span class="bidder-label">招标人</span>
              <span class="bidder-value">{{ project?.bidder || '--' }}</span>
            </div>
            <div class="bidder-item">
              <span class="bidder-label">联系人</span>
              <span class="bidder-value">{{ project?.contactName || '--' }}</span>
            </div>
            <div class="bidder-item">
              <span class="bidder-label">联系电话</span>
              <span class="bidder-value">
                {{ maskPhone(project?.contactPhone) || '--' }}
              </span>
            </div>
            <div class="bidder-item">
              <span class="bidder-label">联系地址</span>
              <span class="bidder-value">{{ project?.contactAddress || '--' }}</span>
            </div>
          </div>
        </div>

        <!-- 相关附件 -->
        <div class="attachments-section">
          <h2>相关附件</h2>
          <div class="attachment-list" v-if="project?.attachments?.length">
            <div class="attachment-item" v-for="attachment in project.attachments" :key="attachment.id">
              <el-icon color="#ff4d4f"><Document /></el-icon>
              <a href="#" class="attachment-link" @click="downloadAttachment(attachment)">
                {{ attachment.originalFileName }}
              </a>
              <el-tag size="small" type="info">{{ attachment.attachmentType }}</el-tag>
            </div>
          </div>
          <div class="other-attachments">
            <h3>其他附件:</h3>
            <div class="no-attachments">暂无其他附件</div>
          </div>
        </div>
      </div>

      <!-- 侧边栏 -->
      <div class="sidebar">
        <!-- 项目小记 -->
        <div class="project-notes">
          <div class="notes-header">
            <el-checkbox v-model="showNotes">项目小记</el-checkbox>
          </div>
          <el-input
            v-if="showNotes"
            v-model="projectNotes"
            type="textarea"
            :rows="6"
            placeholder="请输入您要备注的重要事项或内容"
            @input="saveNotes"
          />
        </div>
      </div>
    </div>

    <!-- 日程模态框 -->
    <el-dialog v-model="scheduleVisible" title="添加日程" width="500px">
      <el-form :model="scheduleForm" label-width="100px">
        <el-form-item label="项目名称">
          <el-input v-model="scheduleForm.projectName" readonly />
        </el-form-item>
        <el-form-item label="提醒时间">
          <el-date-picker
            v-model="scheduleForm.reminderTime"
            type="datetime"
            placeholder="选择提醒时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            v-model="scheduleForm.notes"
            type="textarea"
            :rows="3"
            placeholder="请输入备注信息"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="scheduleVisible = false">取消</el-button>
        <el-button type="primary" @click="saveSchedule">保存</el-button>
      </template>
    </el-dialog>

    <!-- 联系人详情模态框 -->
    <el-dialog v-model="contactVisible" title="联系人详情" width="500px">
      <div class="contact-detail">
        <div class="detail-item">
          <span class="detail-label">姓名：</span>
          <span class="detail-value">{{ project?.contactName }}</span>
        </div>
        <div class="detail-item">
          <span class="detail-label">职位：</span>
          <span class="detail-value">{{ project?.position }}</span>
        </div>
        <div class="detail-item">
          <span class="detail-label">电话：</span>
          <span class="detail-value">{{ maskPhone(project?.contactPhone) }}</span>
        </div>
        <div class="detail-item">
          <span class="detail-label">邮箱：</span>
          <span class="detail-value">{{ project?.contactEmail }}</span>
        </div>
        <div class="detail-item">
          <span class="detail-label">部门：</span>
          <span class="detail-value">{{ project?.department }}</span>
        </div>
      </div>
      <template #footer>
        <el-button type="primary" @click="callPhone(project?.contactPhone)">拨打电话</el-button>
        <el-button @click="sendEmail(project?.contactEmail)">发送邮件</el-button>
        <el-button @click="contactVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import dayjs from 'dayjs'
import tenderApi from '../api/tenderApi'

export default {
  name: 'TenderDetail',
  setup() {
    const store = useStore()
    const route = useRoute()
    
    const project = computed(() => store.getters.currentProject)
    const loading = computed(() => store.getters.loading)
    
    const isFollowing = ref(false)
    const showNotes = ref(false)
    const projectNotes = ref('')
    const scheduleVisible = ref(false)
    const contactVisible = ref(false)
    
    const scheduleForm = ref({
      projectName: '',
      reminderTime: '',
      notes: ''
    })

    onMounted(async () => {
      const routeId = route.params.id
      if (!routeId) return

      try {
        // 如果是纯数字，则按数据库自增ID查询；否则按项目编号查询
        if (/^\d+$/.test(String(routeId))) {
          await store.dispatch('fetchTenderProjectById', routeId)
        } else {
          const res = await tenderApi.getTenderProjectByProjectId(routeId)
          store.commit('SET_CURRENT_PROJECT', res.data)
        }
      } catch (e) {
        // 回退：两种方式都尝试一次
        try {
          const res = await tenderApi.getTenderProjectByProjectId(routeId)
          store.commit('SET_CURRENT_PROJECT', res.data)
        } catch (err) {
          console.error('加载项目详情失败', err)
          ElMessage.error('加载项目详情失败')
        }
      }

      scheduleForm.value.projectName = project.value?.title || ''

      // 加载本地存储的笔记
      const savedNotes = localStorage.getItem(`projectNotes_${routeId}`)
      if (savedNotes) {
        projectNotes.value = savedNotes
        showNotes.value = true
      }
    })

    const formatDate = (date) => {
      if (!date) return '--'
      return dayjs(date).format('YYYY/MM/DD')
    }

    const formatDateTime = (date) => {
      if (!date) return '--'
      return dayjs(date).format('YYYY-MM-DD HH:mm:ss')
    }

    const maskPhone = (phone) => {
      if (!phone) return '--'
      return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
    }

    const showScheduleModal = () => {
      scheduleVisible.value = true
    }

    const saveSchedule = () => {
      if (!scheduleForm.value.reminderTime) {
        ElMessage.warning('请选择提醒时间')
        return
      }
      ElMessage.success('日程已保存！')
      scheduleVisible.value = false
    }

    const toggleFollow = () => {
      isFollowing.value = !isFollowing.value
      ElMessage.success(isFollowing.value ? '已跟进项目' : '已取消跟进项目')
    }

    const exportProject = () => {
      const projectData = {
        title: project.value?.title,
        projectId: project.value?.projectId,
        bidder: project.value?.bidder,
        deadline: formatDate(project.value?.bidDeadline),
        contact: project.value?.contactName,
        phone: project.value?.contactPhone
      }
      
      const dataStr = JSON.stringify(projectData, null, 2)
      const dataBlob = new Blob([dataStr], { type: 'application/json' })
      const url = URL.createObjectURL(dataBlob)
      
      const link = document.createElement('a')
      link.href = url
      link.download = '招标项目信息.json'
      link.click()
      
      URL.revokeObjectURL(url)
      ElMessage.success('项目信息已导出')
    }

    const printPage = () => {
      window.print()
    }

    const showContactDetails = () => {
      contactVisible.value = true
    }

    const callPhone = (phone) => {
      if (phone) {
        ElMessage.success(`正在拨打 ${phone}...`)
      }
    }

    const sendEmail = (email) => {
      if (email) {
        window.open(`mailto:${email}?subject=关于招标项目的咨询`)
      }
    }

    const downloadAttachment = (attachment) => {
      ElMessage.success(`正在下载 ${attachment.originalFileName}...`)
    }

    const saveNotes = () => {
      const routeId = route.params.id
      if (routeId) {
        localStorage.setItem(`projectNotes_${routeId}`, projectNotes.value)
      }
    }

    return {
      project,
      loading,
      isFollowing,
      showNotes,
      projectNotes,
      scheduleVisible,
      contactVisible,
      scheduleForm,
      formatDate,
      formatDateTime,
      maskPhone,
      showScheduleModal,
      saveSchedule,
      toggleFollow,
      exportProject,
      printPage,
      showContactDetails,
      callPhone,
      sendEmail,
      downloadAttachment,
      saveNotes
    }
  }
}
</script>

<style scoped>
.tender-detail {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20px 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  gap: 20px;
}

.main-content {
  flex: 1;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.header-section {
  padding: 24px;
  border-bottom: 1px solid #e8e8e8;
  position: relative;
}

.main-title {
  font-size: 20px;
  font-weight: 600;
  color: #1a1a1a;
  margin-bottom: 16px;
  line-height: 1.4;
}

.title-tags {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}

.action-buttons {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}

.attachment-badge {
  position: absolute;
  top: 24px;
  right: 24px;
  background-color: #f0f0f0;
  color: #666;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  display: flex;
  align-items: center;
  gap: 4px;
}

.summary-section,
.contact-section,
.announcement-section,
.package-section,
.bidder-contact-section,
.attachments-section {
  padding: 24px;
  border-bottom: 1px solid #e8e8e8;
}

.summary-section h2,
.contact-section h2,
.announcement-section h2,
.package-section h2,
.bidder-contact-section h2,
.attachments-section h2 {
  font-size: 16px;
  font-weight: 600;
  color: #1a1a1a;
  margin-bottom: 16px;
}

.summary-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.summary-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
}

.summary-item .label {
  color: #666;
  font-size: 14px;
}

.summary-item .value {
  color: #1a1a1a;
  font-size: 14px;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 8px;
}

.contact-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  background-color: #f8f9fa;
  border-radius: 6px;
}

.contact-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.contact-type {
  font-size: 12px;
  color: #999;
}

.contact-name {
  font-size: 16px;
  font-weight: 600;
  color: #1a1a1a;
}

.contact-phone {
  font-size: 14px;
  color: #666;
}

.announcement-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.announcement-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.announcement-item:last-child {
  border-bottom: none;
}

.item-label {
  color: #666;
  font-size: 14px;
  min-width: 120px;
}

.item-value {
  color: #1a1a1a;
  font-size: 14px;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 8px;
}

.package-info {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.info-item:last-child {
  border-bottom: none;
}

.info-label {
  color: #666;
  font-size: 14px;
  min-width: 180px;
}

.info-value {
  color: #1a1a1a;
  font-size: 14px;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 8px;
}

.bidder-info {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.bidder-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.bidder-item:last-child {
  border-bottom: none;
}

.bidder-label {
  color: #666;
  font-size: 14px;
  min-width: 80px;
}

.bidder-value {
  color: #1a1a1a;
  font-size: 14px;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 8px;
}

.attachment-list {
  margin-bottom: 20px;
}

.attachment-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.attachment-link {
  color: #1890ff;
  text-decoration: none;
  font-size: 14px;
  flex: 1;
}

.attachment-link:hover {
  text-decoration: underline;
}

.other-attachments h3 {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.no-attachments {
  color: #999;
  font-size: 14px;
  font-style: italic;
}

.sidebar {
  width: 300px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.project-notes {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.notes-header {
  margin-bottom: 12px;
}

.contact-detail {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px solid #f0f0f0;
}

.detail-item:last-child {
  border-bottom: none;
}

.detail-label {
  color: #666;
  font-weight: 500;
}

.detail-value {
  color: #1a1a1a;
  font-weight: 500;
}

@media (max-width: 1024px) {
  .container {
    flex-direction: column;
  }
  
  .sidebar {
    width: 100%;
  }
  
  .summary-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .container {
    padding: 0 10px;
  }
  
  .header-section,
  .summary-section,
  .contact-section,
  .announcement-section,
  .package-section,
  .bidder-contact-section,
  .attachments-section {
    padding: 16px;
  }
  
  .main-title {
    font-size: 18px;
  }
  
  .action-buttons {
    flex-wrap: wrap;
  }
  
  .attachment-badge {
    position: static;
    margin-top: 12px;
    display: inline-block;
  }
  
  .contact-card {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .announcement-item,
  .info-item,
  .bidder-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 4px;
  }
  
  .item-label,
  .info-label,
  .bidder-label {
    min-width: auto;
  }
}
</style>
