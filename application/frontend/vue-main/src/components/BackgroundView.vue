<script setup lang="ts">
// 粒子系统相关
import {onMounted, type Ref, ref} from "vue";

interface Star{
  size:number,
  left:number
  top:number
  delay:number
  opacity:number
}

// 存储星星数据
const stars:Ref<Star[]> = ref([]);

// 初始化星星
function initParticles() {
  const newStars:Star[] = [];
  for (let i = 0; i < 200; i++) {
    newStars.push({
      size: Math.random() * 3,
      left: Math.random() * 100,
      top: Math.random() * 100,
      delay: Math.random() * 5,
      opacity: 0.3 + Math.random() * 0.7
    });
  }
  stars.value = newStars;
}

onMounted(() => {
  initParticles();
});
</script>

<template>
  <div class="stars-container" ref="starsContainer">
    <div
      v-for="(star, index) in stars"
      :key="index"
      class="star"
      :style="{
          width: star.size + 'px',
          height: star.size + 'px',
          left: star.left + '%',
          top: star.top + '%',
          animationDelay: star.delay + 's',
          opacity: star.opacity
        }"
    ></div>
  </div>
</template>

<style scoped>
.stars-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
}
.star {
  position: absolute;
  background: white;
  border-radius: 50%;
  opacity: 0.7;
  animation: twinkle 3s infinite ease-in-out;
  /* 修复：确保使用正确的定位方式 */
  transform: translateZ(0); /* 启用GPU加速 */
}

/* 添加更真实的星星闪烁动画 */
@keyframes twinkle {
  0%, 100% {
    opacity: 0.3;
    transform: scale(1);
  }
  50% {
    opacity: 1;
    transform: scale(1.1);
  }
}
</style>
