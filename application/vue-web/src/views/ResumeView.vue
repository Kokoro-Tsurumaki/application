<script setup lang="ts">
import {onMounted, ref} from "vue";
import {getResumeDataApi, type ResumeData} from "@/net/api/resume.ts";
// import '@/assets/base.css';
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
<!--              <span>{{ skill.level }}%</span>-->
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



/* 主容器 - 杂志风格布局 */
.resume {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: 300px 1fr;
  gap: 2rem;
  perspective: 1800px;
}

/* 左侧边栏 - 立体卡片效果 */
.sidebar {
  background: var(--bg-primary);
  border-radius: var(--radius-lg);
  padding: 2rem;
  box-shadow: var(--shadow-lg);
  transform-style: preserve-3d;
  position: relative;
  border: 1px solid rgba(255,255,255,0.1);
}

.sidebar::before {
  position: absolute;
  inset: 0;
  border-radius: var(--radius-lg);
  background: var(--gradient);
  transform: translateZ(-20px);
  opacity: 0.1;
  z-index: -1;
}

/* 头像 - 3D悬浮效果 */
.avatar {
  width: 180px;
  height: 180px;
  border-radius: var(--radius-full);
  object-fit: cover;
  margin: 0 auto 1.5rem;
  display: block;
  border: 4px solid white;
  box-shadow: var(--shadow-primary);
  transition: all 0.4s ease;
  transform: rotateY(0deg);
}

.avatar:hover {
  transform: rotateY(15deg) scale(1.05);
}

/* 主标题 - 渐变文字 */
.name {
  font-size: 2rem;
  font-weight: 800;
  background: var(--gradient);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  text-align: center;
  margin-bottom: 0.5rem;
  letter-spacing: -0.5px;
}

.title {
  font-size: 1.1rem;
  color: var(--secondary);
  text-align: center;
  margin-bottom: 2rem;
  font-weight: 500;
  position: relative;
}

.title::after {
  display: block;
  width: 60px;
  height: 3px;
  background: var(--gradient);
  margin: 0.8rem auto 0;
  border-radius: var(--radius-full);
}

/* 信息卡片 - 微交互 */
.card {
  margin-bottom: 2rem;
  position: relative;
}

.card::after {
  position: absolute;
  bottom: -1rem;
  left: 0;
  width: 40px;
  height: 3px;
  background: var(--accent);
  border-radius: var(--radius-full);
  transition: width 0.3s ease;
}

.card:hover::after {
  width: 80px;
}

.card h2 {
  font-size: 1.3rem;
  margin-bottom: 1.2rem;
  color: var(--text-primary);
  display: flex;
  align-items: center;
}

.card h2 i {
  margin-right: 0.6rem;
  color: var(--primary);
  font-size: 1.1rem;
}

/* 联系信息 - 图标动画 */
.contact-item {
  display: flex;
  align-items: center;
  margin-bottom: 0.8rem;
  transition: all 0.3s ease;
  padding: 0.5rem;
  border-radius: var(--radius-sm);
}

.contact-item:hover {
  background: rgba(107, 90, 237, 0.08);
  transform: translateX(5px);
}

.contact-item i {
  width: 18px;
  height: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--gradient);
  color: white;
  border-radius: 50%;
  margin-right: 0.5rem;
  font-size: 0.9rem;
}

/* 技能条 - 动态填充 */
.skill {
  margin-bottom: 1.2rem;
}

.skill-name {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.4rem;
  font-weight: 500;
}

.skill-bar {
  height: 8px;
  background: var(--decoration-border);
  border-radius: var(--radius-full);
  overflow: hidden;
  position: relative;
}

.skill-level {
  height: 100%;
  border-radius: var(--radius-full);
  background: var(--gradient);
  position: relative;
  transition: width 1s cubic-bezier(0.65, 0, 0.35, 1);
}

.skill-level::after {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(90deg,
  rgba(255,255,255,0.3) 0%,
  rgba(255,255,255,0) 100%);
}

/* 主内容区 - 杂志风格 */
.main {
  display: grid;
  gap: 2rem;
}

