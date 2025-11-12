<script setup lang="ts">
import { useThemeStore } from '@/stores/themeStore'
import { onMounted, onUnmounted } from 'vue'
import IconSun from '@/components/icons/IconSun.vue'
import IconMoon from '@/components/icons/IconMoon.vue'

const themeStore = useThemeStore()

// 初始化主题
onMounted(() => {
  // 初始化主题监听
  const cleanup = themeStore.initialize()

  onUnmounted(() => {
    cleanup() // 清理监听器
  })
})
</script>

<template>
  <div @click="themeStore.toggleTheme()">
        <span v-if="themeStore.currentTheme === 'light'"> <IconSun /></span>
        <span v-else-if="themeStore.currentTheme === 'dark'"> <IconMoon /></span>
  </div>
</template>

<style scoped>

</style>
