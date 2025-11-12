<script setup lang="ts">
import { Plus } from '@element-plus/icons-vue'
import { onMounted, ref, computed } from 'vue'
import Dialog from '@/views/Dialog/Dialog.vue'
import {
  addConfigApi,
  checkVersionApi,
  type Config,
  deleteConfigApi,
  getConfigsApi,
} from '@/net/api/pgyer.ts'
import qr from '@/assets/pgyer-manage-qr.png'

const addUserDialogVisible = ref(false)
const qrCodeVisible = ref(false)
const loading = ref(false)
const error = ref<string | null>(null)
const isSubmitting = ref(false)
const configs = ref<Config[]>([])
const newConfig = ref<Config>({
  config_id: 0,
  name: '',
  apikey: '',
  appkey: '',
})
const deletingId = ref<number | null>(null)
// 当前选中的预览
const currentCheckId = ref<number | null>(null)
const setCheckId = (id: number) => {
  currentCheckId.value = id
}
// 计算当前选中的配置
const currentConfig = computed((): Config | undefined => {
  return configs.value.find((config) => config.config_id === currentCheckId.value)
})

// 获取用户列表
const fetchConfigs = async () => {
  try {
    loading.value = true
    error.value = null
    const response = await getConfigsApi()
    configs.value = response.data
    for (const item of configs.value) {
      const version = await checkVersionApi({ _api_key: item.apikey, appKey: item.appkey })
      console.log('配置数据:', version) // 调试
      if (version.code == 0) {
        item.buildBuildVersion = version.data.buildBuildVersion
        item.downloadURL = version.data.downloadURL
        item.buildVersion = version.data.buildVersion
        item.buildVersionNo = version.data.buildVersionNo
        item.buildUpdateDescription = version.data.buildUpdateDescription
        item.buildFileKey = version.data.buildFileKey
      }
    }
  } catch (err) {
    error.value = '获取用户列表失败'
    console.error('Error fetching users:', err)
  } finally {
    loading.value = false
  }
}
// 添加用户
const handleSubmit = async () => {
  try {
    isSubmitting.value = true
    const response = await addConfigApi(newConfig.value)
    if (response.code == 200) {
      addUserDialogVisible.value = false
      // 清空表单
      newConfig.value = { config_id: 0, name: '', apikey: '', appkey: '' }
      // 刷新列表
      await fetchConfigs()
    } else {
      // ElMessage.error(response.message)
    }
  } catch (err) {
    console.error('Error Add User:', err)
    error.value = '添加用户失败'
  } finally {
    isSubmitting.value = false
  }
}

// 删除用户
const deleteConfig = async (config_id: number) => {
  if (!confirm('确定要删除这个用户吗？')) return

  try {
    deletingId.value = config_id
    await deleteConfigApi(config_id)
    // 删除成功后更新本地数据
    configs.value = configs.value.filter((config) => config.config_id !== config_id)
  } catch (err) {
    error.value = '删除用户失败'
    console.error('Error deleting user:', err)
  } finally {
    deletingId.value = null
  }
}

const downloadFile = (url: string, fileName: string = 'app.apk') => {
  //android原生
  if (window.AndroidApp && typeof window.AndroidApp.downloadFile === 'function') {
    // 调用原生 Android 方法
    window.AndroidApp.downloadFile(url, fileName);
    return;
  }
  // 创建唯一ID防止重复
  const downloadId = `download-${Date.now()}`
  const a = document.createElement('a')
  a.id = downloadId

  // 设置下载属性（兼容移动端）
  a.href = url
  a.download = fileName
  a.style.display = 'none'
  a.target = '_blank' // 解决iOS限制

  // 清理历史节点
  const oldNodes = document.querySelectorAll('a[id^="download-"]')
  oldNodes.forEach((node) => document.body.removeChild(node))

  // 事件监听改进
  const cleanup = () => {
    a.removeEventListener('click', cleanup)
    a.removeEventListener('error', cleanup)
    setTimeout(() => {
      document.body.removeChild(a)
      if (url.startsWith('blob:')) URL.revokeObjectURL(url)
    }, 1000)
  }

  a.addEventListener('click', cleanup)
  a.addEventListener('error', cleanup)

  // 触发下载
  document.body.appendChild(a)

  if (navigator.userAgent.match(/iPhone|iPad|iPod/i)) {
    // iOS特殊处理
    window.open(url, '_blank')
  } else {
    const event = new MouseEvent('click', {
      view: window,
      bubbles: true,
      cancelable: true,
    })
    a.dispatchEvent(event)
  }
}

// 初始化加载数据
onMounted(() => {
  fetchConfigs()
})
</script>

