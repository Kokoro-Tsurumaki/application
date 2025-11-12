import { defineStore } from 'pinia'
import { ref, computed, watch } from 'vue'

export const useThemeStore = defineStore('theme', () => {
  const theme = ref<'light' | 'dark'>('dark')


  const currentTheme = computed(() => {
    return theme.value
  })

  const isDark = computed(() => currentTheme.value === 'dark')

  // 3. Actions - 普通函数
  function toggleTheme() {
    theme.value = theme.value === 'dark' ? 'light' : 'dark'
    applyTheme()
  }

  function setTheme(newTheme: 'light' | 'dark') {
    theme.value = newTheme
    applyTheme()
  }

  function applyTheme() {
    const t = currentTheme.value
    const html = document.documentElement

    // 移除旧主题类
    html.classList.remove('light', 'dark')
    // 添加新主题类
    html.classList.add(t)

    // 设置 data-theme 属性
    html.setAttribute('data-theme', t)

    // 更新 meta theme-color
    updateMetaThemeColor(t)
  }

  function updateMetaThemeColor(theme: 'light' | 'dark') {
    const themeColor = theme === 'dark' ? '#1a1a1a' : '#ffffff'
    let metaThemeColor = document.querySelector('meta[name="theme-color"]')

    if (!metaThemeColor) {
      metaThemeColor = document.createElement('meta')
      metaThemeColor.setAttribute('name', 'theme-color')
      document.head.appendChild(metaThemeColor)
    }

    metaThemeColor.setAttribute('content', themeColor)
  }

  function watchSystemTheme() {
    const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)')

    const handleChange = () => {
      applyTheme()
    }

    mediaQuery.addEventListener('change', handleChange)

    // 返回清理函数
    return () => mediaQuery.removeEventListener('change', handleChange)
  }

  // 4. 初始化逻辑
  function initialize() {
    applyTheme() // 应用初始主题
    const cleanup = watchSystemTheme()

    // 监听主题变化自动应用
    watch(currentTheme, () => {
      applyTheme()
    })

    return cleanup
  }

  // 5. 返回所有需要暴露的内容
  return {
    theme,
    currentTheme,
    isDark,
    toggleTheme,
    setTheme,
    applyTheme,
    watchSystemTheme,
    initialize
  }
}, {
  persist: {
    key: 'app-theme',
    storage: localStorage,
  }
})
