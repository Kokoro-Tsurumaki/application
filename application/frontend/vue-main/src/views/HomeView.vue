<template>
  <div class="blog-list">
    <!-- 标签筛选区域 -->
    <div class="filter-section">
      <h2 class="section-title">博客文章</h2>
      <div class="tag-filters">
        <button
          v-for="tag in allTags"
          :key="tag"
          class="tag-filter"
          :class="{ active: activeTag === tag }"
          @click="toggleTag(tag)"
        >
          {{ tag }}
        </button>
        <button
          class="tag-filter clear"
          :class="{ active: activeTag === null }"
          @click="clearFilter"
        >
          全部
        </button>
      </div>
    </div>

    <!-- 博客列表 -->
    <div class="blog-items">
      <article
        v-for="blog in filteredBlogs"
        :key="blog.id"
        class="blog-item"
        @mouseenter="setActiveBlog(blog.id)"
        @mouseleave="setActiveBlog(null)"
      >
        <!-- 标题和元信息 -->
        <div class="blog-header">
          <h3 class="blog-title">{{ blog.title }}</h3>
          <div class="blog-meta">
            <span class="create-time">
              创建: {{ formatDate(blog.createdAt) }}
            </span>
            <span
              v-if="blog.updatedAt !== blog.createdAt"
              class="update-time"
            >
              更新: {{ formatDate(blog.updatedAt) }}
            </span>
          </div>
        </div>

        <!-- 提示信息（悬停显示） -->
        <transition name="hint-fade">
          <div
            v-show="activeBlogId === blog.id"
            class="blog-hint"
          >
            {{ blog.hint }}
          </div>
        </transition>

        <!-- 标签 -->
        <div class="blog-tags">
          <span
            v-for="tag in blog.tags"
            :key="tag"
            class="blog-tag"
            @click="toggleTag(tag)"
          >
            {{ tag }}
          </span>
        </div>
      </article>
    </div>

    <!-- 空状态 -->
    <div v-if="filteredBlogs.length === 0" class="empty-state">
      <p>暂无相关博客文章</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'

// 类型定义
interface Blog {
  id: number
  title: string
  hint: string
  content: string
  createdAt: Date
  updatedAt: Date
  tags: string[]
}

// 响应式数据
const blogs = ref<Blog[]>([])
const activeBlogId = ref<number | null>(null)
const activeTag = ref<string | null>(null)

// 计算属性
const allTags = computed(() => {
  const tags = new Set<string>()
  blogs.value.forEach(blog => {
    blog.tags.forEach(tag => tags.add(tag))
  })
  return Array.from(tags).sort()
})

const filteredBlogs = computed(() => {
  if (!activeTag.value) {
    return blogs.value
  }
  return blogs.value.filter(blog => blog.tags.includes(activeTag.value as string))
})

// 方法
const setActiveBlog = (id: number | null) => {
  activeBlogId.value = id
}

const toggleTag = (tag: string) => {
  activeTag.value = activeTag.value === tag ? null : tag
}

const clearFilter = () => {
  activeTag.value = null
}

const formatDate = (date: Date): string => {
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
}

// 模拟数据获取
const fetchBlogs = async (): Promise<Blog[]> => {
  // 模拟 API 调用延迟
  await new Promise(resolve => setTimeout(resolve, 500))

  return [
    {
      id: 1,
      title: 'Kotlin的声明与Java不同，Java的声明是类型跟变量名，常量用final修饰，而Kotlin的声明是通变var(常量)，val(常量)，而它的类型在变量名后，如下：val number:int = 200',
      hint: 'Kotlin的声明与Java不同，Java的声明是类型跟变量名，常量用final修饰，而Kotlin的声明是通变var(常量)，val(常量)，而它的类型在变量名后，如下：val number:int = 200\n' +
        '\n' +
        'Kotlin有类型推导的功能：如果某个变量或常量的值可以确定他的类型，那么可以不用定义类型，\n' +
        '\n' +
        '\n' +
        '   在Java中声明对象需要通过new，如：People people = new People()。而在Kotlin中的对象声明可以直接声明，如：val people = People()。\n' +
        '\n' +
        '   在kotlin中的继承与实现都是通过：来实现，类是:T()接口是:T。\n' +
        '\n' +
        '关于属性赋值，getter () = 会使每次获取都是新的对象。',
      content: '...',
      createdAt: new Date('2024-01-15'),
      updatedAt: new Date('2024-01-20'),
      tags: ['Vue', 'JavaScript', '前端']
    },
    {
      id: 2,
      title: 'TypeScript 类型系统进阶',
      hint: '掌握 TypeScript 高级类型和泛型编程',
      content: '...',
      createdAt: new Date('2024-01-10'),
      updatedAt: new Date('2024-01-10'),
      tags: ['TypeScript', '编程']
    },
    {
      id: 3,
      title: '现代 CSS 布局技术',
      hint: 'Flexbox、Grid 和容器查询等现代布局方案',
      content: '...',
      createdAt: new Date('2024-01-05'),
      updatedAt: new Date('2024-01-08'),
      tags: ['CSS', '前端', '布局']
    },
    {
      id: 4,
      title: '响应式设计原理与实践',
      hint: '构建适配多设备的响应式网页',
      content: '...',
      createdAt: new Date('2024-01-03'),
      updatedAt: new Date('2024-01-03'),
      tags: ['响应式', 'CSS', '前端']
    },
    {
      id: 5,
      title: '前端性能优化指南',
      hint: '提升网页加载速度和运行时性能的实用技巧',
      content: '...',
      createdAt: new Date('2023-12-28'),
      updatedAt: new Date('2024-01-02'),
      tags: ['性能', 'JavaScript', '优化']
    }
  ]
}

