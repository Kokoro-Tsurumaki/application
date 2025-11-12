import { createRouter, createWebHistory } from 'vue-router'
// import HomeView from '../views/HomeView.vue'
import ResumeView from '../views/ResumeView.vue'
import HomeView from '@/views/HomeView.vue'
import TopicsView from '@/views/TopicsView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/resume',
      name: 'resume',
      component: ResumeView,
    },
    {
      path: '/topics',
      name: 'topics',
      component: TopicsView,
    },
    // {
    //   path: '/yuxin-user',
    //   name: 'yuxin-user',
    //   component: () => import('@/views/YuxinUserView.vue'),
    // },
    {
      path: '/pgyer-manage',
      name: 'pgyer-manage',
      component: () => import('@/views/PgyerManageView.vue'),
    },
    // {
    //   path: '/markdown',
    //   name: 'markdown',
    //   component: () => import('@/views/MarkdownView.vue'),
    // },
  ],
})

export default router



