<script setup lang="ts">

import ThemeToggle from '@/components/ThemeToggle.vue'

</script>

<template>
  <div class="navbar shadow-border-sm">
    <div class="flex-1">
      <a href="/" class="nav-logo">
        <span class="logo-text" data-text="kokoro">kokoro</span>
      </a>
    </div>
    <div class="navbar-item">
      <ul class="navbar-item-content">

        <li>
          <ThemeToggle class="icon"/>
        </li>
      </ul>
    </div>
  </div>

</template>

<style scoped>
.navbar {
  display: flex;
  width: 100%;
  align-items: center;
  min-height: 4rem;
  padding: 0.5rem;
  position: sticky;
  top: calc(var(--spacing-unit) * 0);
  z-index: 50;
  padding-inline: calc(var(--spacing-unit) * 5) /* 1.25rem = 20px */;
  @media (width >= 48rem /* 768px */) {
    padding-inline: calc(var(--spacing-unit) * 20) /* 5rem = 80px */;
  }
  box-shadow:
    0 0 0 1px var(--md-sys-color-surface-variant),
    0 0 0 1px var(--md-sys-color-surface-variant),
    0 10px 15px -3px var(--md-sys-color-surface-variant),
    0 4px 6px -4px var(--md-sys-color-surface-variant);
}


.navbar-item{
  display: flex;
  align-items: center;
  max-height: 50%;
  max-width: 50%;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  text-decoration: none;
  border-radius: 6px;
  transition: background-color 0.2s;
}

.navbar-item-content{
  display: inline-flex;
  gap: 0.5rem;
  flex-direction: row;
  padding-inline: calc(var(--spacing-unit) * 1)
}

.icon{
  width: 1.5rem;
  height: 1.5rem;
}

/*左上文字*/
.logo-text {
  font-family: 'Courier New', monospace;
  font-weight: 700;
  position: relative;
}

/*悬停时*/
.logo-text::after {
  content: attr(data-text);
  position: absolute;
  left: 0;
  color: var(--md-sys-color-primary);
  width: 0;
  overflow: hidden;
  transition: width 0.5s ease;
}

.nav-logo:hover .logo-text::after {
  width: 100%;
}

.nav-logo {
  padding: 0.5rem 1rem;
  text-decoration: none;
  display: inline-flex;
}

.flex-1 {
  flex: 1;
}
</style>
