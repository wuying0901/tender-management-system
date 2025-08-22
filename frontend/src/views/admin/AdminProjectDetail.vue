<template>
  <div class="admin-project-detail">
    <div class="header">
      <el-button @click="$router.back()">返回</el-button>
      <div class="spacer" />
      <el-button type="primary" @click="onSave" :loading="saving">保存</el-button>
    </div>

    <div class="detail-shell">
      <!-- 标题与标签 -->
      <div class="header-section">
        <el-input v-model="form.title" class="title-input" placeholder="请输入项目标题" />
        <div class="title-tags">
          <el-tag type="primary" size="small">招标公告</el-tag>
          <el-tag type="success" size="small">{{ form.location || '地区' }}</el-tag>
          <el-tag type="warning" size="small">编辑模式</el-tag>
        </div>
      </div>

      <!-- 正文摘要（可直接编辑） -->
      <div class="summary-section">
        <h2>正文摘要</h2>
        <div class="summary-grid">
          <div class="summary-item">
            <span class="label">项目编号</span>
            <el-input v-model="form.projectId" class="inline-input" placeholder="项目编号" />
          </div>
          <div class="summary-item">
            <span class="label">地区</span>
            <el-input v-model="form.location" class="inline-input" placeholder="如 四川-成都" />
          </div>
          <div class="summary-item">
            <span class="label">招标单位</span>
            <el-input v-model="form.bidder" class="inline-input" placeholder="招标单位" />
          </div>
          <div class="summary-item">
            <span class="label">招标类型</span>
            <el-input v-model="form.category" class="inline-input" placeholder="如 招标公告/中标公告" />
          </div>
          <div class="summary-item">
            <span class="label">报名截止时间</span>
            <el-date-picker v-model="form.registrationEndTime" type="date" class="inline-date" />
          </div>
          <div class="summary-item">
            <span class="label">预估金额</span>
            <el-input v-model="form.estimatedAmount" class="inline-input" placeholder="预估金额" />
          </div>
          <div class="summary-item">
            <span class="label">代理单位</span>
            <el-input v-model="form.agency" class="inline-input" placeholder="代理单位" />
          </div>
          <div class="summary-item">
            <span class="label">投标截止时间</span>
            <el-date-picker v-model="form.bidDeadline" type="datetime" class="inline-date" />
          </div>
        </div>
      </div>

      <!-- 联系人信息（可编辑） -->
      <div class="contact-section">
        <h2>本项目联系人</h2>
        <div class="contact-card">
          <div class="contact-info">
            <div class="contact-type">招标单位</div>
            <el-input v-model="form.contactName" class="inline-input" placeholder="联系人" />
            <el-input v-model="form.contactPhone" class="inline-input" placeholder="联系电话" />
          </div>
        </div>
      </div>

      <!-- 公告内容（可编辑） -->
      <div class="announcement-section">
        <h2>公告内容</h2>
        <div class="announcement-list">
          <div class="announcement-item">
            <span class="item-label">招标项目编号</span>
            <el-input v-model="form.projectId" class="inline-input" />
          </div>
          <div class="announcement-item">
            <span class="item-label">采购单位</span>
            <el-input v-model="form.bidder" class="inline-input" />
          </div>
          <div class="announcement-item">
            <span class="item-label">资金来源</span>
            <el-input v-model="form.fundSource" class="inline-input" />
          </div>
          <div class="announcement-item">
            <span class="item-label">项目概况</span>
            <el-input v-model="form.projectScope" class="inline-input" type="textarea" :rows="2" />
          </div>
          <div class="announcement-item">
            <span class="item-label">监督联系人</span>
            <el-input v-model="form.supervisorName" class="inline-input" />
          </div>
          <div class="announcement-item">
            <span class="item-label">监督联系电话</span>
            <el-input v-model="form.supervisorPhone" class="inline-input" />
          </div>
        </div>
      </div>

      <!-- 标段/包信息（可编辑） -->
      <div class="package-section">
        <h2>标段/包信息</h2>
        <div class="package-info">
          <div class="info-item">
            <span class="info-label">标段/包名称</span>
            <el-input v-model="form.title" class="inline-input" />
          </div>
          <div class="info-item">
            <span class="info-label">标段包/编号</span>
            <el-input v-model="form.projectId" class="inline-input" />
          </div>
          <div class="info-item">
            <span class="info-label">报名开始时间</span>
            <el-date-picker v-model="form.registrationStartTime" type="datetime" class="inline-date" />
          </div>
          <div class="info-item">
            <span class="info-label">报名截止时间</span>
            <el-date-picker v-model="form.registrationEndTime" type="datetime" class="inline-date" />
          </div>
          <div class="info-item">
            <span class="info-label">文件获取开始时间</span>
            <el-date-picker v-model="form.documentStartTime" type="datetime" class="inline-date" />
          </div>
          <div class="info-item">
            <span class="info-label">文件获取截止时间</span>
            <el-date-picker v-model="form.documentEndTime" type="datetime" class="inline-date" />
          </div>
          <div class="info-item">
            <span class="info-label">供应商提出澄清截止时间</span>
            <el-date-picker v-model="form.clarificationEndTime" type="datetime" class="inline-date" />
          </div>
          <div class="info-item">
            <span class="info-label">截标/开标时间</span>
            <el-date-picker v-model="form.bidDeadline" type="datetime" class="inline-date" />
          </div>
          <div class="info-item">
            <span class="info-label">招标范围</span>
            <el-input v-model="form.projectScope" class="inline-input" type="textarea" :rows="2" />
          </div>
          <div class="info-item">
            <span class="info-label">投标人资格要求</span>
            <el-input v-model="form.qualification" class="inline-input" type="textarea" :rows="2" />
          </div>
          <div class="info-item">
            <span class="info-label">监督人名称</span>
            <el-input v-model="form.supervisorName" class="inline-input" />
          </div>
          <div class="info-item">
            <span class="info-label">监督人电话</span>
            <el-input v-model="form.supervisorPhone" class="inline-input" />
          </div>
        </div>
      </div>

      <!-- 招标人联系方式（可编辑） -->
      <div class="bidder-contact-section">
        <h2>招标人联系方式</h2>
        <div class="bidder-info">
          <div class="bidder-item">
            <span class="bidder-label">招标人</span>
            <el-input v-model="form.bidder" class="inline-input" />
          </div>
          <div class="bidder-item">
            <span class="bidder-label">联系人</span>
            <el-input v-model="form.contactName" class="inline-input" />
          </div>
          <div class="bidder-item">
            <span class="bidder-label">联系电话</span>
            <el-input v-model="form.contactPhone" class="inline-input" />
          </div>
          <div class="bidder-item">
            <span class="bidder-label">联系地址</span>
            <el-input v-model="form.contactAddress" class="inline-input" />
          </div>
          <div class="bidder-item">
            <span class="bidder-label">联系邮箱</span>
            <el-input v-model="form.contactEmail" class="inline-input" />
          </div>
          <div class="bidder-item">
            <span class="bidder-label">部门</span>
            <el-input v-model="form.department" class="inline-input" />
          </div>
          <div class="bidder-item">
            <span class="bidder-label">职位</span>
            <el-input v-model="form.position" class="inline-input" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import tenderApi from '../../api/tenderApi'
