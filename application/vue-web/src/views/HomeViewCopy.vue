<template>
 <div class=" h-full flex justify-center absolute inset-0">
  <v-md-editor :model-value="textareaRef" class="h-full"></v-md-editor>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
const textareaRef = ref('# 标题')
</script>

<style scoped>



</style>
