<script setup lang="ts">

</script>

<template>
  <footer class="footer">
    <aside>
      <div>
        <p>© 2025 kokoro - Content licensed under the <a
          href="https://creativecommons.org/licenses/by-nc/4.0/" rel="noopener"
          class="link link-hover">CC BY-NC 4.0 </a></p>
        <div>
          <a href="#" onclick="location.href='mailto:'+'xianjie.zhang516'+'@gmail.com'"
             rel="noopener" class="link link-hover"> xianjie.zhang516@gmail.com </a>
          <a href="https://beian.miit.gov.cn" target="_blank" rel="noopener"
             class="link link-hover">苏ICP备2025171499号</a>
        </div>
      </div>
    </aside>
  </footer>
</template>
<style scoped>

.footer {
  padding: calc(var(--spacing-unit) * 4) /* 1rem = 16px */;
  padding-block: calc(var(--spacing-unit) * 12) /* 3rem = 48px */;
  display: grid;
  width: 100%;
  column-gap: calc(0.25rem/* 4px */ * 4) /* 1rem = 16px */;
  row-gap: calc(0.25rem/* 4px */ * 10) /* 2.5rem = 40px */;
  font-size: 0.875rem /* 14px */;
  line-height: 1.25rem /* 20px */;
  grid-auto-flow: column dense;
  place-items: center;
  text-align: center;
}
</style>