<template>
  <!--  <button class="fixed-toggle" @click="addUserDialogVisible = true">-->
  <!--    <Plus />-->
  <!--  </button>-->
  <!--  <button class="fixed-toggle-secondary" @click="qrCodeVisible = true">-->
  <!--    <img :src="qr" alt="Logo" class="auto-shrink-img">-->
  <!--  </button>-->
  <!--  <Dialog v-if="qrCodeVisible" @click.self="qrCodeVisible = false">-->
  <!--    <img :src="qr" alt="Logo" class="auto-shrink-img-detail">-->
  <!--  </Dialog >-->
  <!--  <Dialog v-if="addUserDialogVisible" @click.self="addUserDialogVisible = false">-->
  <!--    <h2>添加新配置</h2>-->
  <!--    <form @submit.prevent="handleSubmit">-->
  <!--      <div class="form-group">-->
  <!--        <label for="name">应用名称</label>-->
  <!--        <input v-model="newConfig.name" type="text" id="name" required />-->
  <!--      </div>-->
  <!--      <div class="form-group">-->
  <!--        <label for="apikey">apikey</label>-->
  <!--        <input v-model="newConfig.apikey" type="text" id="apikey" required />-->
  <!--      </div>-->
  <!--      <div class="form-group">-->
  <!--        <label for="appkey">appkey</label>-->
  <!--        <input v-model="newConfig.appkey" type="text" id="appkey" required />-->
  <!--      </div>-->
  <!--      <button class="add-btn" type="submit" :disabled="isSubmitting">-->
  <!--        {{ isSubmitting ? '提交中...' : '添加配置' }}-->
  <!--      </button>-->
  <!--    </form>-->
  <!--  </Dialog>-->
    <div v-if="loading" class="bg-base-200 h-full flex justify-center absolute inset-0">
      <span class="loading loading-spinner loading-xl"></span>
    </div>

    <div v-else-if="configs.length === 0" class="h-full flex justify-center absolute inset-0">
      <div class="card w-200 h-auto bg-base-100 justify-center items-center">
        <img src="@/assets/empty.svg" alt="无数据" width="120" />
        <p>暂无数据</p>
        <button class="btn btn-primary" @click="fetchConfigs">刷新数据</button>
      </div>
    </div>
    <!-- 正常显示 -->
    <div v-else class="h-full w-full container responsive-nav">
      <ul class="hidden md:flex menu bg-base-200 w-56 ">
        <li v-for="config in configs" :key="config.config_id">
          <a
            @click="setCheckId(config.config_id)"
            :class="{ 'menu-active': currentCheckId === config.config_id }"
            >{{ config.name }}</a
          >
        </li>
      </ul>
      <div role="tablist" class="md:hidden tabs tabs-box mx-auto">
        <a role="tab" class="tab" v-for="config in configs" :key="config.config_id"  @click="setCheckId(config.config_id)"
           :class="{ 'tab-active': currentCheckId === config.config_id }">{{ config.name }}</a>
      </div>


      <div v-if="currentCheckId != null && currentConfig" class="md:mockup-phone mx-auto my-auto">
        <div class="md:mockup-phone-camera"></div>
        <div class="mockup-phone-display flex text-black place-content-start bg-white overflow-y-auto">
          <div class="mx-2 my-20 w-full h-auto">
            <h1 class="text-3xl my-2 mx-3">Pgyer build details</h1>
            <h2 class="text-2xl my-2 mx-3">{{ currentConfig.name }}</h2>

            <p class="mb-2 mt-4 mx-3 text-stone-500">Build details</p>
            <div class="card shadow-sm py-2">
              <p class="mx-3 my-3"
                v-if="currentConfig?.buildVersion != null && currentConfig?.buildVersion.trim() !== ''">
                buildVersion：{{ currentConfig.buildVersion }}
              </p>
              <p class="mx-3 my-3" v-if="currentConfig.buildVersionNo != null && currentConfig.buildVersionNo.trim() !== ''">
                buildVersionNo：{{ currentConfig.buildVersionNo }}
              </p>
            </div>

            <p class="mb-2 mt-4 mx-3 text-stone-500">Pgyer details</p>
            <div class="card shadow-sm py-2">
              <p class="mx-3 my-3" v-if="currentConfig.buildBuildVersion != null && currentConfig.buildBuildVersion.trim() !== ''">
                蒲公英构建版本：{{ currentConfig.buildBuildVersion }}
              </p>
              <p class="mx-3 my-3" v-if="currentConfig.buildUpdateDescription != null &&currentConfig.buildUpdateDescription.trim() !== ''">
                构建描述
              </p>
              <p class="detail mb-3 mx-3" v-if="currentConfig.buildUpdateDescription != null &&currentConfig.buildUpdateDescription.trim() !== ''">
                {{ currentConfig.buildUpdateDescription}}
              </p>
            </div>

            <div class="card my-5 pb-10">
              <button
                class="btn btn-primary"
                v-if="currentConfig.downloadURL != null && currentConfig.downloadURL.trim() !== ''"
                @click="downloadFile( currentConfig.downloadURL,currentConfig.buildFileKey ?? 'yuxinzhihui.apk',)">
                下载
              </button>
            </div>
          </div>
          </div>
        </div>
    </div>
</template>

<style scoped>
.detail {
  white-space: pre-wrap;
  word-break: break-all;
  font-size: 0.85em;
}
.container {
  display: flex;
  flex-direction: row;
}
@media (max-width: 768px) {
  .container {
    display: flex;
    flex-direction: column;
  }
}
</style>
