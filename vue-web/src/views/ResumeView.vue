<script setup lang="ts">
import {onMounted, ref} from "vue";
import {getResumeDataApi, type ResumeData} from "@/net/api/resume.ts";
// 获取简历数据
const getResumeData = async () => {
  try {
    const response = await getResumeDataApi()
    resumeData.value = response.data
  } catch (err) {
    console.error('Error fetching users:', err)
  } finally {
  }
}


onMounted(() => {
  getResumeData()
})
// 简历数据
const resumeData = ref<ResumeData>()

</script>

<template>
  <div class="resume-container" v-if="resumeData">


    <div class="resume">
      <!-- 左侧边栏 -->
      <aside class="sidebar">
        <img :src="resumeData.personalInfo.avatar" alt="头像" class="avatar">
        <h1 class="name">{{ resumeData.personalInfo.name }}</h1>
        <p class="title">{{ resumeData.personalInfo.title }}</p>

        <div class="card">
          <h2><i class="fas fa-id-card"></i> 联系方式</h2>
          <div
            class="contact-item"
            v-for="(contact, index) in resumeData.personalInfo.contacts"
            :key="index"
          >
            <i :class="contact.icon"></i>
            <span>{{ contact.text }}</span>
          </div>
        </div>

        <div class="card">
          <h2><i class="fas fa-star"></i> 专业技能</h2>
          <div class="skill" v-for="(skill, index) in resumeData.skills" :key="'skill-'+index">
            <div class="skill-name">
              <span>{{ skill.name }}</span>
              <span>{{ skill.level }}%</span>
            </div>
            <div class="skill-bar">·
              <div class="skill-level" :style="{ width: `${skill.level}%` }"></div>
            </div>
          </div>
        </div>

<!--        <div class="card">
          <h2><i class="fas fa-language"></i> 语言能力</h2>
          <div class="skill" v-for="(lang, index) in resumeData.languages" :key="'lang-'+index">
            <div class="skill-name">
              <span>{{ lang.name }}</span>
              <span>{{ lang.level }}%</span>
            </div>
            <div class="skill-bar">
              <div class="skill-level" :style="{ width: `${lang.level}%` }"></div>
            </div>
          </div>
        </div>-->
      </aside>

      <!-- 主内容区 -->
      <main class="main">
        <section class="section">
          <h2>个人简介</h2>
          <p class="timeline-desc">
            {{ resumeData.profile }}
          </p>
        </section>

        <section class="section">
          <h2>工作经历</h2>
          <div class="timeline">
            <div class="timeline-item" v-for="(exp, index) in resumeData.experiences" :key="'exp-'+index">
              <div class="timeline-date">{{ exp.period }}</div>
              <h3 class="timeline-title">{{ exp.company }} · <span>{{ exp.position }}</span></h3>
              <ul class="timeline-desc">
                <li v-for="(item, i) in exp.items" :key="i">{{ item }}</li>
              </ul>
            </div>
          </div>
        </section>

        <section class="section">
          <h2>教育背景</h2>
          <div class="timeline">
            <div class="timeline-item">
              <div class="timeline-date">{{ resumeData.education.period }}</div>
              <h3 class="timeline-title">{{ resumeData.education.school }} · <span>{{ resumeData.education.major }}</span></h3>
              <ul class="timeline-desc">
                <li v-for="(item, index) in resumeData.education.items" :key="'edu-'+index">{{ item }}</li>
              </ul>
            </div>
          </div>
        </section>

        <section class="section">
          <h2>精选项目</h2>
          <div class="projects">
            <div class="project-card" v-for="(project, index) in resumeData.projects" :key="'proj-'+index">
              <h3><i class="fas"></i> {{ project.title }}</h3>
              <p>{{ project.description }}</p>
              <div class="project-tags">
                <span class="tag" v-for="(tag, i) in project.tags" :key="i">{{ tag }}</span>
              </div>
            </div>
          </div>
        </section>
      </main>
    </div>
  </div>
</template>

<style scoped>

</style>
