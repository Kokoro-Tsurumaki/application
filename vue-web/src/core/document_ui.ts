import { onMounted } from 'vue'

export function initializeTitle(title: string) {
  onMounted(() => {
    document.title = title
  })
}