import dayjs from 'dayjs'

export default {
  name: 'AdminProjectDetail',
  setup() {
    const route = useRoute()
    const router = useRouter()
    const id = route.params.id
    const form = ref({})
    const saving = ref(false)

    const load = async () => {
      if (!id) return
      const res = await tenderApi.getTenderProjectById(id)
      form.value = res.data
    }

    const onSave = async () => {
      try {
        saving.value = true
        const payload = { ...form.value }
        // 将 Day.js 对象转为 ISO 字符串
        const dateKeys = ['registrationStartTime','registrationEndTime','documentStartTime','documentEndTime','clarificationEndTime','bidDeadline','publishTime','createTime']
        dateKeys.forEach(k => { if (payload[k]) payload[k] = dayjs(payload[k]).format('YYYY-MM-DDTHH:mm:ss') })
        if (id) {
          await tenderApi.adminUpdateProject(id, payload)
        } else {
          await tenderApi.adminCreateProject(payload)
          router.push('/admin/projects')
        }
        ElMessage.success('保存成功')
      } catch (e) {
        ElMessage.error('保存失败')
      } finally { saving.value = false }
    }

    onMounted(load)

    return { form, onSave, saving }
  }
}
</script>

<style scoped>
.admin-project-detail{ padding:16px; }
.header{ display:flex; align-items:center; margin-bottom:12px; }
.spacer{ flex:1; }
.detail-shell{ background:#fff; border-radius:8px; box-shadow:0 2px 8px rgba(0,0,0,.06); overflow:hidden; }
.header-section{ padding:20px 20px 0; border-bottom:1px solid #eee; }
.title-input :deep(.el-input__wrapper){ box-shadow:none !important; padding:0; font-size:20px; font-weight:600; }
.title-tags{ display:flex; gap:10px; margin:12px 0 8px; }
.summary-section,.contact-section,.announcement-section{ padding:20px; border-bottom:1px solid #eee; }
.summary-section h2,.contact-section h2,.announcement-section h2{ font-size:16px; font-weight:600; margin-bottom:12px; }
.summary-grid{ display:grid; grid-template-columns:1fr 1fr; gap:14px; }
.summary-item{ display:flex; justify-content:space-between; align-items:center; }
.summary-item .label{ color:#666; min-width:120px; }
.inline-input{ width:60%; }
.inline-date{ width:60%; }
.contact-card{ background:#f8f9fb; border-radius:6px; padding:16px; }
.announcement-list{ display:flex; flex-direction:column; gap:12px; }
.announcement-item{ display:flex; justify-content:space-between; align-items:center; border-bottom:1px solid #f0f0f0; padding:10px 0; }
.announcement-item:last-child{ border-bottom:none; }
.item-label{ color:#666; min-width:140px; }
.package-section,.bidder-contact-section{ padding:20px; border-bottom:1px solid #eee; }
.package-info{ display:flex; flex-direction:column; gap:12px; }
.info-item{ display:flex; justify-content:space-between; align-items:center; border-bottom:1px solid #f0f0f0; padding:10px 0; }
.info-item:last-child{ border-bottom:none; }
.info-label{ color:#666; min-width:180px; }
.bidder-info{ display:flex; flex-direction:column; gap:12px; }
.bidder-item{ display:flex; justify-content:space-between; align-items:center; border-bottom:1px solid #f0f0f0; padding:10px 0; }
.bidder-item:last-child{ border-bottom:none; }
.bidder-label{ color:#666; min-width:100px; }
</style>