// 生命周期
onMounted(async () => {
  blogs.value = await fetchBlogs()
})
</script>

<style scoped>
.blog-list {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem 1rem;
}

/* 筛选区域样式 */
.filter-section {
  margin-bottom: 2rem;
  border-bottom: 1px solid var(#e1e5e9);
  padding-bottom: 1rem;
}

.section-title {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 1rem;
  color: var(#1a1a1a);
}

.tag-filters {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.tag-filter {
  padding: 0.375rem 0.75rem;
  border: 1px solid var(--border-color, #e1e5e9);
  border-radius: 1.5rem;
  background: transparent;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 0.875rem;
  color: var(--text-secondary, #666);
}

.tag-filter:hover {
  border-color: var(--primary-color, #3b82f6);
  color: var(--primary-color, #3b82f6);
  transform: translateY(-1px);
}

.tag-filter.active {
  background: var(--primary-color, #3b82f6);
  border-color: var(--primary-color, #3b82f6);
  color: white;
}

.tag-filter.clear {
  font-weight: 500;
}

/* 博客列表样式 */
.blog-items {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.blog-item {
  border: 1px solid var(--border-color, #e1e5e9);
  border-radius: 0.5rem;
  padding: 1.5rem;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.blog-item:hover {
  border-color: var(--primary-color, #3b82f6);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.blog-header {
  margin-bottom: 0.5rem;
}

.blog-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
  line-height: 1.4;
}

.blog-meta {
  display: flex;
  gap: 1rem;
  font-size: 0.875rem;
  color: var(--text-secondary, #666);
}

.create-time, .update-time {
  opacity: 0.8;
}

/* 提示信息动画 */
.blog-hint {
  margin: 0.75rem 0;
  padding: 0.75rem;
  background: var(--surface-secondary, #f8f9fa);
  border-radius: 0.375rem;
  font-size: 0.9rem;
  line-height: 1.5;
  color: var(--text-secondary, #666);
  border-left: 3px solid var(--primary-color, #3b82f6);
}

/* 提示淡入淡出动画 */
.hint-fade-enter-active {
  transition: all 0.3s ease-out;
}

.hint-fade-leave-active {
  transition: all 0.2s ease-in;
}

.hint-fade-enter-from {
  opacity: 0;
  transform: translateY(-10px);
  max-height: 0;
  margin-top: 0;
  margin-bottom: 0;
  padding-top: 0;
  padding-bottom: 0;
}

.hint-fade-enter-to {
  opacity: 1;
  transform: translateY(0);
  max-height: 100px;
}

.hint-fade-leave-from {
  opacity: 1;
  max-height: 100px;
}

.hint-fade-leave-to {
  opacity: 0;
  max-height: 0;
  margin-top: 0;
  margin-bottom: 0;
  padding-top: 0;
  padding-bottom: 0;
}

/* 标签样式 */
.blog-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-top: 1rem;
}

.blog-tag {
  padding: 0.25rem 0.5rem;
  background: var(--surface-secondary, #f8f9fa);
  border-radius: 0.25rem;
  font-size: 0.75rem;
  color: var(--text-secondary, #666);
  cursor: pointer;
  transition: all 0.2s ease;
}

.blog-tag:hover {
  background: var(--primary-color, #3b82f6);
  color: white;
  transform: scale(1.05);
}

/* 空状态 */
.empty-state {
  text-align: center;
  padding: 3rem;
  color: var(--text-secondary, #666);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .blog-list {
    padding: 1rem 0.5rem;
  }

  .blog-item {
    padding: 1rem;
  }

  .blog-meta {
    flex-direction: column;
    gap: 0.25rem;
  }

  .tag-filters {
    gap: 0.25rem;
  }

  .tag-filter {
    padding: 0.25rem 0.5rem;
    font-size: 0.8rem;
  }
}

/* 加载动画 */
.blog-item {
  animation: fadeInUp 0.5s ease-out;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 为每个项目添加延迟动画 */
.blog-item:nth-child(1) { animation-delay: 0.1s; }
.blog-item:nth-child(2) { animation-delay: 0.2s; }
.blog-item:nth-child(3) { animation-delay: 0.3s; }
.blog-item:nth-child(4) { animation-delay: 0.4s; }
.blog-item:nth-child(5) { animation-delay: 0.5s; }
</style>
