<script setup lang="ts">
import {initializeTitle} from '@/core/document_ui.ts'
initializeTitle("首页")
// 功能模块配置（避免硬编码）
const modules = [
  {
    id: 'yuxin-user',
    title: '雨昕Dev用户',
    icon: 'database',
    path: '/yuxin-user',
    glowColor: '#6B5AED'
  },
  {
    id: 'pgyer-manage',
    title: 'Apk管理',
    icon: 'portal',
    path: '/pgyer-manage',
    glowColor: '#FF4D4D'
  },
  // {
  //   id: 'topic',
  //   title: '题目',
  //   icon: 'portal',
  //   path: '/topic',
  //   glowColor: '#FF4D4D'
  // }
]


</script>

<template>
  <title>首页-kokoro</title>
  <div class="quantum-container">
    <div class="stellar-background">
      <div class="quantum-tunnel"></div>
    </div>

    <nav class="hologram-matrix">
      <router-link
        target="_blank"
        v-for="module in modules"
        :key="module.id"
        :to="module.path"
        class="quantum-card"
        :style="{ '--glow-color': module.glowColor }"
      >
        <div class="icon-wrapper">
          <font-awesome-icon :icon="`fa-solid fa-${module.icon}`" class="portal-icon"/>
        </div>
        <div class="data-stream"></div>
        <h3>{{ module.title }}</h3>
      </router-link>
    </nav>
  </div>
</template>

<style scoped>
/* 基础样式 */
.quantum-container {
  margin: auto;
  width: 80vw;
  padding: env(safe-area-inset-top) env(safe-area-inset-right)
  env(safe-area-inset-bottom) env(safe-area-inset-left);
  min-height: 100vh;
  overflow-x: hidden;
}

/* 网格布局 */
.hologram-matrix {

  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 2rem;
  padding: 1rem;
  position: relative;
  z-index: 2;
}

/* 卡片样式 */
.quantum-card {
  transition: all 0.3s;
  position: relative;
  height: 160px;
  min-width: 0;
  background: rgba(26,26,46,0.7);
  border: 1px solid var(--accent);
  border-radius: 16px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

/* 图标和文字 */
.portal-icon {
  font-size: 2.5rem;
  color: var(--accent);
  filter: drop-shadow(0 0 8px var(--accent));
}

h3 {
  font-size: 1.2rem;
  margin-top: 1rem;
  color: white;
}

/* 动画效果 */
.data-stream {
  position: absolute;
  bottom: 0;
  height: 4px;
  width: 100%;
  background: linear-gradient(90deg, transparent 0%, var(--accent) 50%, transparent 100%);
  animation: data-flow 2s linear infinite;
}


.quantum-tunnel {
  position: fixed;
  width: 100vw;
  height: 100vh;
  background: linear-gradient(45deg, rgba(43,210,255,0.05) 0%, rgba(107,90,237,0.05) 50%, rgba(255,77,77,0.05) 100%);
  animation: tunnel-flow 60s linear infinite;
}

/* 交互效果 */
.quantum-card:active {
  transform: scale(0.98) translateY(-5px);
}

@media (hover: hover) {
  .quantum-card:hover {
    transform: translateY(-10px) rotateX(5deg);
    box-shadow: 0 0 40px var(--accent);
  }
}

/* 桌面端适配 */
@media (min-width: 768px) {
  .hologram-matrix {
    grid-template-columns: repeat(3, 1fr);
    padding: 8rem;
    gap: 4rem;
  }

  .quantum-card {
    height: 200px;
  }

  .portal-icon {
    font-size: 4rem;
  }

  .quantum-tunnel {
    width: 150vw;
    height: 150vh;
    animation-duration: 30s;
  }
}

/* 动画定义 */
@keyframes tunnel-flow {
  from { transform: rotate(0deg) translateX(-50%); }
  to { transform: rotate(360deg) translateX(-50%); }
}

@keyframes data-flow {
  from { background-position: -200% 0; }
  to { background-position: 200% 0; }
}
</style>
