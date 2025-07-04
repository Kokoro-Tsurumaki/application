<script setup lang="ts">
import { RouterView } from 'vue-router'
import NavigationBar from '@/components/NavigationBar.vue'
import CopyRight from '@/components/CopyRight.vue'
</script>
<template>
    <div class="scrollbar-demo-item">
      <NavigationBar />
      <main class="content">
        <RouterView />
      </main>
<!--      <CopyRight />-->
    </div>
</template>

<style scoped>
</style>


