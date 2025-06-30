<template>
  <div class="topic-app">
    <!-- 类型选择器 -->
    <div class="type-selector">
      <h1>知识库导航</h1>
      <div class="selector-container">
        <button
          v-for="type in types"
          :key="type.typeId"
          @click="selectType(type.typeId)"
          :class="{ active: selectedTypeId === type.typeId }"
        >
          {{ type.name }}
        </button>
      </div>
    </div>

    <!-- 内容区域 -->
    <div class="content-container">
      <!-- 加载状态 -->
      <div v-if="loading" class="loading-spinner">
        <div class="spinner"></div>
        <p>加载中...</p>
      </div>

      <!-- 错误提示 -->
      <div v-if="error" class="error-message">
        {{ error }}
      </div>

      <!-- 直接主题 -->
      <div v-if="!loading && !error">
        <div class="section" v-if="directTopics.length > 0">
          <h2 class="section-title">
            <span class="icon">📚</span> 通用知识
          </h2>
          <div class="topic-grid">
            <div
              v-for="topic in directTopics"
              :key="'direct-'+topic.topic_id"
              class="topic-card"
            >
              <div class="topic-header">
                <h3>{{ topic.topic_name }}</h3>
              </div>
              <div class="topic-body">
                <p>{{ topic.topic_answer }}</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 标签分类主题 -->
        <div
          class="section"
          v-for="tag in tags"
          :key="'tag-'+tag.tag_id"
        >
          <h2 class="section-title">
            <span class="icon">🏷️</span> {{ tag.tag_name }}
          </h2>
          <div class="topic-grid">
            <div
              v-for="topic in tag.topics"
              :key="'tagged-'+topic.topic_id"
              class="topic-card"
            >
              <div class="topic-header">
                <h3>{{ topic.topic_name }}</h3>
              </div>
              <div class="topic-body">
                <p>{{ topic.topic_answer }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import axios from 'axios'

// 数据状态
const types = ref([])
const selectedTypeId = ref(null)
const tags = ref([])
const directTopics = ref([])
const loading = ref(false)
const error = ref(null)

// 获取所有类型
const fetchTypes = async () => {
  try {
    const response = await axios.get('http://127.0.0.1:5000/api/topic/types')
    types.value = response.data.data
    if (types.value.length > 0 && !selectedTypeId.value) {
      selectedTypeId.value = types.value[0].typeId
    }
  } catch (err) {
    error.value = '获取类型列表失败: ' + err.message
  }
}

// 获取当前选中类型的内容
const fetchContent = async () => {
  if (!selectedTypeId.value) return

  loading.value = true
  error.value = null

  try {
    const response = await axios.get(`http://127.0.0.1:5000/api/topic/topics/${selectedTypeId.value}`)
    tags.value = response.data.data.tag || []
    directTopics.value = response.data.data.topic || []
  } catch (err) {
    error.value = '加载内容失败: ' + err.message
  } finally {
    loading.value = false
  }
}

// 选择类型
const selectType = (typeId) => {
  selectedTypeId.value = typeId
}

// 初始化获取类型
onMounted(fetchTypes)

// 监听类型变化
watch(selectedTypeId, fetchContent)
</script>

<style scoped>
/* 基础样式 */
.topic-app {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: #333;
}

/* 类型选择器 */
.type-selector {
  background: linear-gradient(135deg, #6B73FF 0%, #000DFF 100%);
  padding: 2rem;
  border-radius: 12px;
  margin-bottom: 2rem;
  box-shadow: 0 4px 20px rgba(0, 13, 255, 0.15);
}

.type-selector h1 {
  color: white;
  text-align: center;
  margin-bottom: 1.5rem;
  font-size: 2rem;
  font-weight: 600;
}

.selector-container {
  display: flex;
  flex-wrap: wrap;
  gap: 0.8rem;
  justify-content: center;
}

.selector-container button {
  background: rgba(255, 255, 255, 0.2);
  border: none;
  color: white;
  padding: 0.8rem 1.5rem;
  border-radius: 50px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-weight: 500;
  font-size: 1rem;
  backdrop-filter: blur(5px);
}

.selector-container button:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: translateY(-2px);
}

.selector-container button.active {
  background: white;
  color: #000DFF;
  box-shadow: 0 4px 15px rgba(255, 255, 255, 0.3);
}

/* 内容区域 */
.content-container {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
}

.section {
  margin-bottom: 3rem;
}

.section:last-child {
  margin-bottom: 0;
}

.section-title {
  display: flex;
  align-items: center;
  gap: 0.8rem;
  color: #2c3e50;
  font-size: 1.5rem;
  margin-bottom: 1.5rem;
  padding-bottom: 0.8rem;
  border-bottom: 1px solid #eee;
}

.section-title .icon {
  font-size: 1.8rem;
}

/* 主题网格 */
.topic-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 1.5rem;
}

.topic-card {
  background: white;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  border: 1px solid #eee;
}

.topic-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.12);
}

.topic-header {
  background: #f8f9fa;
  padding: 1.2rem;
  border-bottom: 1px solid #eee;
}

.topic-header h3 {
  margin: 0;
  color: #2c3e50;
  font-size: 1.1rem;
}

.topic-body {
  padding: 1.2rem;
}

.topic-body p {
  margin: 0;
  color: #555;
  line-height: 1.5;
}

/* 加载状态 */
.loading-spinner {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 3rem;
}

.spinner {
  width: 50px;
  height: 50px;
  border: 5px solid #f3f3f3;
  border-top: 5px solid #3498db;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 1rem;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* 错误提示 */
.error-message {
  background: #ffebee;
  color: #c62828;
  padding: 1.5rem;
  border-radius: 8px;
  text-align: center;
  margin: 2rem 0;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .topic-grid {
    grid-template-columns: 1fr;
  }

  .type-selector {
    padding: 1.5rem;
  }

  .selector-container {
    gap: 0.5rem;
  }

  .selector-container button {
    padding: 0.6rem 1rem;
    font-size: 0.9rem;
  }
}
</style>
