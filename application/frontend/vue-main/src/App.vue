<script setup lang="ts">
import { RouterView } from 'vue-router'
import NavigationBar from '@/components/NavigationBar.vue'
import CopyRight from '@/components/CopyRight.vue'
import BackgroundView from '@/components/BackgroundView.vue'
</script>
<template>
  <BackgroundView />
  <div class="main-content-container">
    <NavigationBar />
    <main class="router-container">
      <RouterView />
    </main>
  </div>
  <CopyRight />
</template>

<style scoped>
.main-content-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.router-container {
  flex-grow: 1;
  position: relative;
}

</style>