.section {
  background: var(--bg-primary);
  border-radius: var(--radius-lg);
  padding: 2rem;
  box-shadow: var(--shadow-md);
  position: relative;
  overflow: hidden;
}

.section::before {
  position: absolute;
  top: 0;
  left: 0;
  width: 5px;
  height: 100%;
  background: var(--gradient);
}

.section h2 {
  font-size: 1.5rem;
  margin-bottom: 1.5rem;
  color: var(--primary);
  position: relative;
  display: inline-block;
}

.section h2::after {
  position: absolute;
  bottom: -8px;
  left: 0;
  width: 40px;
  height: 3px;
  background: var(--gradient);
  border-radius: var(--radius-full);
}

/* 时间轴 - 3D效果 */
.timeline {
  display: block;
  position: relative;
  padding-left: 1.5rem;
}

.timeline::before {
  content: '';
  position: absolute;
  left: 7px;
  top: 10px;
  bottom: 10px;
  width: 2px;
  background: var(--decoration-border);
  transform: translateZ(-10px);
}

.timeline-item {
  position: relative;
  margin-bottom: 2rem;
  padding: 1.5rem;
  background: rgba(255,255,255,0.05);
  border-radius: var(--radius-md);
  transition: all 0.3s ease;
  border: 1px solid var(--decoration-border);
}

.timeline-item:hover {
  transform: translateY(-5px);
  box-shadow: var(--shadow-sm);
  border-color: var(--primary);
}

.timeline-item::before {
  content: '';
  position: absolute;
  left: -20px;
  top: 1.8rem;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: var(--gradient);
  border: 2px solid var(--bg-primary);
  z-index: 1;
}

.timeline-date {
  font-size: 0.9rem;
  color: var(--secondary);
  margin-bottom: 0.5rem;
  font-weight: 500;
}

.timeline-title {
  font-size: 1.2rem;
  margin-bottom: 0.8rem;
  color: var(--text-primary);
}

.timeline-title span {
  color: var(--primary);
  font-weight: 600;
}

.timeline-desc {
  color: var(--text-primary);
  opacity: 0.9;
  white-space: pre-line;
}

.timeline-desc li {
  margin-bottom: 0.5rem;
  position: relative;
  padding-left: 1.2rem;
}

.timeline-desc li::before {
  content: '•';
  position: absolute;
  left: 0;
  color: var(--accent);
  font-weight: bold;
}

/* 项目展示 - 卡片组 */
.projects {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
}

.project-card {
  background: var(--bg-primary);
  border-radius: var(--radius-md);
  padding: 1.5rem;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--decoration-border);
  transition: all 0.3s ease;
}

.project-card:hover {
  transform: translateY(-5px);
  box-shadow: var(--shadow-md);
  border-color: var(--secondary);
}

.project-card h3 {
  font-size: 1.1rem;
  margin-bottom: 0.8rem;
  color: var(--secondary);
  display: flex;
  align-items: center;
}

.project-card h3 i {
  margin-right: 0.6rem;
  color: var(--primary);
}

.project-card p {
  color: var(--text-primary);
  opacity: 0.9;
  font-size: 0.95rem;
  margin-bottom: 1rem;
}

.project-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-top: 1rem;
}

.tag {
  background: rgba(107, 90, 237, 0.1);
  color: var(--secondary);
  padding: 0.3rem 0.8rem;
  border-radius: var(--radius-full);
  font-size: 0.8rem;
  font-weight: 500;
}


/* 响应式设计 */
@media (max-width: 992px) {
  .resume {
    grid-template-columns: 1fr;
  }

  .projects {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  body {
    padding: 1rem;
  }

  .section, .sidebar {
    padding: 1.5rem;
  }
}

/* 打印优化 */
@media print {
  body {
    padding: 0;
    background: white !important;
    color: black !important;
  }

  .resume {
    grid-template-columns: 240px 1fr;
    gap: 1rem;
  }

  .sidebar, .section {
    box-shadow: none;
    border: 1px solid #ddd;
    page-break-inside: avoid;
  }

  a {
    text-decoration: none !important;
    color: inherit !important;
  }
}
</style>
