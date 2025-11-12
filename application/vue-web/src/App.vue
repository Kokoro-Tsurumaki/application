<script setup lang="ts">
import { RouterView } from 'vue-router'
import NavigationBar from '@/components/NavigationBar.vue'
import CopyRight from '@/components/CopyRight.vue'
import BackgroundView from '@/views/BackgroundView.vue'
</script>
<template>
  <BackgroundView />
  <div class="min-h-screen flex flex-col">
    <!--最小视图宽度-->
    <NavigationBar />
    <main class="content flex-grow relative">
      <RouterView />
    </main>
  </div>
  <CopyRight />
</template>

<style scoped></style>
