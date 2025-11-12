<script setup lang="ts">

</script>

<template>
  <div class="navbar bg-base-100 shadow-sm sticky top-0 z-50 px-5 md:px-20">
    <div class="flex-1">
      <a href="/" class="nav-logo">
        <span class="logo-text bg-base-100" data-text="kokoro">kokoro</span>
      </a>
    </div>
    <div class="flex-none">
      <ul class="menu menu-horizontal px-1">
<!--        <li class="nav-item"><a href="/resume" target="_blank" class="nav-link">关于</a></li>-->
<!--        <li>-->
<!--          <details>-->
<!--            <summary>App管理</summary>-->
<!--            <ul class="bg-base-100 rounded-t-none p-2 w-35">-->
<!--              <li><a href="/yuxin-user"  target="_blank" >Dev用户管理</a></li>-->
<!--              <li><a href="/pgyer-manage"  target="_blank">蒲公英分发</a></li>-->
<!--            </ul>-->
<!--          </details>-->
<!--        </li>-->
        <li>
          <label class="swap swap-rotate">
            <!-- this hidden checkbox controls the state -->
            <input type="checkbox" class="theme-controller" value="light" />

            <!-- sun icon -->
            <svg
              class="swap-on h-5 w-5 fill-current"
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
            >
              <path
                d="M5.64,17l-.71.71a1,1,0,0,0,0,1.41,1,1,0,0,0,1.41,0l.71-.71A1,1,0,0,0,5.64,17ZM5,12a1,1,0,0,0-1-1H3a1,1,0,0,0,0,2H4A1,1,0,0,0,5,12Zm7-7a1,1,0,0,0,1-1V3a1,1,0,0,0-2,0V4A1,1,0,0,0,12,5ZM5.64,7.05a1,1,0,0,0,.7.29,1,1,0,0,0,.71-.29,1,1,0,0,0,0-1.41l-.71-.71A1,1,0,0,0,4.93,6.34Zm12,.29a1,1,0,0,0,.7-.29l.71-.71a1,1,0,1,0-1.41-1.41L17,5.64a1,1,0,0,0,0,1.41A1,1,0,0,0,17.66,7.34ZM21,11H20a1,1,0,0,0,0,2h1a1,1,0,0,0,0-2Zm-9,8a1,1,0,0,0-1,1v1a1,1,0,0,0,2,0V20A1,1,0,0,0,12,19ZM18.36,17A1,1,0,0,0,17,18.36l.71.71a1,1,0,0,0,1.41,0,1,1,0,0,0,0-1.41ZM12,6.5A5.5,5.5,0,1,0,17.5,12,5.51,5.51,0,0,0,12,6.5Zm0,9A3.5,3.5,0,1,1,15.5,12,3.5,3.5,0,0,1,12,15.5Z"
              />
            </svg>

            <!-- moon icon -->
            <svg
              class="swap-off h-5 w-5 fill-current"
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
            >
              <path
                d="M21.64,13a1,1,0,0,0-1.05-.14,8.05,8.05,0,0,1-3.37.73A8.15,8.15,0,0,1,9.08,5.49a8.59,8.59,0,0,1,.25-2A1,1,0,0,0,8,2.36,10.14,10.14,0,1,0,22,14.05,1,1,0,0,0,21.64,13Zm-9.5,6.69A8.14,8.14,0,0,1,7.08,5.22v.27A10.15,10.15,0,0,0,17.22,15.63a9.79,9.79,0,0,0,2.1-.22A8.11,8.11,0,0,1,12.14,19.73Z"
              />
            </svg>
          </label>
        </li>
      </ul>
    </div>
  </div>

</template>

<style scoped>
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
  color: var(--color-primary);
  width: 0;
  overflow: hidden;
  transition: width 0.5s ease;
}
.nav-logo:hover .logo-text::after {
  width: 100%;
}

.nav-logo {
  text-decoration: none;
  display: inline-flex;
}
</style>
