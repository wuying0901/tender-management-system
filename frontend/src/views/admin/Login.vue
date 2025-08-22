<template>
  <div class="admin-login">
    <el-card class="login-card" shadow="always">
      <div class="card-header">
        <div class="brand-badge">正望</div>
        <h2 class="title">后台登录</h2>
        <p class="subtitle">请输入管理员账号密码</p>
      </div>
      <el-form :model="form" @keyup.enter.native="onSubmit" class="login-form">
        <el-form-item>
          <el-input v-model="form.username" size="large" placeholder="用户名" clearable autocomplete="username">
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" size="large" type="password" placeholder="密码" show-password autocomplete="current-password">
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button class="submit-btn" type="primary" size="large" :loading="loading" @click="onSubmit">登录</el-button>
        </el-form-item>
      </el-form>
      <div class="footer-note">仅限授权管理员使用</div>
    </el-card>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import tenderApi from '../../api/tenderApi'
import { User, Lock } from '@element-plus/icons-vue'

export default {
  name: 'AdminLogin',
  setup() {
    const router = useRouter()
    const form = ref({ username: '', password: '' })
    const loading = ref(false)

    const onSubmit = async () => {
      if (!form.value.username || !form.value.password) {
        ElMessage.warning('请输入用户名和密码')
        return
      }
      loading.value = true
      try {
        const res = await tenderApi.adminLogin(form.value)
        localStorage.setItem('admin_token', res.data.token)
        ElMessage.success('登录成功')
        router.push('/admin/projects')
      } catch (e) {
        ElMessage.error('登录失败')
      } finally {
        loading.value = false
      }
    }

    return { form, loading, onSubmit, User, Lock }
  }
}
</script>

<style scoped>
.admin-login {
  position: fixed;
  inset: 0;               /* 覆盖整屏 */
  display: grid;          /* 更稳妥的居中方式，避免外层样式干扰 */
  place-items: center;
  background: linear-gradient(135deg, #e6eeff 0%, #f3f6ff 100%);
  padding: 24px;
}
.login-card {
  width: 420px;
  max-width: 92vw;
  border-radius: 14px;
  box-shadow: 0 12px 30px rgba(31, 45, 61, 0.12);
}
.card-header { text-align:center; margin-bottom: 10px; }
.brand-badge {
  width: 44px; height: 44px; border-radius: 50%;
  margin: 6px auto 8px; display:flex; align-items:center; justify-content:center;
  color: #fff; font-weight: 700; letter-spacing: 1px;
  background: linear-gradient(135deg, #5b8cff 0%, #3a7bff 100%);
}
.title {
  margin: 6px 0 4px;
  text-align: center;
  font-weight: 700;
  color: #1f2d3d;
}
.subtitle {
  margin: 0 0 18px;
  text-align: center;
  color: #8c8c8c;
  font-size: 13px;
}
.login-form {
  padding-top: 6px;
}
.submit-btn {
  width: 100%;
}
.footer-note { margin-top: 4px; text-align:center; color:#b3b3b3; font-size:12px; }
</style>


