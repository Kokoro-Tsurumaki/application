import './style.css'
import { createApp } from 'vue'

import Application from './App.vue'
import router from './router'

import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';

import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';


import hljs from 'highlight.js';
import Prism from 'prismjs';

const app = createApp(Application)




VueMarkdownEditor.use(vuepressTheme, {
  hljs,
});

app.use(VueMarkdownEditor);

app.use(router)

app.mount('#app')
