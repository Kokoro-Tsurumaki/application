<script setup lang="ts">
// import {initializeTitle} from '@/core/document_ui.ts'
// initializeTitle("首页")
const datas = [
  { title: '标题1', detail: '内容1' },
  { title: '标题2', detail: '内容2' },
  { title: '标题3', detail: '内容3' },
  { title: '标题3', detail: '内容3' },
  { title: '标题3', detail: '内容3' },
  { title: '标题3', detail: '内容3' },
]
</script>

<template>
  <ul class="list bg-base-100 rounded-box shadow-md mx-auto">
    <li class="list-row mx-auto" v-for="data in datas" :key="data.title" >
      <div class="card w-96 bg-base-100 card-md shadow-sm">
        <div class="card-body">
          <h2 class="card-title">{{data.title}}</h2>
          <p>
          {{data.detail}}
          </p>
          <div class="justify-end card-actions">
            <button class="btn btn-primary">Go Page</button>
          </div>
        </div>
      </div>
    </li>
  </ul>
</template>

<style scoped></style>
