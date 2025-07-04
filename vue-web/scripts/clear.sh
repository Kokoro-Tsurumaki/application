# 清理
npm cache clean --force && \
npm prune && \
npx depcheck && \
rm -rf node_modules && \
npm install