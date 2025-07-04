<script setup lang="ts">
// import {initializeTitle} from '@/core/document_ui.ts'
// initializeTitle("首页")
import * as d3 from 'd3';
import {onMounted, ref} from "vue";



onMounted({
  setup() {
    const graphContainer = ref(null);
    const selectedNode = ref(null);

    // 节点数据
    const nodes = ref([
      { id: 'README', name: 'README', category: 'core' },
      { id: 'Java', name: 'Java', category: 'other' },
      { id: 'Android', name: 'Android', category: 'other' },
      { id: 'Vue', name: 'Vue', category: 'other' },
      { id: 'Flutter', name: 'Flutter', category: 'other' },
      { id: 'JavaScript', name: 'JavaScript', category: 'other' },
      { id: '网络', name: '网络', category: 'other' },
      { id: 'TCP', name: 'TCP', category: 'other' },
      { id: 'UDP', name: 'UDP', category: 'other' },
      { id: 'Gradle', name: 'Gradle', category: 'other' },
      { id: 'git', name: 'git', category: 'other' },
      { id: '正则表达式', name: '正则表达式', category: 'other' },
      { id: '语法', name: '语法', category: 'other' },
      { id: '随笔1', name: '随笔1', category: 'other' },
      { id: 'View绘制', name: 'View绘制', category: 'other' },
      { id: 'Compose', name: 'Compose', category: 'other' },
      { id: 'Python', name: 'Python', category: 'other' },
      { id: 'AOSP', name: 'AOSP', category: 'other' },
      { id: 'adb', name: 'adb', category: 'other' },
      { id: 'Spring', name: 'Spring', category: 'other' },
      { id: 'C', name: 'C', category: 'other' },
      { id: 'C++', name: 'C++', category: 'other' },
      { id: 'Okhttp', name: 'Okhttp', category: 'other' },
      { id: 'Linex', name: 'Linex', category: 'other' },
      { id: 'record', name: 'record', category: 'other' },
      { id: '编解', name: '编解', category: 'other' },
      { id: 'Kotlin', name: 'Kotlin', category: 'other' },
      { id: 'mvn', name: 'mvn', category: 'other' },
      { id: '包管理工具', name: '包管理工具', category: 'other' },
      { id: '工作问题记录', name: '工作问题记录', category: 'other' },
      { id: '三方框架与架构搭建', name: '三方框架与架构搭建', category: 'other' },
      { id: '命令', name: '命令', category: 'other' },
      { id: '近代史', name: '近代史', category: 'other' },
      { id: '随笔近现代史', name: '随笔近现代史', category: 'other' }
    ]);

    // 连接数据
    const links = ref([
      { source: 'README', target: 'Java' },
      { source: 'README', target: 'Android' },
      { source: 'README', target: '网络' },
      { source: 'README', target: 'git' },
      { source: 'README', target: 'Gradle' },
      { source: 'README', target: 'Vue' },
      { source: 'README', target: '正则表达式' },
      { source: 'README', target: '随笔1' },
      { source: 'Java', target: 'Android' },
      { source: 'Java', target: 'Spring' },
      { source: 'Java', target: 'mvn' },
      { source: 'Android', target: 'View绘制' },
      { source: 'Android', target: 'Compose' },
      { source: 'Android', target: 'adb' },
      { source: 'Vue', target: 'JavaScript' },
      { source: 'Vue', target: '正则表达式' },
      { source: '网络', target: 'TCP' },
      { source: '网络', target: 'UDP' },
      { source: '正则表达式', target: 'Python' },
      { source: '正则表达式', target: '语法' },
      { source: 'git', target: '命令' },
      { source: 'Python', target: '编解' },
      { source: '随笔1', target: '近代史' },
      { source: '随笔1', target: '随笔近现代史' },
      { source: '工作问题记录', target: '三方框架与架构搭建' },
      { source: '工作问题记录', target: '包管理工具' },
      { source: '三方框架与架构搭建', target: 'Framework' },
      { source: 'Spring', target: 'Java' },
      { source: 'C++', target: 'Linex' },
      { source: 'C++', target: 'C' },
      { source: 'Android', target: 'AOSP' },
      { source: 'record', target: '工作问题记录' }
    ]);

    onMounted(() => {
      initGraph();
    });

    const initGraph = () => {
      const width = graphContainer.value.clientWidth;
      const height = graphContainer.value.clientHeight;

      // 创建SVG容器
      const svg = d3.select(graphContainer.value)
        .append('svg')
        .attr('width', width)
        .attr('height', height)
        .attr('viewBox', [0, 0, width, height])
        .call(d3.zoom().on('zoom', (event) => {
          svg.attr('transform', event.transform);
        }))
        .append('g');

      // 绘制连线
      const link = svg.selectAll('.link')
        .data(links.value)
        .enter()
        .append('line')
        .attr('class', 'link');

      // 绘制节点
      const node = svg.selectAll('.node')
        .data(nodes.value)
        .enter()
        .append('circle')
        .attr('class', d => `node ${d.category === 'core' ? 'node core' : 'node other'}`)
        .attr('data-id', d => d.id)
        .on('click', (event, d) => selectNode(d))
        .call(d3.drag()
          .on('start', (event, d) => {
            if (!event.active) simulation.alphaTarget(0.3).restart();
            d.fx = d.x;
            d.fy = d.y;
          })
          .on('drag', (event, d) => {
            d.fx = event.x;
            d.fy = event.y;
          })
          .on('end', (event, d) => {
            if (!event.active) simulation.alphaTarget(0);
            d.fx = null;
            d.fy = null;
          })
        );

      // 添加节点标签
      const labels = svg.selectAll('.node-text')
        .data(nodes.value)
        .enter()
        .append('text')
        .attr('class', 'node-text')
        .text(d => d.name)
        .attr('dy', d => d.category === 'core' ? 20 : 15)
        .attr('data-id', d => d.id)
        .on('click', (event, d) => selectNode(d));

      // 创建力导向模拟
      const simulation = d3.forceSimulation(nodes.value)
        .force('link', d3.forceLink(links.value).id(d => d.id).distance(100))
        .force('charge', d3.forceManyBody().strength(-300))
        .force('center', d3.forceCenter(width / 2, height / 2))
        .force('collision', d3.forceCollide().radius(d => d.category === 'core' ? 20 : 12))
        .on('tick', ticked);

      // 添加README节点的中心引力
      simulation.force('x', d3.forceX(width / 2).strength(d => d.id === 'README' ? 0.1 : 0.001));
      simulation.force('y', d3.forceY(height / 2).strength(d => d.id === 'README' ? 0.1 : 0.001));

      // 更新函数
      function ticked() {
        link
          .attr('x1', d => d.source.x)
          .attr('y1', d => d.source.y)
          .attr('x2', d => d.target.x)
          .attr('y2', d => d.target.y);

        node
          .attr('cx', d => d.x)
          .attr('cy', d => d.y);

        labels
          .attr('x', d => d.x)
          .attr('y', d => d.y);
      }

      // 选择节点
      function selectNode(nodeData) {
        selectedNode.value = nodeData;
        highlightNode(nodeData);
      }

      // 高亮节点和相关连接
      function highlightNode(node) {
        // 清除所有高亮
        d3.selectAll('.node-highlight').classed('node-highlight', false);
        d3.selectAll('.link-highlight').classed('link-highlight', false);

        // 高亮当前节点
        d3.select(`.node[data-id="${node.id}"]`).classed('node-highlight', true);

        // 高亮相关连线
        const relatedLinks = links.value.filter(l =>
          l.source.id === node.id || l.target.id === node.id
        );

        relatedLinks.forEach(link => {
          d3.selectAll('.link')
            .filter(d =>
              (d.source.id === link.source.id && d.target.id === link.target.id) ||
              (d.source.id === link.target.id && d.target.id === link.source.id)
            )
            .classed('link-highlight', true);
        });
      }
    };

    return {
      graphContainer
    };
  }
}).mount('#app');
</script>

<template>
  <div id="graph" class="container bg-base-100 w-full h-full graph"></div>
</template>

<style scoped>
#graph {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
}
</style>
