<template>
  <div class="markdown-editor">
    <div class="editor-header">
      <input
        v-model="article.title"
        placeholder="文章标题"
        class="title-input"
      />
      <div class="editor-actions">
        <button @click="saveDraft" class="btn-secondary">保存草稿</button>
        <button @click="publish" class="btn-primary">发布</button>
      </div>
    </div>

    <div class="editor-container">
      <div class="toolbar">
        <button @click="insertText('**粗体**')" title="粗体">B</button>
        <button @click="insertText('*斜体*')" title="斜体">I</button>
        <button @click="insertText('[链接](url)')" title="链接">🔗</button>
        <button @click="insertText('![图片](url)')" title="图片">🖼</button>
        <button @click="insertCodeBlock()" title="代码块">```</button>
      </div>

      <div class="editor-content">
        <textarea
          ref="textareaRef"
          v-model="article.content"
          @input="updatePreview"
          placeholder="开始编写您的文章..."
          class="markdown-input"
        ></textarea>

        <div class="preview-container">
          <div
            v-html="compiledMarkdown"
            class="markdown-preview"
          ></div>
        </div>
      </div>
    </div>

    <div class="editor-sidebar">
      <div class="metadata">
        <h3>文章信息</h3>
        <div class="form-group">
          <label>标签</label>
          <TagInput v-model="article.tags" />
        </div>
        <div class="form-group">
          <label>分类</label>
          <select v-model="article.category">
            <option value="技术">技术</option>
            <option value="生活">生活</option>
            <option value="随笔">随笔</option>
          </select>
        </div>
        <div class="form-group">
          <label>摘要</label>
          <textarea v-model="article.excerpt" rows="3"></textarea>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import MarkdownIt from 'markdown-it'
import hljs from 'highlight.js'
import 'highlight.js/styles/github.css'

// Markdown 解析器配置
const md = new MarkdownIt({
  html: true,
  linkify: true,
  typographer: true,
  highlight: (str, lang) => {
    if (lang && hljs.getLanguage(lang)) {
      try {
        return hljs.highlight(str, { language: lang }).value
      } catch (__) {}
    }
    return ''
  }
})

const textareaRef = ref(null)
const article = ref({
  title: '',
  content: '',
  tags: [],
  category: '技术',
  excerpt: '',
  published: false
})

const compiledMarkdown = computed(() => {
  return md.render(article.value.content || '')
})

const insertText = (text) => {
  const textarea = textareaRef.value
  const start = textarea.selectionStart
  const end = textarea.selectionEnd
  const selectedText = textarea.value.substring(start, end)

  article.value.content =
    article.value.content.substring(0, start) +
    text.replace('selected', selectedText) +
    article.value.content.substring(end)

  // 重新聚焦并设置光标位置
  textarea.focus()
  textarea.setSelectionRange(start + text.length, start + text.length)
}

const insertCodeBlock = () => {
  insertText('```javascript\n// 你的代码\n```')
}

const saveDraft = async () => {
  article.value.published = false
  await saveArticle()
}

const publish = async () => {
  article.value.published = true
  article.value.publishDate = new Date().toISOString()
  await saveArticle()
}

const saveArticle = async () => {
  // 保存逻辑 - 可以保存到本地或后端
  const articles = JSON.parse(localStorage.getItem('articles') || '[]')
  const existingIndex = articles.findIndex(a => a.id === article.value.id)

  if (existingIndex >= 0) {
    articles[existingIndex] = article.value
  } else {
    articles.push({
      ...article.value,
      id: Date.now().toString(),
      createDate: new Date().toISOString()
    })
  }

  localStorage.setItem('articles', JSON.stringify(articles))
  alert('保存成功!')
}
</script>

<style scoped>
.markdown-editor {
  display: grid;
  grid-template-areas:
    "header header"
    "editor sidebar";
  grid-template-rows: auto 1fr;
  grid-template-columns: 1fr 300px;
  height: 100vh;
  gap: 1rem;
  padding: 1rem;
}

.editor-header {
  grid-area: header;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.title-input {
  font-size: 1.5rem;
  font-weight: bold;
  border: none;
  outline: none;
  width: 100%;
}

.editor-actions {
  display: flex;
  gap: 0.5rem;
}

.editor-container {
  grid-area: editor;
  display: flex;
  flex-direction: column;
  border-radius: 8px;
  overflow: hidden;
}

.toolbar {
  padding: 0.5rem;
  background: #f5f5f5;
  border-bottom: 1px solid #ddd;
}

.toolbar button {
  padding: 0.5rem;
  border: 1px solid #ddd;
  background: white;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 0.5rem;
}

.editor-content {
  display: grid;
  grid-template-columns: 1fr 1fr;
  height: 100%;
}

.markdown-input, .preview-container {
  padding: 1rem;
  border: none;
  outline: none;
  font-family: 'Monaco', 'Consolas', monospace;
  font-size: 14px;
  line-height: 1.6;
}

.markdown-input {
  border-right: 1px solid #eee;
  resize: none;
}

.preview-container {
  overflow-y: auto;
}

.editor-sidebar {
  grid-area: sidebar;
  background: white;
  border-radius: 8px;
  padding: 1rem;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.btn-primary {
  background: #007bff;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
}

.btn-secondary {
  background: #6c757d;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
}
</style>
