<script setup lang="ts">

import {ref, onMounted, computed} from "vue";
import {Moon, Sunny} from "@element-plus/icons-vue";
// 主题状态
const theme = ref<'light' | 'dark'>('dark')

const themeIcon = computed(() =>
  theme.value === 'light' ? Moon : Sunny
)

onMounted(() => {
  theme.value = localStorage.getItem('theme') as 'light' | 'dark' || 'dark'
  document.documentElement.setAttribute('data-theme', theme.value)
})

// 主题切换功能
function toggleTheme() {
  const html = document.documentElement;
  const currentTheme = html.getAttribute('data-theme');
  const newTheme = currentTheme === 'light' ? 'dark' : 'light';
  theme.value = newTheme
  html.setAttribute('data-theme', newTheme);
  localStorage.setItem('theme', newTheme);
}

</script>

<template>
  <nav class="navbar">
<!--    <a href="/" class="nav-logo"></a>-->
    <a href="/" class="nav-logo">
      <span class="logo-text" data-text="kokoro">kokoro</span>
    </a>
    <ul class="nav-menu">
      <li class="nav-item"><a href="/resume" target="_blank" class="nav-link">关于</a></li>
      <li class="nav-item">
        <button class="theme-toggle" @click="toggleTheme()">
          <el-icon class="theme-icon">
            <component :is="themeIcon" />
          </el-icon>
        </button>
      </li>
    </ul>
  </nav>
</template>

<style scoped>
/* 导航栏基础样式 */
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 5%;
  height: 60px; /* 固定高度 */
  background: var(--bg-primary);
  position: sticky;
  top: 0;
  z-index: 1000;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

/* 导航菜单 */
.nav-menu {
  display: flex;
  gap: 2rem;
  list-style: none;
  margin: 0;
  padding: 0;
  height: 100%; /* 继承navbar高度 */
}

/*左上文字*/
.logo-text {
  color: var(--text-primary);
  font-family: 'Courier New', monospace;
  font-weight: 700;
  position: relative;
}
/*悬停时*/
.logo-text::after {
  content: attr(data-text);
  position: absolute;
  left: 0;
  color: var(--secondary);
  width: 0;
  overflow: hidden;
  transition: width 0.5s ease;
}
.nav-logo:hover .logo-text::after {
  width: 100%;
}

/* 导航项 */
.nav-item {
  display: flex;
  align-items: center;
  margin: auto;
  height: 50%;
  position: relative;
}

.nav-logo {
  text-decoration: none;
  display: inline-flex;
}

/* 导航链接 */
.nav-link {
  color: var(--text-primary);
  text-decoration: none;
  padding: 0.5rem 0;
  display: flex;
  align-items: center;
  height: auto;
  transition: color 0.3s ease;
}

.nav-link:hover {
  color: var(--text-primary);
}

.nav-link::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 0;
  height: 2px;
  background: var(--text-primary);
  transition: width 0.3s ease;
}

.nav-link:hover::after {
  width: 100%;
}

.theme-toggle {
  background: transparent;
  border: none;
  cursor: pointer;
  padding: 0;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  transition: all 0.3s ease;
}


.theme-toggle:hover {
  background: rgba(255, 255, 255, 0.1);
  transform: scale(1.1);
}

.theme-icon {
  color: var(--text-primary);
  font-size: 1.2rem;
  transition: transform 0.3s ease;
}

.theme-toggle:hover .theme-icon {
  transform: rotate(30deg);
}

@media (max-width: 768px) {
  .nav-menu {
    gap: 1rem;
  }

  .nav-link {
    font-size: 0.9rem;
  }

  .theme-toggle {
    width: 36px;
    height: 36px;
  }
}
</style>
